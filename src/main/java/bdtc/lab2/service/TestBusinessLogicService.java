package bdtc.lab2.service;

import bdtc.lab2.controller.model.NewsEventStat;
import bdtc.lab2.controller.model.NewsInteraction;
import bdtc.lab2.dao.TestServiceRepository;
import bdtc.lab2.model.NewsEventStatEntity;
import bdtc.lab2.model.TotalStatsEntity;
import bdtc.lab2.model.NewsInteractionEntity;

import java.util.List;
import java.util.UUID;

public class TestBusinessLogicService {

    private TestServiceRepository testServiceRepository;

    public TestBusinessLogicService(TestServiceRepository testServiceRepository) {
        this.testServiceRepository = testServiceRepository;
    }

    public NewsInteractionEntity processCreate(NewsInteraction newsInteraction){
        NewsInteractionEntity newsInteractionEntity = new NewsInteractionEntity(newsInteraction.getNews_id(),
                newsInteraction.getUser_id(), newsInteraction.getEvent_type(), newsInteraction.getSeconds());
        testServiceRepository.save(newsInteractionEntity);
        return newsInteractionEntity;
    }

    public NewsInteractionEntity processGet(String id){
        return testServiceRepository.get(UUID.fromString(id));
    }

    public List<NewsInteractionEntity> processGetAll(){
        return testServiceRepository.getAll();
    }

    public TotalStatsEntity processCountStats(){
        return testServiceRepository.countStats();
    }

    public List<NewsEventStatEntity> processGetStats() {return testServiceRepository.getStats();}
}
