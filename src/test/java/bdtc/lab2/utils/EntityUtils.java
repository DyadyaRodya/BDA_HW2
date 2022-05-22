package bdtc.lab2.utils;

import bdtc.lab2.dao.TestServiceRepository;
import bdtc.lab2.model.NewsInteractionEntity;
import java.util.concurrent.ThreadLocalRandom;
import org.apache.ignite.Ignite;

public class EntityUtils {
    private TestServiceRepository testServiceRepository;
    private Ignite ignite;

    public EntityUtils(TestServiceRepository testServiceRepository, Ignite ignite) {
        this.testServiceRepository = testServiceRepository;
        this.ignite = ignite;
    }



    public NewsInteractionEntity createAndSaveNewsInteractionEntity() {
        NewsInteractionEntity newsInteractionEntity = createNewsInteractionEntity();
        testServiceRepository.save(newsInteractionEntity);

        return newsInteractionEntity;
    }

    public void clearNewsInteractionEntitiesCache() {
        ignite.getOrCreateCache("newsInteraction").clear();
    }

    public static NewsInteractionEntity createNewsInteractionEntity() {
        int news_id = ThreadLocalRandom.current().nextInt(0, 1000 + 1);
        int user_id = ThreadLocalRandom.current().nextInt(0, 1000 + 1);
        int event_type = ThreadLocalRandom.current().nextInt(1, 3 + 1);
        int seconds = ThreadLocalRandom.current().nextInt(10, 10000 + 1);
        return new NewsInteractionEntity(news_id,user_id,event_type,seconds);
    }

}
