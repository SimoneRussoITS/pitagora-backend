package org.giannico.russo.rest;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import org.giannico.russo.persistence.model.TennisTournament;
import org.giannico.russo.service.TennisTournamentService;

import java.util.List;

@Path("/tennis/tournament")
public class TennisTournamentResource {
    private final TennisTournamentService tennisTournamentService;

    public TennisTournamentResource(TennisTournamentService tennisTournamentService) {
        this.tennisTournamentService = tennisTournamentService;
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<TennisTournament> getTennisTournaments() {
        return tennisTournamentService.getTennisTournaments();
    }
}
