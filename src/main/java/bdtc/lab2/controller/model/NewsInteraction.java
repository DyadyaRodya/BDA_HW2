package bdtc.lab2.controller.model;

import java.util.Objects;

public class NewsInteraction {
    private int news_id;
    private int user_id;
    private int event_type;
    private int seconds;

    public NewsInteraction() {
    }

    public NewsInteraction(int news_id, int user_id, int event_type, int seconds) {
        this.news_id = news_id;
        this.user_id = user_id;
        this.event_type = event_type;
        this.seconds = seconds;
    }

    public int getNews_id() {
        return news_id;
    }

    public void setNews_id(int news_id) {
        this.news_id = news_id;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public int getEvent_type() {
        return event_type;
    }

    public void setEvent_type(int event_type) {
        this.event_type = event_type;
    }

    public int getSeconds() {
        return seconds;
    }

    public void setSeconds(int seconds) {
        this.seconds = seconds;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        NewsInteraction newsInteraction = (NewsInteraction) o;
        return Objects.equals(news_id, newsInteraction.news_id) &&
                Objects.equals(user_id, newsInteraction.user_id) &&
                Objects.equals(event_type, newsInteraction.event_type) &&
                Objects.equals(seconds, newsInteraction.seconds);
    }

    @Override
    public int hashCode() {

        return Objects.hash(news_id, user_id, event_type, seconds);
    }

    @Override
    public String toString() {
        return "NewsInteraction{" +
                "news_id=" + news_id +
                ", user_id=" + user_id +
                ", event_type=" + event_type +
                ", seconds=" + seconds +
                '}';
    }
}
