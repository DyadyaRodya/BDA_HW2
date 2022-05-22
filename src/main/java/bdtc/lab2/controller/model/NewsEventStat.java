package bdtc.lab2.controller.model;

import java.util.Objects;

public class NewsEventStat {
    private int news_id;
    private int event_type;
    private int times;

    public NewsEventStat() {
    }

    public NewsEventStat(int news_id, int event_type) {
        this.news_id = news_id;
        this.event_type = event_type;
        this.times = 1;
    }

    public NewsEventStat(int news_id, int event_type, int times) {
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
        NewsEventStat newsEventStat = (NewsEventStat) o;
        return Objects.equals(news_id, newsEventStat.news_id) &&
                Objects.equals(event_type, newsEventStat.event_type) &&
                Objects.equals(times, newsEventStat.times);
    }

    public boolean isSameEvent(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        NewsEventStat newsEventStat = (NewsEventStat) o;
        return Objects.equals(news_id, newsEventStat.news_id) &&
                Objects.equals(event_type, newsEventStat.event_type);
    }

    @Override
    public int hashCode() {

        return Objects.hash(news_id, event_type, times);
    }

    @Override
    public String toString() {
        return "NewsEventStat{" +
                "news_id=" + news_id +
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
}
