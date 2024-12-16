package org.giannico.russo.persistence.repository;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.enterprise.context.ApplicationScoped;
import org.giannico.russo.persistence.model.TennisTournament;
import org.giannico.russo.rest.model.GroupsResponse;
import org.giannico.russo.rest.model.InfoResponse;
import org.giannico.russo.rest.model.SeasonsResponse;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@ApplicationScoped
public class TennisTournamentRepository {
    private final ObjectMapper objectMapper;

    public TennisTournamentRepository(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    public List<Integer> parseTournamentIdsFromJson(String tournamentsJson) {
        try {
            // Deserializza l'oggetto principale (GroupsResponse) che contiene "groups"
            GroupsResponse groupsResponse = objectMapper.readValue(tournamentsJson, GroupsResponse.class);

            // Estrai l'array di gruppi
            List<Map<String, Object>> groups = groupsResponse.getGroups();

            // Converte ogni elemento dell'array (gruppo) in un intero, estraendo l'ID
            List<Integer> ids = groups.stream()
                    .flatMap(group -> ((List<Map<String, Object>>) group.get("uniqueTournaments")).stream())
                    .map(tournament -> (Integer) tournament.get("id"))
                    .toList();

            return ids;
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Errore durante il parsing del JSON", e);
        }
    }

    public TennisTournament parseTournamentFromJson(String tournamentJson) {
        try {
            TennisTournament tennisTournament = objectMapper.readValue(tournamentJson, TennisTournament.class);
            return tennisTournament;

        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Errore durante il parsing del JSON", e);
        }
    }

    public List<Integer> parseTournamentYearsFromJson(String tournamentYearsJson) {
        try {
            // Deserializza l'oggetto principale (SeasonsResponse) che contiene "seasons"
            SeasonsResponse seasonsResponse = objectMapper.readValue(tournamentYearsJson, SeasonsResponse.class);

            // Estrai l'array di stagioni
            List<Map<String, Object>> seasons = seasonsResponse.getSeasons();

            if (seasons == null) {
                return new ArrayList<>();
            }

            // Creo una lista di anni
            List<Integer> years = new ArrayList<>();

            // Ciclo per ogni stagione
            for (Map<String, Object> season : seasons) {
                // Estrai l'anno
                String year = (String) season.get("year");

                // Se l'anno contiene "/" lo splitta e prende entrambi gli anni
                if (year.contains("/")) {
                    String[] split = year.split("/");
                    years.add(Integer.parseInt(split[0]));
                    years.add(Integer.parseInt(split[1]));
                } else {
                    years.add(Integer.parseInt(year));
                }
            }

            return years;
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Errore durante il parsing del JSON", e);
        }
    }

    public List<Integer> parseSeasonIdsFromJson(String tournamentYearsJson) {
        try {
            // Deserializza l'oggetto principale (SeasonsResponse) che contiene "seasons"
            SeasonsResponse seasonsResponse = objectMapper.readValue(tournamentYearsJson, SeasonsResponse.class);

            // Estrai l'array di stagioni
            List<Map<String, Object>> seasons = seasonsResponse.getSeasons();

            if (seasons == null) {
                return new ArrayList<>();
            }

            // Converte ogni elemento dell'array (stagione) in un intero, estraendo l'anno
            List<Integer> ids = seasons.stream()
                    .map(season -> (Integer) season.get("id"))
                    .toList();

            return ids;
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Errore durante il parsing del JSON", e);
        }
    }

    public String parseTournamentLocationFromJson(String tournamentLocationJson) {
        try {
            // Deserializza l'oggetto principale (InfoResponse) che contiene "info"
            InfoResponse infoResponse = objectMapper.readValue(tournamentLocationJson, InfoResponse.class);

            // Estrai la location
            Map<String, Object> info = infoResponse.getInfo();

            // Estrai la città
            String city = (String) info.get("hostCity");

            // Se la città non è presente, restituisci "Not provided"
            return Objects.requireNonNullElse(city, "Not provided");

        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Errore durante il parsing del JSON", e);
        }
    }
}
