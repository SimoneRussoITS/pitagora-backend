package org.giannico.russo.persistence.model;

import org.bson.types.ObjectId;
import org.giannico.russo.persistence.model.enums.SetNumber;

public class Set {
    private ObjectId setId;
    private ObjectId matchResultId;
    private SetNumber set;
    private int homePlayerWonGames;
    private int awayPlayerWonGames;
    private boolean tieBreak;
    private int homePlayerTieBreakPoints;
    private int awayPlayerTieBreakPoints;

    public Set(ObjectId setId, ObjectId matchResultId, SetNumber set, int homePlayerWonGames, int awayPlayerWonGames, boolean tieBreak, int homePlayerTieBreakPoints, int awayPlayerTieBreakPoints) {
        this.setId = setId;
        this.matchResultId = matchResultId;
        this.set = set;
        this.homePlayerWonGames = homePlayerWonGames;
        this.awayPlayerWonGames = awayPlayerWonGames;
        this.tieBreak = tieBreak;
        this.homePlayerTieBreakPoints = homePlayerTieBreakPoints;
        this.awayPlayerTieBreakPoints = awayPlayerTieBreakPoints;
    }


    public ObjectId getSetId() {
        return setId;
    }

    public void setSetId(ObjectId setId) {
        this.setId = setId;
    }

    public ObjectId getMatchResultId() {
        return matchResultId;
    }

    public void setMatchResultId(ObjectId matchResultId) {
        this.matchResultId = matchResultId;
    }

    public SetNumber getSet() {
        return set;
    }

    public void setSet(SetNumber set) {
        this.set = set;
    }

    public int getHomePlayerWonGames() {
        return homePlayerWonGames;
    }

    public void setHomePlayerWonGames(int homePlayerWonGames) {
        this.homePlayerWonGames = homePlayerWonGames;
    }

    public int getAwayPlayerWonGames() {
        return awayPlayerWonGames;
    }

    public void setAwayPlayerWonGames(int awayPlayerWonGames) {
        this.awayPlayerWonGames = awayPlayerWonGames;
    }

    public boolean isTieBreak() {
        return tieBreak;
    }

    public void setTieBreak(boolean tieBreak) {
        this.tieBreak = tieBreak;
    }

    public int getHomePlayerTieBreakPoints() {
        return homePlayerTieBreakPoints;
    }

    public void setHomePlayerTieBreakPoints(int homePlayerTieBreakPoints) {
        this.homePlayerTieBreakPoints = homePlayerTieBreakPoints;
    }

    public int getAwayPlayerTieBreakPoints() {
        return awayPlayerTieBreakPoints;
    }

    public void setAwayPlayerTieBreakPoints(int awayPlayerTieBreakPoints) {
        this.awayPlayerTieBreakPoints = awayPlayerTieBreakPoints;
    }
}
