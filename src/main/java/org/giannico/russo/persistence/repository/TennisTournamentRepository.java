package org.giannico.russo.persistence.repository;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.enterprise.context.ApplicationScoped;
import org.giannico.russo.persistence.model.TennisMatch;
import org.giannico.russo.persistence.model.TennisTournament;
import org.giannico.russo.rest.model.UniqueTournamentsResponse;

import java.util.List;
import java.util.Map;

@ApplicationScoped
public class TennisTournamentRepository {
    private final ObjectMapper objectMapper;

    public TennisTournamentRepository(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    public List<TennisTournament> parseTennisTournaments(String json) {
        try {
            // Deserializza l'oggetto principale (UniqueTournamentsResponse) che contiene "uniqueTournaments"
            UniqueTournamentsResponse uniqueTournamentsResponse = objectMapper.readValue(json, UniqueTournamentsResponse.class);
            // Estrai l'array di eventi
            List<Map<String, Object>> uniqueTournaments = uniqueTournamentsResponse.getUniqueTournaments();
            // Puoi ora convertire ogni elemento dell'array (evento) in un oggetto TennisMatch
            List<TennisTournament> tennisTournaments = objectMapper.convertValue(
                    uniqueTournaments,
                    objectMapper
                            .getTypeFactory()
                            .constructCollectionType(List.class, TennisTournament.class)
            );
            return tennisTournaments;
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Errore durante il parsing del JSON", e);
        }
    }
}
