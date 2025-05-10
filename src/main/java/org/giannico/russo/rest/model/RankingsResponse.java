package org.giannico.russo.rest.model;

import java.util.List;
import java.util.Map;

public class RankingsResponse {
    private List<Map<String, Object>> rankings;

    public List<Map<String, Object>> getRankings() {
        return rankings;
    }

    public void setRankings(List<Map<String, Object>> rankings) {
        this.rankings = rankings;
    }
}
