package bdtc.lab2.service;

import bdtc.lab2.controller.model.NewsInteraction;
import bdtc.lab2.dao.TestServiceRepository;
import bdtc.lab2.model.NewsEventStatEntity;
import bdtc.lab2.model.NewsInteractionEntity;
import bdtc.lab2.model.TotalStatsEntity;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.List;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;


@RunWith(SpringRunner.class)
@ContextConfiguration(classes = {TestBusinessLogicServiceTest.TestBusinessLogicServiceTestConfiguration.class})
public class TestBusinessLogicServiceTest {

    @Autowired
    private TestBusinessLogicService testBusinessLogicService;

    @Autowired
    private TestServiceRepository testServiceRepository;

    @Test
    public void testCreateGetCount(){
        //create
        NewsInteraction newsInteraction = new NewsInteraction(1,1,1,10);

        NewsInteractionEntity newsInteractionEntity = testBusinessLogicService.processCreate(newsInteraction);

        Assert.assertEquals(newsInteraction.getNews_id(), newsInteractionEntity.getNews_id());
        Assert.assertEquals(newsInteraction.getUser_id(), newsInteractionEntity.getUser_id());
        Assert.assertEquals(newsInteraction.getEvent_type(), newsInteractionEntity.getEvent_type());
        Assert.assertEquals(newsInteraction.getSeconds(), newsInteractionEntity.getSeconds());
        Mockito.verify(testServiceRepository, Mockito.times(1)).save(newsInteractionEntity);

        //count
        TotalStatsEntity totalStatsEntity = testBusinessLogicService.processCountStats();

        Assert.assertEquals(5, totalStatsEntity.getTotalStats());
        Mockito.verify(testServiceRepository, Mockito.times(1)).countStats();

        //getAll
        List<NewsInteractionEntity> newsInteractionEntityList = testBusinessLogicService.processGetAll();

        Assert.assertEquals(1, newsInteractionEntityList.get(0).getNews_id());
        Assert.assertEquals(2, newsInteractionEntityList.get(1).getNews_id());
        Assert.assertEquals(1, newsInteractionEntityList.get(0).getUser_id());
        Assert.assertEquals(2, newsInteractionEntityList.get(1).getUser_id());
        Assert.assertEquals(1, newsInteractionEntityList.get(0).getEvent_type());
        Assert.assertEquals(2, newsInteractionEntityList.get(1).getEvent_type());
        Assert.assertEquals(10, newsInteractionEntityList.get(0).getSeconds());
        Assert.assertEquals(20, newsInteractionEntityList.get(1).getSeconds());
        Mockito.verify(testServiceRepository, Mockito.times(1)).getAll();

        //getStats
        List<NewsEventStatEntity> newsEventStatEntityList = testBusinessLogicService.processGetStats();

        Assert.assertEquals(1, newsEventStatEntityList.get(0).getNews_id());
        Assert.assertEquals(2, newsEventStatEntityList.get(1).getNews_id());
        Assert.assertEquals(1, newsEventStatEntityList.get(0).getTimes());
        Assert.assertEquals(2, newsEventStatEntityList.get(1).getTimes());
        Assert.assertEquals(1, newsEventStatEntityList.get(0).getEvent_type());
        Assert.assertEquals(2, newsEventStatEntityList.get(1).getEvent_type());
        Mockito.verify(testServiceRepository, Mockito.times(1)).getStats();

    }

    @Configuration
    static class TestBusinessLogicServiceTestConfiguration {

        @Bean
        TestServiceRepository testServiceRepository() {
            TestServiceRepository testServiceRepository = mock(TestServiceRepository.class);
            when(testServiceRepository.get(any())).thenReturn(new NewsInteractionEntity(1,2,3,40));
            when(testServiceRepository.countStats()).thenReturn(new TotalStatsEntity(5));
            when(testServiceRepository.getAll())
                    .thenReturn(Arrays.asList(new NewsInteractionEntity(1,1,1,10),
                            new NewsInteractionEntity(2,2,2,20)));
            when(testServiceRepository.getStats())
                    .thenReturn(Arrays.asList(new NewsEventStatEntity(1,1,1),
                            new NewsEventStatEntity(2,2,2)));
            return testServiceRepository;
        }

        @Bean
        TestBusinessLogicService testBusinessLogicService(TestServiceRepository testServiceRepository){
            return new TestBusinessLogicService(testServiceRepository);
        }
    }

}
