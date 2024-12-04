package org.giannico.russo.persistence.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.quarkus.mongodb.panache.common.MongoEntity;
import org.bson.types.ObjectId;
import org.giannico.russo.persistence.model.enums.Round;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;

@MongoEntity(collection = "tennis-match")
public class TennisMatch {
    private ObjectId id;

    @JsonProperty("tournament")
    private TennisTournament tournament;

    @JsonProperty("roundInfo")
    private Round round;
    private LocalDateTime scheduledOnAt;
    private TennisPlayer homePlayer;
    private TennisPlayer awayPlayer;
    private List<Set> results;
    private Duration duration;

    public ObjectId getId() {
        return id;
    }

    public void setId(ObjectId id) {
        this.id = id;
    }

    public TennisTournament getTournament() {
        return tournament;
    }

    public void setTournament(TennisTournament tournament) {
        this.tournament = tournament;
    }

    public Round getRound() {
        return round;
    }

    public void setRound(Round round) {
        this.round = round;
    }

    public LocalDateTime getScheduledOnAt() {
        return scheduledOnAt;
    }

    public void setScheduledOnAt(LocalDateTime scheduledOnAt) {
        this.scheduledOnAt = scheduledOnAt;
    }

    public TennisPlayer getHomePlayer() {
        return homePlayer;
    }

    public void setHomePlayer(TennisPlayer homePlayer) {
        this.homePlayer = homePlayer;
    }

    public TennisPlayer getAwayPlayer() {
        return awayPlayer;
    }

    public void setAwayPlayer(TennisPlayer awayPlayer) {
        this.awayPlayer = awayPlayer;
    }

    public List<Set> getResults() {
        return results;
    }

    public void setResults(List<Set> results) {
        this.results = results;
    }

    public Duration getDuration() {
        return duration;
    }

    public void setDuration(Duration duration) {
        this.duration = duration;
    }
}
