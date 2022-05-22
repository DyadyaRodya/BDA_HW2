package bdtc.lab2;

import java.lang.reflect.InvocationTargetException;
import java.util.*;
import java.util.stream.Collectors;

import bdtc.lab2.helpers.MapComputeTaskArg;
import bdtc.lab2.model.NewsEventStatEntity;
import bdtc.lab2.model.NewsInteractionEntity;
import bdtc.lab2.controller.model.NewsEventStat;
import org.apache.ignite.cluster.ClusterNode;
import org.apache.ignite.compute.*;
import org.apache.ignite.resources.TaskContinuousMapperResource;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class MapEventCountTask extends ComputeTaskAdapter<MapComputeTaskArg, Integer>{

    private MapComputeTaskArg updateStatArg;

    @NotNull @Override public Map<? extends ComputeJob, ClusterNode> map(List<ClusterNode> nodes, MapComputeTaskArg arg) {
        Map<ComputeJob, ClusterNode> map = new HashMap<>();

        Iterator<ClusterNode> it = nodes.iterator();

        this.updateStatArg = new MapComputeTaskArg(arg.getMethod(), arg.getObject());

        Map<Integer, Map<Integer, List<NewsInteractionEntity>>> groupedByNewsAndEvent = arg.getArg().stream()
                .collect(Collectors
                        .groupingBy(NewsInteractionEntity::getNews_id,Collectors.groupingBy(NewsInteractionEntity::getEvent_type)));

        for (Map<Integer, List<NewsInteractionEntity>> groupedByEvent : groupedByNewsAndEvent.values()){
            for (List<NewsInteractionEntity> statList: groupedByEvent.values()){
                // If we used all nodes, restart the iterator.
                if (!it.hasNext())
                    it = nodes.iterator();

                ClusterNode node = it.next();


                map.put(new ComputeJobAdapter() {
                    @Nullable @Override public Object execute() {

                        NewsInteractionEntity newsInteraction = statList.get(0);
                        NewsEventStat newsEventStatToSave = new NewsEventStat(newsInteraction.getNews_id(),
                                newsInteraction.getEvent_type(),
                                statList.size());

                        return newsEventStatToSave;
                    }
                }, node);

            }
        }


        return map;
    }

    @Override @Nullable public Integer reduce(List<ComputeJobResult> results) {
        int sumStat = 0;
        List<NewsEventStatEntity> newsEventStatsList = new ArrayList<>();
        Map<Integer, Map<Integer, List<NewsEventStat>>> groupedByNewsAndEvent = results.stream()
                .map(ComputeJobResult::<NewsEventStat>getData)
                .collect(Collectors
                        .groupingBy(NewsEventStat::getNews_id,Collectors.groupingBy(NewsEventStat::getEvent_type)));

        for (ComputeJobResult result : results){
            NewsEventStat newsEventStat = result.<NewsEventStat>getData();
            NewsEventStatEntity newsEventStatToSave = new NewsEventStatEntity(newsEventStat.getNews_id(),
                    newsEventStat.getEvent_type(),
                    newsEventStat.getTimes());
            newsEventStatsList.add(newsEventStatToSave);
            sumStat += newsEventStat.getTimes();
        }

        try {
            updateStatArg.invoke(newsEventStatsList);
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return sumStat;
    }
}
