package org.giannico.russo.rest;

import com.fasterxml.jackson.core.JsonProcessingException;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.giannico.russo.persistence.model.TennisMatch;
import org.giannico.russo.persistence.repository.TennisMatchRepository;

import java.util.List;

@Path("/tennis/match")
public class TennisMatchResource {
    private final TennisMatchRepository tennisMatchRepository;

    public TennisMatchResource(TennisMatchRepository tennisMatchRepository) {
        this.tennisMatchRepository = tennisMatchRepository;
    }

    @GET
    @Path("/today")
    @Produces(MediaType.APPLICATION_JSON)
    public String getTodayTennisMatches() throws JsonProcessingException {
        return tennisMatchRepository.getTodayTennisMatches();
    }
}
