package bdtc.lab2.config;


import bdtc.lab2.dao.TestServiceRepository;
import bdtc.lab2.utils.EntityUtils;
import org.apache.ignite.Ignite;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class UtilsConf {
    @Bean
    EntityUtils entityUtils(TestServiceRepository testServiceRepository, Ignite ignite){
        return new EntityUtils(testServiceRepository, ignite);
    }
}
