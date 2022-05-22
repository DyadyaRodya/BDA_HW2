package bdtc.lab2.config;

import bdtc.lab2.controller.TestServiceController;
import bdtc.lab2.dao.TestServiceRepository;
import bdtc.lab2.model.NewsEventStatEntity;
import bdtc.lab2.model.NewsInteractionEntity;
import bdtc.lab2.service.TestBusinessLogicService;
import org.apache.ignite.Ignite;
import org.apache.ignite.configuration.CacheConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import java.util.UUID;

@Configuration
@Import(IgniteConf.class)
public class ServiceConf {
    @Bean
    TestServiceRepository testServiceRepository(Ignite ignite, CacheConfiguration<UUID, NewsInteractionEntity> newsInteractionCacheConf,
                                                CacheConfiguration<UUID, NewsEventStatEntity> newsEventStatCacheConfiguration){
        return new TestServiceRepository(ignite, newsInteractionCacheConf, newsEventStatCacheConfiguration);
    }

    @Bean
    TestBusinessLogicService testBusinessLogicService(TestServiceRepository testServiceRepository){
        return new TestBusinessLogicService(testServiceRepository);
    }

    @Bean
    TestServiceController testServiceController(TestBusinessLogicService testBusinessLogicService){
        return new TestServiceController(testBusinessLogicService);
    }
}
