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

    public void insertTennisPlayerInDatabase(TennisPlayer player){
        persist(player);
    }

    public TennisPlayer findByFirstNameAndLastName(String firstName, String lastName) {
        TennisPlayer tennisPlayer = find("{ firstName: { $regex: ?1, $options: \"i\" }, lastName: { $regex: ?2, $options: \"i\" } }", firstName, lastName).firstResult();
        return tennisPlayer;
    }

    public TennisPlayer findByLastName(String lastName) {
        TennisPlayer tennisPlayer = find("{\"lastName\": {\"$regex\": ?1, \"$options\": \"i\"}}", lastName).firstResult();
        return tennisPlayer;
    }
}
