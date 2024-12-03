package org.giannico.russo.service;

import jakarta.enterprise.context.ApplicationScoped;
import org.giannico.russo.client.SofascoreClient;

@ApplicationScoped
public class TennisPlayerService {
    private final SofascoreClient sofascoreClient;

    public TennisPlayerService(SofascoreClient sofascoreClient) {
        this.sofascoreClient = sofascoreClient;
    }

    public String getAllTennisPlayersInAtpRanking() {
        String endpoint = "api/v1/rankings/type/5";
        return sofascoreClient.fetchTennisData(endpoint);
    }
}
