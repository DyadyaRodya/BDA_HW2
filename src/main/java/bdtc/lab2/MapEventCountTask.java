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

        for (final NewsInteractionEntity newsEvent : arg.getArg()) {
            // If we used all nodes, restart the iterator.
            if (!it.hasNext())
                it = nodes.iterator();

            ClusterNode node = it.next();

            map.put(new ComputeJobAdapter() {
                @Nullable @Override public Object execute() {

                    // Return number of letters in the word.
                    return new NewsEventStat(newsEvent.getNews_id(),newsEvent.getEvent_type());
                }
            }, node);
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

        for (Map<Integer, List<NewsEventStat>> groupedByEvent : groupedByNewsAndEvent.values()){
            for (List<NewsEventStat> statList: groupedByEvent.values()){
                NewsEventStat newsEventStat = statList.get(0);
                NewsEventStatEntity newsEventStatToSave = new NewsEventStatEntity(newsEventStat.getNews_id(),
                        newsEventStat.getEvent_type(),
                        statList.size());
                newsEventStatsList.add(newsEventStatToSave);
                sumStat += statList.size();
            }
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