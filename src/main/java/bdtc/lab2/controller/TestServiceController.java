package bdtc.lab2.controller;

import bdtc.lab2.controller.model.NewsEventStat;
import bdtc.lab2.controller.model.NewsInteraction;
import bdtc.lab2.model.NewsEventStatEntity;
import bdtc.lab2.model.TotalStatsEntity;
import bdtc.lab2.model.NewsInteractionEntity;
import bdtc.lab2.service.TestBusinessLogicService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("newsinteraction")
public class TestServiceController {

    private TestBusinessLogicService testBusinessLogicService;

    public TestServiceController(TestBusinessLogicService testBusinessLogicService) {
        this.testBusinessLogicService = testBusinessLogicService;
    }

    @PostMapping(path = {"/create"}, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<NewsInteractionEntity> createNewsInteraction(@RequestBody NewsInteraction newsInteraction) {
        NewsInteractionEntity newsInteractionEntity = testBusinessLogicService.processCreate(newsInteraction);
        return new ResponseEntity<>(newsInteractionEntity, HttpStatus.OK);
    }

    @GetMapping(path = {"/get/{id}"}, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<NewsInteractionEntity> getNewsInteraction(@PathVariable String id) {
        NewsInteractionEntity newsInteractionEntity = testBusinessLogicService.processGet(id);
        return new ResponseEntity<>(newsInteractionEntity, HttpStatus.OK);
    }

    @GetMapping(path = {"/get/all"}, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<NewsInteractionEntity>> getAll() {
        List<NewsInteractionEntity> newsInteractionEntities = testBusinessLogicService.processGetAll();
        return new ResponseEntity<>(newsInteractionEntities, HttpStatus.OK);
    }

    @GetMapping(path = {"/get/stats"}, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<NewsEventStatEntity>> getStats() {
        List<NewsEventStatEntity> newsEventStatsEntities = testBusinessLogicService.processGetStats();
        return new ResponseEntity<>(newsEventStatsEntities, HttpStatus.OK);
    }

    @PostMapping(path = {"/count"}, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<TotalStatsEntity> countStats() {
        TotalStatsEntity totalStatsEntity = testBusinessLogicService.processCountStats();
        return new ResponseEntity<>(totalStatsEntity, HttpStatus.OK);
    }
}
