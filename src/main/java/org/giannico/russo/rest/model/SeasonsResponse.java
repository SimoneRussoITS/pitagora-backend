package org.giannico.russo.rest.model;

import java.util.List;
import java.util.Map;

public class SeasonsResponse {
    private List<Map<String, Object>> seasons;

    public List<Map<String, Object>> getSeasons() {
        return seasons;
    }

    public void setSeasons(List<Map<String, Object>> seasons) {
        this.seasons = seasons;
    }
}
