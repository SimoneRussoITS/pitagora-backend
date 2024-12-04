package org.giannico.russo.persistence.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.quarkus.mongodb.panache.common.MongoEntity;
import org.bson.types.ObjectId;
import org.giannico.russo.persistence.model.enums.Round;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.List;

@MongoEntity(collection = "tennis-match")
public class TennisMatch {
    private String id;
    private TennisTournament tournament;
    private Round round;
    private LocalDateTime scheduledOnAt;
    private TennisPlayer homePlayer;
    private TennisPlayer awayPlayer;
    private List<Set> results;
    private Duration duration;

    public String getId() {
        return id;
    }

    public void setId(String id) {
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

    @JsonCreator
    public TennisMatch(
            @JsonProperty("homeScore") Object homeScore,
            @JsonProperty("awayScore") Object awayScore,
            @JsonProperty("homeTeam") Object homeTeam,
            @JsonProperty("awayTeam") Object awayTeam,
            @JsonProperty("startTimestamp") long startTimestamp,
            @JsonProperty("time") TimeInfo timeInfo,
            @JsonProperty("roundInfo") Object roundInfo,
            @JsonProperty("tournament") TennisTournament tournament) {
        this.results = new ArrayList<>();
        // Controllo per evitare NullPointerException se homeScore o awayScore sono null
        if (homeScore != null && awayScore != null) {
            int maxSets = Math.min(5, Math.max(((java.util.Map<?, ?>) homeScore).size(), ((java.util.Map<?, ?>) awayScore).size()));
            for (int i = 1; i <= maxSets; i++) {
                Object homeGamesObj = ((java.util.Map<?, ?>) homeScore).get("period");
                Object awayGamesObj = ((java.util.Map<?, ?>) awayScore).get("period");
                if (homeGamesObj != null && awayGamesObj != null) {
                    int homeGames = ((Number) homeGamesObj).intValue();
                    int awayGames = ((Number) awayGamesObj).intValue();
                    results.add(new Set(i, homeGames, awayGames));
                }
            }
        }
        // Verifica se homeTeam e awayTeam sono non nulli prima di creare i giocatori
        this.homePlayer = (homeTeam != null) ? createPlayerFromTeam((java.util.Map<?, ?>) homeTeam) : null;
        this.awayPlayer = (awayTeam != null) ? createPlayerFromTeam((java.util.Map<?, ?>) awayTeam) : null;
        // Gestione di timeInfo: se è null, assegna un valore di fallback
        if (timeInfo == null) {
            this.duration = Duration.ZERO;  // Imposta durata a zero se timeInfo è null
        } else {
            // Calcola la durata utilizzando i timestamp
            this.duration = Duration.ofSeconds(timeInfo.getCurrentPeriodStartTimestamp() - startTimestamp);
        }
        // Gestisci roundInfo se è null
        if (roundInfo != null) {
            this.round = createRoundFromInfo((java.util.Map<?, ?>) roundInfo);
        } else {
            this.round = null; // O qualsiasi altro valore di fallback se necessario
        }
        this.scheduledOnAt = convertTimestampToLocalDateTime(startTimestamp);
        this.tournament = tournament;
    }

    // Metodo di supporto per creare un TennisPlayer a partire dai dati del team
    private TennisPlayer createPlayerFromTeam(java.util.Map<?, ?> teamData) {
        if (teamData == null) {
            return new TennisPlayer(
                    null,
                    "",
                    "",
                    null,
                    0,
                    "",
                    0,
                    0);
        }

        String fullName = (String) teamData.get("name"); // Nome completo del giocatore
        String[] names = fullName.split(" ", 2); // Dividi in nome e cognome
        String nationality = (String) ((java.util.Map<?, ?>) teamData.get("country")).get("name");

        return new TennisPlayer(
                null, // ID non fornito nel JSON
                names.length > 0 ? names[0] : "",
                names.length > 1 ? names[1] : "",
                null, // birthday non fornito nel JSON
                0, // ATP ranking non fornito nel JSON
                nationality,
                0, // Peso non fornito
                0  // Altezza non fornita
        );
    }

    // Metodo di supporto per creare l'enum Round dal campo "roundInfo"
    private Round createRoundFromInfo(java.util.Map<?, ?> roundInfoData) {
        String roundDescription = (String) roundInfoData.get("name");
        try {
            return Round.fromDescription(roundDescription);
        } catch (IllegalArgumentException e) {
            // Gestione dell'errore per round sconosciuti, ad esempio log o valore di fallback
            return Round.UNKNOWN; // O qualsiasi altro valore predefinito
        }
    }

    // Metodo di supporto per convertire il timestamp Unix in LocalDateTime
    private LocalDateTime convertTimestampToLocalDateTime(long timestamp) {
        return LocalDateTime.ofEpochSecond(timestamp, 0, ZoneOffset.UTC);
    }
}
