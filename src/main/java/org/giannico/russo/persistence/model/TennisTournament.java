package org.giannico.russo.persistence.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.quarkus.mongodb.panache.common.MongoEntity;
import org.giannico.russo.persistence.model.enums.Category;
import org.giannico.russo.persistence.model.enums.Status;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Map;

@MongoEntity(collection = "tennis-tournament")
public class TennisTournament {
    @JsonIgnore
    private String id;
    private String name;
    private String surface;
    private Category category;
    private Status status;
    private TennisTournamentDetails details;

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

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public TennisTournamentDetails getDetails() {
        return details;
    }

    public void setDetails(TennisTournamentDetails details) {
        this.details = details;
    }

    @JsonCreator
    public TennisTournament(@JsonProperty("uniqueTournament") Object tournamentData) {
        this.name = getNameFromUniqueTournamentData((Map<?, ?>) tournamentData);
        this.surface = getSurfaceFromUniqueTournamentData((Map<?, ?>) tournamentData);
        this.category = getCategoryFromUniqueTournamentData((Map<?, ?>) tournamentData);
        this.details = new TennisTournamentDetails();
        details.setTitleHolder((String) (getTitlesHolderFromUniqueTournamentData((Map<?, ?>) tournamentData)));
        details.setStartDate(getStartDateFromUniqueTournamentData((Map<?, ?>) tournamentData));
        details.setEndDate(getEndDateFromUniqueTournamentData((Map<?, ?>) tournamentData));
    }

    // Metodo di supporto per deserializzare la data di fine da un oggetto JSON
    private LocalDate getEndDateFromUniqueTournamentData(Map<?, ?> tournamentData) {
        Integer endDate = (Integer) tournamentData.get("endDateTimestamp");
        return convertTimestampToLocalDate(endDate).toLocalDate();
    }

    // Metodo di supporto per deserializzare la data di inizio da un oggetto JSON
    private LocalDate getStartDateFromUniqueTournamentData(Map<?, ?> tournamentData) {
        Integer startDate = (Integer) tournamentData.get("startDateTimestamp");
        return convertTimestampToLocalDate(startDate).toLocalDate();
    }

    // Metodo di supporto per deserializzare la superficie da un oggetto JSON
    private String getSurfaceFromUniqueTournamentData(Map<?, ?> tournamentData) {
        return (String) tournamentData.get("groundType");
    }

    // Metodo di supporto per convertire il timestamp Unix in LocalDateTime
    private LocalDateTime convertTimestampToLocalDate(Integer timestamp) {
        return LocalDateTime.ofEpochSecond(timestamp, 0, ZoneOffset.UTC);
    }

    // Metodo di supporto per deserializzare il nome da un oggetto JSON
    private String getNameFromUniqueTournamentData(Map<?, ?> tournamentData) {
        return (String) tournamentData.get("name");
    }

    // Metodo di supporto per deserializzare il titleHolder da un oggetto JSON
    private Object getTitlesHolderFromUniqueTournamentData(Map<?, ?> tournamentData) {
        Map<?, ?> titleHolderJson = (Map<?, ?>) tournamentData.get("titleHolder");
        if (titleHolderJson != null) {
            return titleHolderJson.get("name");
        } else {
            return null;
        }
    }

    // Metodo di supporto per deserializzare la categoria da un oggetto JSON
    private Category getCategoryFromUniqueTournamentData(Map<?, ?> uniqueTournament) {
        Map<?, ?> categoryJson = (Map<?, ?>) uniqueTournament.get("category");
        if (categoryJson != null) {
            String categoryName = (String) categoryJson.get("name");
            return Category.fromDescription(categoryName);
        } else {
            return Category.UNKNOWN;
        }
    }
}
