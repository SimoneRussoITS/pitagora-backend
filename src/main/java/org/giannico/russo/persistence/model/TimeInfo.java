package org.giannico.russo.persistence.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class TimeInfo {
    private long currentPeriodStartTimestamp;

    @JsonCreator
    public TimeInfo(@JsonProperty("currentPeriodStartTimestamp") long currentPeriodStartTimestamp) {
        this.currentPeriodStartTimestamp = currentPeriodStartTimestamp;
    }

    public long getCurrentPeriodStartTimestamp() {
        return currentPeriodStartTimestamp;
    }

    public void setCurrentPeriodStartTimestamp(long currentPeriodStartTimestamp) {
        this.currentPeriodStartTimestamp = currentPeriodStartTimestamp;
    }
}
