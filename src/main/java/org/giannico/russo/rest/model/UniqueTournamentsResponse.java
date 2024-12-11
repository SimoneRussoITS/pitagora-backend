package org.giannico.russo.rest.model;

import java.util.List;
import java.util.Map;

public class UniqueTournamentsResponse {
    private List<Map<String, Object>> uniqueTournaments;

    public List<Map<String, Object>> getUniqueTournaments() {
        return uniqueTournaments;
    }

    public void setUniqueTournaments(List<Map<String, Object>> uniqueTournaments) {
        this.uniqueTournaments = uniqueTournaments;
    }
}
