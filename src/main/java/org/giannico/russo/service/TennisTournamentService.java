package org.giannico.russo.service;

import jakarta.enterprise.context.ApplicationScoped;
import org.giannico.russo.client.SofascoreClient;
import org.giannico.russo.persistence.model.TennisTournament;
import org.giannico.russo.persistence.repository.TennisTournamentRepository;

import java.util.List;

@ApplicationScoped
public class TennisTournamentService {
    private final SofascoreClient sofascoreClient;
    private final TennisTournamentRepository tennisTournamentRepository;

    public TennisTournamentService(SofascoreClient sofascoreClient, TennisTournamentRepository tennisTournamentRepository) {
        this.sofascoreClient = sofascoreClient;
        this.tennisTournamentRepository = tennisTournamentRepository;
    }

    public List<TennisTournament> getTennisTournaments() {
        String endpoint = "api/v1/config/sport/default-unique-tournaments/IT/tennis";
        String json = sofascoreClient.fetchTennisData(endpoint);
        return tennisTournamentRepository.parseTennisTournaments(json);
    }
}
