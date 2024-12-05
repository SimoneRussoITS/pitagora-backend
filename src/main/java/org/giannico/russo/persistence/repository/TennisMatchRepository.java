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
            // Deserializza l'oggetto principale (EventResponse) che contiene "events"
            EventResponse eventResponse = objectMapper.readValue(json, EventResponse.class);
            // Estrai l'array di eventi
            List<Map<String, Object>> events = eventResponse.getEvents();
            // Puoi ora convertire ogni elemento dell'array (evento) in un oggetto TennisMatch
            List<TennisMatch> tennisMatches = objectMapper.convertValue(
                    events,
                    objectMapper
                            .getTypeFactory()
                            .constructCollectionType(List.class, TennisMatch.class)
            );
            return tennisMatches;
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Errore durante il parsing del JSON", e);
        }
    }

}

