package org.giannico.russo.persistence.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.quarkus.mongodb.panache.common.MongoEntity;
import org.bson.types.ObjectId;
import org.giannico.russo.persistence.model.enums.Category;
import org.giannico.russo.persistence.model.enums.Status;

import java.time.LocalDate;
import java.util.List;

@MongoEntity(collection = "tennis-tournament")
public class TennisTournament {
    private String id;
    private String name;
    private int year;
    private String location;
    private LocalDate startDate;
    private LocalDate endDate;
    private String surface;
    private Category category;
    private List<Group> groups;
    private String winner;
    private Status status;

    // Costruttore per il parsing del JSON
    @JsonCreator
    public TennisTournament(
            @JsonProperty("name") String name,
            @JsonProperty("year") int year,
            @JsonProperty("location") String location,
            @JsonProperty("startDate") LocalDate startDate,
            @JsonProperty("endDate") LocalDate endDate,
            @JsonProperty("surface") String surface,
            @JsonProperty("category") Category category,
            @JsonProperty("groups") List<Group> groups,
            @JsonProperty("winner") String winner,
            @JsonProperty("status") Status status) {
        this.name = name;
        this.year = year;
        this.location = location;
        this.startDate = startDate;
        this.endDate = endDate;
        this.surface = surface;
        this.category = category;
        this.groups = groups;
        this.winner = winner;
        this.status = status;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

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

    public String getSurface() {
        return surface;
    }

    public void setSurface(String surface) {
        this.surface = surface;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public List<Group> getGroups() {
        return groups;
    }

    public void setGroups(List<Group> groups) {
        this.groups = groups;
    }

    public String getWinner() {
        return winner;
    }

    public void setWinner(String winner) {
        this.winner = winner;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }
}
