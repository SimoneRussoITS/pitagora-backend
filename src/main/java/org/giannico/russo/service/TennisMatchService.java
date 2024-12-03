package org.giannico.russo.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import io.quarkus.mongodb.panache.PanacheMongoRepository;
import jakarta.enterprise.context.ApplicationScoped;
import org.giannico.russo.client.SofascoreClient;
import org.giannico.russo.persistence.model.TennisMatch;

import java.time.LocalDate;
import java.util.List;

@ApplicationScoped
public class TennisMatchService implements PanacheMongoRepository<TennisMatch> {
    private final SofascoreClient sofascoreClient;

    public TennisMatchService(SofascoreClient sofascoreClient) {
        this.sofascoreClient = sofascoreClient;
    }

    public String getTodayTennisMatches() {
        String today = LocalDate.now().toString();
        String endpoint = "api/v1/sport/tennis/scheduled-events/" + today;
        return sofascoreClient.fetchTennisData(endpoint);
    }
}
