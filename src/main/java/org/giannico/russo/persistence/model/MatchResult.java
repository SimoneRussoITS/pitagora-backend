package org.giannico.russo.persistence.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.quarkus.mongodb.panache.common.MongoEntity;
import org.bson.types.ObjectId;

import java.util.List;

@MongoEntity(collection = "tennis-match-result")
public class MatchResult {
    @JsonIgnore
    private ObjectId id;
    private ObjectId matchId;
    private List<Set> sets;
}
