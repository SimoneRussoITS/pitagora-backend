package org.giannico.russo.service;

import io.quarkus.mongodb.panache.PanacheMongoRepository;
import jakarta.enterprise.context.ApplicationScoped;
import org.giannico.russo.client.SofascoreClient;
import org.giannico.russo.persistence.model.TennisMatch;

import java.time.LocalDate;

@ApplicationScoped
public class TennisMatchService implements PanacheMongoRepository<TennisMatch> {
    private final SofascoreClient sofascoreClient;

    public TennisMatchService(SofascoreClient sofascoreClient) {
        this.sofascoreClient = sofascoreClient;
    }

    public String getTodayTennisMatches() {
        String today = LocalDate.now().plusDays(1).toString();
        String endpoint = "api/v1/sport/tennis/scheduled-events/" + today;
        return sofascoreClient.fetchTennisData(endpoint);
    }
}
