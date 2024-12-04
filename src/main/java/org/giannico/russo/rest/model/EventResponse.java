package org.giannico.russo.rest.model;

import java.util.List;
import java.util.Map;

// Classe per mappare il JSON principale
public class EventResponse {
    private List<Map<String, Object>> events;

    // Getter e Setter
    public List<Map<String, Object>> getEvents() {
        return events;
    }

    public void setEvents(List<Map<String, Object>> events) {
        this.events = events;
    }
}