package org.giannico.russo.service;

import io.quarkus.mongodb.panache.PanacheMongoRepository;
import jakarta.enterprise.context.ApplicationScoped;
import org.giannico.russo.client.SofascoreClient;
import org.giannico.russo.persistence.model.TennisMatch;
import org.giannico.russo.persistence.repository.TennisMatchRepository;

import java.time.LocalDate;
import java.util.List;

@ApplicationScoped
public class TennisMatchService implements PanacheMongoRepository<TennisMatch> {
    private final SofascoreClient sofascoreClient;
    private final TennisMatchRepository tennisMatchRepository;

    public TennisMatchService(SofascoreClient sofascoreClient, TennisMatchRepository tennisMatchRepository) {
        this.sofascoreClient = sofascoreClient;
        this.tennisMatchRepository = tennisMatchRepository;
    }

    public List<TennisMatch> getTodayTennisMatches() {
        String today = LocalDate.now().plusDays(1).toString();
        String endpoint = "api/v1/sport/tennis/scheduled-events/" + today;
        String json = sofascoreClient.fetchTennisData(endpoint);
        return tennisMatchRepository.parseTennisMatches(json);
    }
}
