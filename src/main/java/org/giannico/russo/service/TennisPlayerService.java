package org.giannico.russo.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import jakarta.enterprise.context.ApplicationScoped;
import org.giannico.russo.client.SofascoreClient;
import org.giannico.russo.persistence.model.TennisPlayer;
import org.giannico.russo.persistence.repository.TennisPlayerRepository;
import org.giannico.russo.rest.model.RankingsResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@ApplicationScoped
public class TennisPlayerService {
    private final SofascoreClient sofascoreClient;
    private final TennisPlayerRepository tennisPlayerRepository;
    private final ObjectMapper objectMapper;

    public TennisPlayerService(SofascoreClient sofascoreClient, TennisPlayerRepository tennisPlayerRepository, ObjectMapper objectMapper) {
        this.sofascoreClient = sofascoreClient;
        this.tennisPlayerRepository = tennisPlayerRepository;
        this.objectMapper = objectMapper;
    }

    public List<TennisPlayer> getAllTennisPlayersInAtpRanking() throws JsonProcessingException {
        // Get the list of the 500 players in ATP rank
        String rankingsEndpoint = "api/v1/rankings/type/5";
        String rankingsJson = sofascoreClient.fetchTennisData(rankingsEndpoint);

        // Map each player in format sofascoreId-rank
        Map<Integer, Integer> idRankMap = mapPlayersIdRank(rankingsJson);

        return getTennisPlayersList(idRankMap);
    }

    private Map<Integer, Integer> mapPlayersIdRank(String rankingJson) throws JsonProcessingException {
        RankingsResponse rankingResponse = objectMapper.readValue(rankingJson, RankingsResponse.class);
        List<Map<String, Object>> rankings = rankingResponse.getRankings();

        Map<Integer, Integer> idRankMap = new HashMap<>();
        for (Map<String, Object> playerRanking : rankings) {
            Map<String, Object> player = (Map<String, Object>) playerRanking.get("team");
            Integer playerId = (Integer) player.get("id");
            Integer playerRank = (Integer) playerRanking.get("ranking");
            idRankMap.put(playerId, playerRank);
        }

        return idRankMap;
    }

    private List<TennisPlayer> getTennisPlayersList(Map<Integer, Integer> idRankMap) throws JsonProcessingException {
        List<TennisPlayer> tennisPlayers = new ArrayList<>();
        String playerEndpoint = "api/v1/team/";

        for (Map.Entry<Integer, Integer> entry : idRankMap.entrySet()){
            Integer playerId = entry.getKey();
            Integer rank = entry.getValue();

            String playerJson  = sofascoreClient.fetchTennisData(playerEndpoint + playerId);
            TennisPlayer player = tennisPlayerRepository.parseJsonToTennisPlayer(playerJson);
            tennisPlayers.add(player);
        }
        return tennisPlayers;
    }
}
