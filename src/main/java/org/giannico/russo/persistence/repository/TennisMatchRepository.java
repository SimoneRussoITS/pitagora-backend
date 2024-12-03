package org.giannico.russo.persistence.repository;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.enterprise.context.ApplicationScoped;
import org.giannico.russo.persistence.model.TennisMatch;
import org.giannico.russo.service.TennisMatchService;

import java.util.List;

@ApplicationScoped
public class TennisMatchRepository {
    private final ObjectMapper objectMapper;
    private final TennisMatchService tennisMatchService;

    public TennisMatchRepository(ObjectMapper objectMapper, TennisMatchService tennisMatchService) {
        this.objectMapper = objectMapper;
        this.tennisMatchService = tennisMatchService;
    }

    public String getTodayTennisMatches() throws JsonProcessingException {
        String jsonResponse = tennisMatchService.getTodayTennisMatches();
        return jsonResponse;
    }
}
