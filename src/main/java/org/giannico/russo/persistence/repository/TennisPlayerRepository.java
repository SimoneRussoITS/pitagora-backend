package org.giannico.russo.persistence.repository;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.quarkus.mongodb.panache.PanacheMongoRepository;
import jakarta.enterprise.context.ApplicationScoped;
import org.giannico.russo.persistence.model.TennisPlayer;


@ApplicationScoped
public class TennisPlayerRepository implements PanacheMongoRepository<TennisPlayer> {
    private final ObjectMapper objectMapper;

    public TennisPlayerRepository(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }


    public TennisPlayer parseJsonToTennisPlayer(String json) throws JsonProcessingException {
        return objectMapper.readValue(json, TennisPlayer.class);
    }
}
