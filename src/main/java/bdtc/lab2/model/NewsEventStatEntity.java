package bdtc.lab2.model;

import bdtc.lab2.controller.model.NewsEventStat;

import java.util.Objects;
import java.util.UUID;

public class NewsEventStatEntity {
    private UUID id;
    private int news_id;
    private int event_type;
    private int times;

    public NewsEventStatEntity() {
    }

    public NewsEventStatEntity(int news_id, int event_type) {
        this.id = UUID.randomUUID();
        this.news_id = news_id;
        this.event_type = event_type;
        this.times = 1;
    }

    public NewsEventStatEntity(int news_id, int event_type, int times) {
        this.id = UUID.randomUUID();
        this.news_id = news_id;
        this.event_type = event_type;
        this.times = times;
    }

    public int getNews_id() {
        return news_id;
    }

    public void setNews_id(int news_id) {
        this.news_id = news_id;
    }

    public int getEvent_type() {
        return event_type;
    }

    public void setEvent_type(int event_type) {
        this.event_type = event_type;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        NewsEventStatEntity newsEventStatEntity = (NewsEventStatEntity) o;
        return Objects.equals(news_id, newsEventStatEntity.news_id) &&
                Objects.equals(event_type, newsEventStatEntity.event_type) &&
                Objects.equals(times, newsEventStatEntity.times);
    }

    public boolean isSameEvent(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        NewsEventStatEntity newsEventStatEntity = (NewsEventStatEntity) o;
        return Objects.equals(news_id, newsEventStatEntity.news_id) &&
                Objects.equals(event_type, newsEventStatEntity.event_type);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, news_id, event_type, times);
    }

    @Override
    public String toString() {
        return "NewsEventStat{" +
                "id=" + id +
                ", news_id=" + news_id +
                ", event_type=" + event_type +
                ", times=" + times +
                '}';
    }

    public int getTimes() {
        return times;
    }

    public void setTimes(int times) {
        this.times = times;
    }

    public void addTimes(int times) {
        this.times++;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }
}

