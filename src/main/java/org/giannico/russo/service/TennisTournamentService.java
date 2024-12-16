package org.giannico.russo.service;

import jakarta.enterprise.context.ApplicationScoped;
import org.giannico.russo.client.SofascoreClient;
import org.giannico.russo.persistence.model.Season;
import org.giannico.russo.persistence.model.TennisTournament;
import org.giannico.russo.persistence.repository.TennisTournamentRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

@ApplicationScoped
public class TennisTournamentService {
    private static final Logger log = LoggerFactory.getLogger(TennisTournamentService.class);
    private final SofascoreClient sofascoreClient;
    private final TennisTournamentRepository tennisTournamentRepository;

    public TennisTournamentService(SofascoreClient sofascoreClient, TennisTournamentRepository tennisTournamentRepository) {
        this.sofascoreClient = sofascoreClient;
        this.tennisTournamentRepository = tennisTournamentRepository;
    }

    public List<TennisTournament> getTennisTournaments() {
        // Creo una lista di TennisTournament che conterrà i tornei
        List<TennisTournament> tennisTournaments = new ArrayList<>();

        // Id che ci servono delle categorie di tennis di SofaScore
        List<Integer> categories = List.of(3, 74, 72, 76, 79, -100, -101, 785, 1705);

        // Ciclo per ogni categoria
        for (Integer category : categories) {
            // Endpoint per ottenere i tornei
            String tournamentsEndpoint = "api/v1/category/" + category + "/unique-tournaments";
            String tournamentsJson = sofascoreClient.fetchTennisData(tournamentsEndpoint);

            if (tournamentsJson.contains("<")) {
                System.out.println("Errore: " + tournamentsJson);
            }

            // Estrai gli id dei tornei
            List<Integer> tournamentIds = tennisTournamentRepository.parseTournamentIdsFromJson(tournamentsJson);

            // Ciclo per ogni id del torneo
            for (Integer tournamentId : tournamentIds) {
                // Endpoint per ottenere i dettagli del torneo
                String tournamentEndpoint = "api/v1/unique-tournament/" + tournamentId;
                String tournamentJson = sofascoreClient.fetchTennisData(tournamentEndpoint);

                if (tournamentJson.contains("<")) {
                    System.out.println("Errore: " + tournamentJson);
                }

                // Estraggo il torneo (nome, categoria, titleHolder, groundType, startDate, endDate)
                TennisTournament tournament = tennisTournamentRepository.parseTournamentFromJson(tournamentJson);

                // Estraggo la lista di anni in cui si è svolto il torneo con un endpoint diverso
                String tournamentYearsEndpoint = "api/v1/unique-tournament/" + tournamentId + "/seasons";
                String tournamentYearsJson = sofascoreClient.fetchTennisData(tournamentYearsEndpoint);

                if (tournamentYearsJson.contains("<")) {
                    System.out.println("Errore: " + tournamentYearsJson);
                }

                // Estraggo la lista di anni in cui si è svolto il torneo
                List<Integer> tournamentYears = tennisTournamentRepository.parseTournamentYearsFromJson(tournamentYearsJson);

                // Estraggo la lista di id delle stagioni in cui si è svolto il torneo
                List<Integer> seasonIds = tennisTournamentRepository.parseSeasonIdsFromJson(tournamentYearsJson);

                // Creo una lista di locations
                List<String> locations = new ArrayList<>();

                // Ciclo per ogni id della stagione
                for (Integer seasonId : seasonIds) {
                    String tournamentLocationEndpoint = "api/v1/unique-tournament/" + tournamentId + "/season/" + seasonId + "/info";
                    String tournamentLocationJson = sofascoreClient.fetchTennisData(tournamentLocationEndpoint);

                    if (!tournamentLocationJson.contains("404")) {
                        // Estraggo la location del torneo
                        String location = tennisTournamentRepository.parseTournamentLocationFromJson(tournamentLocationJson);

                        // Aggiungi la location alla lista
                        locations.add(location);
                    } else {
                        locations.add("Not provided");
                    }
                }

                // Estraggo le stagioni
                List<Season> seasons = getSeasons(tournamentYears, locations);

                // Aggiungi la lista di stagioni al torneo
                tournament.getDetails().setSeasons(seasons);

                // Aggiungi il torneo alla lista
                tennisTournaments.add(tournament);
            }

            // Stampo un messaggio per ogni categoria
            System.out.println("Categoria " + category + " completata");
        }

        return tennisTournaments;
    }

    private List<Season> getSeasons(List<Integer> tournamentYears, List<String> locations) {
        // Creo una lista di stagioni
        List<Season> seasons = new ArrayList<>();

        // Ciclo per ogni anno in cui si è svolto il torneo
        for (int i = 0; i < tournamentYears.size(); i++) {
            int year = tournamentYears.get(i);
            String location;

            // Assicuro che la location corrisponda correttamente
            if (i < locations.size()) {
                location = locations.get(i);
            } else {
                // Se ci sono più anni rispetto alle location, usa l'ultima location valida
                location = locations.getLast();
            }

            // Crea una stagione
            Season season = new Season(year, location);

            // Aggiungi la stagione alla lista
            seasons.add(season);
        }
        return seasons;
    }
}
