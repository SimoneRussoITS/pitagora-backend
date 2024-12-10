package org.giannico.russo.rest;

import com.fasterxml.jackson.core.JsonProcessingException;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import org.giannico.russo.persistence.model.TennisMatch;
import org.giannico.russo.persistence.model.TennisPlayer;
import org.giannico.russo.service.TennisPlayerService;

import java.util.List;

@Path("/tennis/player")
public class TennisPlayerResource {
    private final TennisPlayerService tennisPlayerService;

    public TennisPlayerResource(TennisPlayerService tennisPlayerService) {
        this.tennisPlayerService = tennisPlayerService;
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<TennisPlayer> getAllTennisPlayersInAtpRanking() throws JsonProcessingException {
        return tennisPlayerService.getAllTennisPlayersInAtpRanking();
    }
}
