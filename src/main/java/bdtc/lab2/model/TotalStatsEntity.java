package bdtc.lab2.model;

import java.util.Objects;

public class TotalStatsEntity {
    private int totalStats;

    public TotalStatsEntity(int totalStats) {
        this.totalStats = totalStats;
    }

    public int getTotalStats() {
        return totalStats;
    }

    public void setTotalStats(int totalStats) {
        this.totalStats = totalStats;
    }

    @Override
    public String toString() {
        return "TotalStatsEntity{" +
                "total_stats=" + totalStats +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TotalStatsEntity totalStatsEntity = (TotalStatsEntity) o;
        return Objects.equals(this.totalStats, totalStatsEntity.totalStats);
    }

    @Override
    public int hashCode() {

        return Objects.hash(totalStats);
    }
}
