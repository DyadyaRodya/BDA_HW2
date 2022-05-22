package bdtc.lab2.dao;

import bdtc.lab2.controller.model.NewsEventStat;
import bdtc.lab2.MapEventCountTask;
import bdtc.lab2.helpers.MapComputeTaskArg;
import bdtc.lab2.model.NewsEventStatEntity;
import bdtc.lab2.model.TotalStatsEntity;
import bdtc.lab2.model.NewsInteractionEntity;
import org.apache.ignite.Ignite;
import org.apache.ignite.configuration.CacheConfiguration;

import javax.cache.Cache;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public class TestServiceRepository {
    private Ignite ignite;
    CacheConfiguration<UUID, NewsInteractionEntity> newsInteractionCacheConfiguration;
    CacheConfiguration<UUID, NewsEventStatEntity> newsEventStatCacheConfiguration;


    public TestServiceRepository(Ignite ignite, CacheConfiguration<UUID, NewsInteractionEntity> newsInteractionCacheConfiguration,
                                 CacheConfiguration<UUID, NewsEventStatEntity> newsEventStatCacheConfiguration) {
        this.ignite = ignite;
        this.newsInteractionCacheConfiguration = newsInteractionCacheConfiguration;
        this.newsEventStatCacheConfiguration = newsEventStatCacheConfiguration;
    }

    public void updateStats(List<NewsEventStatEntity> newsEventStatEntitiess){
        ignite.getOrCreateCache(newsEventStatCacheConfiguration).clear();
        for (NewsEventStatEntity newsEventStatEntity: newsEventStatEntitiess)
            ignite.getOrCreateCache(newsEventStatCacheConfiguration).put(UUID.randomUUID(), newsEventStatEntity);
    }

    public void save(NewsInteractionEntity newsInteractionEntity){
        ignite.getOrCreateCache(newsInteractionCacheConfiguration).put(newsInteractionEntity.getId(), newsInteractionEntity);
    }

    public NewsInteractionEntity get(UUID id){
        return ignite.getOrCreateCache(newsInteractionCacheConfiguration).get(id);
    }

    public List<NewsEventStatEntity> getStats(){
        Iterable<Cache.Entry<UUID, NewsEventStatEntity>> iterable = () -> ignite.getOrCreateCache(newsEventStatCacheConfiguration).iterator();

        List<NewsEventStatEntity> newsEventStatEntities = StreamSupport
                .stream(iterable.spliterator(), false)
                .map(Cache.Entry::getValue)
                .collect(Collectors.toList());

        return newsEventStatEntities;
    }

    public List<NewsInteractionEntity> getAll(){
        Iterable<Cache.Entry<UUID, NewsInteractionEntity>> iterable = () -> ignite.getOrCreateCache(newsInteractionCacheConfiguration).iterator();

        List<NewsInteractionEntity> newsInteractionEntities = StreamSupport
                .stream(iterable.spliterator(), false)
                .map(Cache.Entry::getValue)
                .collect(Collectors.toList());

        return newsInteractionEntities;
    }

    public TotalStatsEntity countStats(){
        Iterable<Cache.Entry<UUID, NewsInteractionEntity>> iterable = () -> ignite.getOrCreateCache(newsInteractionCacheConfiguration).iterator();

        List<NewsInteractionEntity> newsInteractions = StreamSupport
                .stream(iterable.spliterator(), false)
                .map(Cache.Entry::getValue)
                .collect(Collectors.toList());

        Class[] parameterTypes = new Class[1];
        parameterTypes[0] = List.class;

        int res = 0;
        try {
            MapComputeTaskArg arg = new MapComputeTaskArg(newsInteractions,
                    TestServiceRepository.class.getMethod("updateStats", parameterTypes),
                    this);
            res = ignite.compute().
                    execute(MapEventCountTask.class, arg);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }

        TotalStatsEntity totalStatsEntity = new TotalStatsEntity(res);

        return totalStatsEntity;
    }
}
