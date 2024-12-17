package org.giannico.russo.persistence.model;

import java.time.LocalDate;
import java.util.List;

public class TennisTournamentDetails {
    private List<Season> seasons;
    private LocalDate startDate;
    private LocalDate endDate;
    private List<Group> groups;
    private List<TennisPlayer> titleHolders;
    private String lastCountryWinner;

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public List<Group> getGroups() {
        return groups;
    }

    public void setGroups(List<Group> groups) {
        this.groups = groups;
    }

    public List<TennisPlayer> getTitleHolders() {
        return titleHolders;
    }

    public void setTitleHolders(List<TennisPlayer> titleHolders) {
        this.titleHolders = titleHolders;
    }

    public List<Season> getSeasons() {
        return seasons;
    }

    public void setSeasons(List<Season> seasons) {
        this.seasons = seasons;
    }

    public String getLastCountryWinner() {
        return lastCountryWinner;
    }

    public void setLastCountryWinner(String lastCountryWinner) {
        this.lastCountryWinner = lastCountryWinner;
    }
}
