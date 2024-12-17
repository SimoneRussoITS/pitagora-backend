package org.giannico.russo.persistence.model;

import org.giannico.russo.persistence.model.enums.GroupLetter;

import java.util.List;

public class Group {
    private GroupLetter groupLetter;
    private List<String> teams;
    private List<TennisPlayer> players;

    public GroupLetter getGroupLetter() {
        return groupLetter;
    }

    public void setGroupLetter(GroupLetter groupLetter) {
        this.groupLetter = groupLetter;
    }

    public List<String> getTeams() {
        return teams;
    }

    public void setTeams(List<String> teams) {
        this.teams = teams;
    }

    public List<TennisPlayer> getPlayers() {
        return players;
    }

    public void setPlayers(List<TennisPlayer> players) {
        this.players = players;
    }
}
