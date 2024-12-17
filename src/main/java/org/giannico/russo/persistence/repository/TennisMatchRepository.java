package org.giannico.russo.persistence.repository;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.enterprise.context.ApplicationScoped;
import org.giannico.russo.persistence.model.TennisMatch;
import org.giannico.russo.rest.model.EventResponse;

import java.util.List;
import java.util.Map;

@ApplicationScoped
public class TennisMatchRepository {
    private final ObjectMapper objectMapper;

    public TennisMatchRepository(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    public List<TennisMatch> parseTennisMatches(String json) {
        try {
            // Deserialize the json passed in the method
            EventResponse eventResponse = objectMapper.readValue(json, EventResponse.class);
            // Extract the array of events
            List<Map<String, Object>> events = eventResponse.getEvents();
            // Convert each element in a TennisMatch object and return the complete list
            return objectMapper.convertValue(
                    events,
                    objectMapper
                            .getTypeFactory()
                            .constructCollectionType(List.class, TennisMatch.class)
            );
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Errore durante il parsing del JSON", e);
        }
    }

}

