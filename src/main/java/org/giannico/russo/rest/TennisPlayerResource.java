package org.giannico.russo.rest;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import org.giannico.russo.service.TennisPlayerService;

@Path("/tennis/player")
public class TennisPlayerResource {
    private final TennisPlayerService tennisPlayerService;

    public TennisPlayerResource(TennisPlayerService tennisPlayerService) {
        this.tennisPlayerService = tennisPlayerService;
    }

    @GET
    @Path("/atp-all")
    @Produces(MediaType.APPLICATION_JSON)
    public String getAllTennisPlayersInAtpRanking(){
        return tennisPlayerService.getAllTennisPlayersInAtpRanking();
    }
}
