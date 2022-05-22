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
    // кеш для данных о взаимодействиях (наполняется запросами от пользователя)
    CacheConfiguration<UUID, NewsInteractionEntity> newsInteractionCacheConfiguration;
    // кеш для статистики по news_id и event_type распределению
    CacheConfiguration<UUID, NewsEventStatEntity> newsEventStatCacheConfiguration;


    public TestServiceRepository(Ignite ignite, CacheConfiguration<UUID, NewsInteractionEntity> newsInteractionCacheConfiguration,
                                 CacheConfiguration<UUID, NewsEventStatEntity> newsEventStatCacheConfiguration) {
        this.ignite = ignite;
        this.newsInteractionCacheConfiguration = newsInteractionCacheConfiguration;
        this.newsEventStatCacheConfiguration = newsEventStatCacheConfiguration;
    }

    // Очищаем старую статистику и сохраняем новую
    public void updateStats(List<NewsEventStatEntity> newsEventStatEntitiess){
        ignite.getOrCreateCache(newsEventStatCacheConfiguration).clear();
        for (NewsEventStatEntity newsEventStatEntity: newsEventStatEntitiess)
            ignite.getOrCreateCache(newsEventStatCacheConfiguration).put(UUID.randomUUID(), newsEventStatEntity);
    }

    // добавление нового события
    public void save(NewsInteractionEntity newsInteractionEntity){
        ignite.getOrCreateCache(newsInteractionCacheConfiguration).put(newsInteractionEntity.getId(), newsInteractionEntity);
    }

    // поиск события по id
    public NewsInteractionEntity get(UUID id){
        return ignite.getOrCreateCache(newsInteractionCacheConfiguration).get(id);
    }

    // просмотр сохраненной статистики
    public List<NewsEventStatEntity> getStats(){
        Iterable<Cache.Entry<UUID, NewsEventStatEntity>> iterable = () -> ignite.getOrCreateCache(newsEventStatCacheConfiguration).iterator();

        List<NewsEventStatEntity> newsEventStatEntities = StreamSupport
                .stream(iterable.spliterator(), false)
                .map(Cache.Entry::getValue)
                .collect(Collectors.toList());

        return newsEventStatEntities;
    }

    // получение всех событий
    public List<NewsInteractionEntity> getAll(){
        Iterable<Cache.Entry<UUID, NewsInteractionEntity>> iterable = () -> ignite.getOrCreateCache(newsInteractionCacheConfiguration).iterator();

        List<NewsInteractionEntity> newsInteractionEntities = StreamSupport
                .stream(iterable.spliterator(), false)
                .map(Cache.Entry::getValue)
                .collect(Collectors.toList());

        return newsInteractionEntities;
    }

    // подсчет новой статистики
    public TotalStatsEntity countStats(){
        Iterable<Cache.Entry<UUID, NewsInteractionEntity>> iterable = () -> ignite.getOrCreateCache(newsInteractionCacheConfiguration).iterator();

        //собираем все события
        List<NewsInteractionEntity> newsInteractions = StreamSupport
                .stream(iterable.spliterator(), false)
                .map(Cache.Entry::getValue)
                .collect(Collectors.toList());

        // параметр для прокидывания результата reduce
        Class[] parameterTypes = new Class[1];
        parameterTypes[0] = List.class;

        int res = 0;
        System.out.println("=========Starting update stats task.=========\n");
        try {
            // класс аргумента для прокидывания кеша с методом обновления статистики
            MapComputeTaskArg arg = new MapComputeTaskArg(newsInteractions,
                    TestServiceRepository.class.getMethod("updateStats", parameterTypes),
                    this);
            // запуск таски
            res = ignite.compute().
                    execute(MapEventCountTask.class, arg);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
            res = -1;
        }
        System.out.println("=========Update stats task finished. Total result:=========\n");
        TotalStatsEntity totalStatsEntity = new TotalStatsEntity(res);
        System.out.println(totalStatsEntity);
        return totalStatsEntity;
    }
}
