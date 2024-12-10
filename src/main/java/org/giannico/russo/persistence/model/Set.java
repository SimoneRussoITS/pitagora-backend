package org.giannico.russo.persistence.model;

import org.giannico.russo.persistence.model.enums.SetNumber;

public class Set {
    private SetNumber set;
    private int homePlayerWonGames;
    private int awayPlayerWonGames;

    public Set(int i, int homeGames, int awayGames) {
        this.set = SetNumber.values()[i];
        this.homePlayerWonGames = homeGames;
        this.awayPlayerWonGames = awayGames;
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
}
