package bdtc.lab2.model;

import java.util.Objects;
import java.util.UUID;

public class NewsInteractionEntity {
    private UUID id;
    private int news_id;
    private int user_id;
    private int event_type;
    private int seconds;

    public NewsInteractionEntity(int news_id, int user_id, int event_type, int seconds) {
        this.id = UUID.randomUUID();
        this.news_id = news_id;
        this.user_id = user_id;
        this.event_type = event_type;
        this.seconds = seconds;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        NewsInteractionEntity newsInteractionEntity = (NewsInteractionEntity) o;
        return Objects.equals(id, newsInteractionEntity.id) &&
                Objects.equals(news_id, newsInteractionEntity.news_id) &&
                Objects.equals(user_id, newsInteractionEntity.user_id) &&
                Objects.equals(event_type, newsInteractionEntity.event_type) &&
                Objects.equals(seconds, newsInteractionEntity.seconds);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, news_id, user_id, event_type, seconds);
    }

    @Override
    public String toString() {
        return "NewsEntity{" +
                "id=" + id +
                ", news_id=" + news_id +
                ", user_id=" + user_id +
                ", event_type=" + event_type +
                ", seconds=" + seconds +
                '}';
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

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }
}
