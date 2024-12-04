package org.giannico.russo.persistence.repository;

import jakarta.enterprise.context.ApplicationScoped;
import org.giannico.russo.persistence.model.TennisMatch;

import java.util.ArrayList;
import java.util.List;

@ApplicationScoped
public class TennisMatchRepository {
    public List<TennisMatch> parseTennisMatches(String json) {
        // Parse the JSON and return a list of TennisMatch objects
        List<TennisMatch> tennisMatches = new ArrayList<>();
        return tennisMatches;
    }
}
