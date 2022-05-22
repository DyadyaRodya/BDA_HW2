package bdtc.lab2.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import bdtc.lab2.config.ServiceConf;
import bdtc.lab2.config.UtilsConf;
import bdtc.lab2.model.NewsInteractionEntity;
import bdtc.lab2.utils.EntityUtils;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest
@RunWith(SpringRunner.class)
@ContextConfiguration(classes = {ServiceConf.class, UtilsConf.class})
public class TestServiceControllerTest {
    private ObjectMapper objectMapper = new ObjectMapper();

    @Autowired
    private MockMvc mvc;

    @Autowired
    private EntityUtils entityUtils;

    @Before
    public void init() {
        entityUtils.clearNewsInteractionEntitiesCache();
    }

    @Test
    public void get_should_returnNewsInteractionEntity_when_newsInteractionEntityExists() throws Exception {
        NewsInteractionEntity newsInteractionEntity = entityUtils.createAndSaveNewsInteractionEntity();
        String expectedJson = objectMapper.writeValueAsString(newsInteractionEntity);

        mvc.perform(get("/newsinteraction/get/" + newsInteractionEntity.getId()).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(expectedJson));
    }
}
