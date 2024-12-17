package org.giannico.russo.persistence.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.quarkus.mongodb.panache.common.MongoEntity;
import org.giannico.russo.persistence.model.enums.Category;
import org.giannico.russo.persistence.model.enums.Round;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@MongoEntity(collection = "tennis-match")
public class TennisMatch {
    @JsonIgnore
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
            @JsonProperty("tournament") Map<?, ?> tournamentData,
            @JsonProperty("season") Object seasonData) {
        this.results = new ArrayList<>();
        // Controllo per evitare NullPointerException se homeScore o awayScore sono null
        if (homeScore != null && awayScore != null) {
            for (int i = 1; i <= 5; i++) {
                Object homeGamesObj = ((Map<?, ?>) homeScore).get("period" + i);
                Object awayGamesObj = ((Map<?, ?>) awayScore).get("period" + i);
                if (homeGamesObj != null && awayGamesObj != null) {
                    int homeGames = ((Number) homeGamesObj).intValue();
                    int awayGames = ((Number) awayGamesObj).intValue();
                    results.add(new Set(i - 1, homeGames, awayGames));
                }
            }
        }
        // Verifica se homeTeam e awayTeam sono non nulli prima di creare i giocatori
        this.homePlayer = (homeTeam != null) ? createPlayerFromTeam((Map<?, ?>) homeTeam) : null;
        this.awayPlayer = (awayTeam != null) ? createPlayerFromTeam((Map<?, ?>) awayTeam) : null;
        // Gestione di timeInfo: se è null, assegna un valore di fallback
        if (timeInfo == null) {
            this.duration = Duration.ZERO;  // Imposta durata a zero se timeInfo è null
        } else {
            // Calcola la durata utilizzando i timestamp
            this.duration = Duration.ofSeconds(timeInfo.getCurrentPeriodStartTimestamp() - startTimestamp);
        }
        // Gestisci roundInfo se è null
        if (roundInfo != null) {
            this.round = createRoundFromInfo((Map<?, ?>) roundInfo);
        } else {
            this.round = null; // O qualsiasi altro valore di fallback se necessario
        }
        this.scheduledOnAt = convertTimestampToLocalDateTime(startTimestamp);
        this.tournament = getTournamentFromJson(tournamentData, (Map<?, ?>) seasonData);
    }

    // Metodo di supporto per creare un TennisPlayer a partire dai dati del team
    private TennisPlayer createPlayerFromTeam(Map<?, ?> teamData) {
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
        String nationality = (String) ((Map<?, ?>) teamData.get("country")).get("name");

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
    private Round createRoundFromInfo(Map<?, ?> roundInfoData) {
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

    // Metodo di supporto per creare un TennisTournament a partire dai dati forniti
    private TennisTournament getTournamentFromJson(Map<?, ?> tournamentData, Map<?, ?> seasonData) {
        if (tournamentData == null) {
            return null; // Ritorna null se il JSON del torneo è assente
        }

        Map<?, ?> uniqueTournament = (Map<?, ?>) tournamentData.get("uniqueTournament");
        if (uniqueTournament == null) {
            return null; // Ritorna null se uniqueTournament è assente
        }

        //TennisTournament tournament = new TennisTournament();
        TennisTournamentDetails details = new TennisTournamentDetails();

        // Mappa il nome del torneo
        tournament.setName((String) uniqueTournament.get("name"));

        // Mappa la categoria del torneo
        Map<?, ?> categoryJson = (Map<?, ?>) uniqueTournament.get("category");
        if (categoryJson != null) {
            String categoryName = (String) categoryJson.get("name");
            tournament.setCategory(Category.fromDescription(categoryName));
        }

        // Mappa la superficie
        tournament.setSurface((String) uniqueTournament.get("groundType"));

        // Mappa l'anno dal campo season
        if (seasonData != null) {
            // Se l'anno è una Stringa, proviamo a convertirla in Integer
            Object yearObj = seasonData.get("year");
            if (yearObj != null) {
                try {
                    // Se year è una String, proviamo a convertirlo in Integer
                    int year = Integer.parseInt(yearObj.toString());
                    details.getSeasons().add(new Season(year, ""));
                } catch (NumberFormatException e) {
                    // Gestisci l'errore di parsing se la stringa non è un numero
                    e.printStackTrace();
                }
            }
        }

        return tournament;
    }

}
