package org.giannico.russo.persistence.model;

import org.giannico.russo.persistence.model.enums.GroupLetter;

import java.util.List;

public class Group {
    private GroupLetter groupLetter;
    private String team;
    private List<TennisPlayer> players;
}
