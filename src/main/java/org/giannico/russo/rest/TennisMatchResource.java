package org.giannico.russo.rest;

import com.fasterxml.jackson.core.JsonProcessingException;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import org.giannico.russo.persistence.model.TennisMatch;
import org.giannico.russo.service.TennisMatchService;

import java.util.List;

@Path("/tennis/match")
public class TennisMatchResource {
    private final TennisMatchService tennisMatchService;

    public TennisMatchResource(TennisMatchService tennisMatchService) {
        this.tennisMatchService = tennisMatchService;
    }

    @GET
    @Path("/today")
    @Produces(MediaType.APPLICATION_JSON)
    public List<TennisMatch> getTodayTennisMatches() throws JsonProcessingException {
        return tennisMatchService.getTodayTennisMatches();
    }
}
