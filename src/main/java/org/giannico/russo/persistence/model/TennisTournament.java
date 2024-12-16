package org.giannico.russo.persistence.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.quarkus.mongodb.panache.common.MongoEntity;
import org.giannico.russo.persistence.model.enums.Category;
import org.giannico.russo.persistence.model.enums.Status;
import org.giannico.russo.persistence.repository.TennisPlayerRepository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@MongoEntity(collection = "tennis-tournament")
public class TennisTournament {
    private final TennisPlayerRepository tennisPlayerRepository = new TennisPlayerRepository(null);

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
        Object titleHolders = getTitlesHoldersFromUniqueTournamentData((Map<?, ?>) tournamentData);
        if (titleHolders instanceof String) {
            this.details.setLastCountryWinner((String) titleHolders);
        } else { // Altrimenti è una lista di giocatori
            this.details.setTitleHolders((List<TennisPlayer>) titleHolders);
        }
        details.setStartDate(getStartDateFromUniqueTournamentData((Map<?, ?>) tournamentData));
        details.setEndDate(getEndDateFromUniqueTournamentData((Map<?, ?>) tournamentData));
        // Imposto lo status del torneo
        if (details.getEndDate().isBefore(LocalDate.now())) {
            this.status = Status.FINISHED;
        } else if (details.getStartDate().isAfter(LocalDate.now())) {
            this.status = Status.SCHEDULED;
        } else { // Altrimenti il torneo è in corso
            this.status = Status.IN_PROGRESS;
        }
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
    private Object getTitlesHoldersFromUniqueTournamentData(Map<?, ?> tournamentData) {
        // Creo una lista di giocatori in cui inserire il titleHolder o i titleHolders
        List<TennisPlayer> titleHolders = new ArrayList<>();

        // Estraggo il titleHolder dall'oggetto JSON
        Map<?, ?> titleHolderJson = (Map<?, ?>) tournamentData.get("titleHolder");
        if (titleHolderJson != null) {
            // Estraggo lo slug del titleHolder
            String slug = (String) titleHolderJson.get("slug"); // ex: "rafael-nadal" o "vavassori-a-errani-s"
            // Divido lo slug in parti
            List<String> slugParts = List.of(slug.split("-")); // ex: "[sinner, jannik]" o "[vavassori, a, errani, s]"
            if (!slugParts.isEmpty()) {
                if (slugParts.size() < 2) {
                    // Se lo slug ha meno di due parti, non è un giocatore ma una nazione. ex: "italy"
                    String country = slugParts.getFirst();
                    return country;
                } else {
                    // Se lo slug ha due parti, è un giocatore. ex: "rafael-nadal"
                    if (slugParts.size() == 2) {
                        String firstName = slugParts.getLast();
                        String lastName = slugParts.getFirst();
                        // query mongoDB per trovare il giocatore  (cerco per nome e cognome, ignorando le maiuscole)
                        TennisPlayer titleHolder = tennisPlayerRepository.findByFirstNameAndLastName(firstName, lastName);
                        if (titleHolder == null) {
                            System.out.println("Giocatore non trovato: " + firstName + " " + lastName);
                            titleHolder = new TennisPlayer();
                            titleHolder.setFirstName(firstName);
                            titleHolder.setLastName(firstName);
                        }
                        titleHolders.add(titleHolder);
                    } else {
                        // Se lo slug ha più di due parti, è una coppia di giocatori. ex: "vavassori-a-errani-s"
                        for (String slugPart : slugParts) {
                            // Se la parte dello slug ha più di una lettera, è un cognome
                            if (slugPart.length() > 1) {
                                String lastName = slugPart;
                                // query mongoDB per trovare il giocatore
                                TennisPlayer titleHolder = tennisPlayerRepository.findByLastName(lastName);
                                if (titleHolder == null) {
                                    System.out.println("Giocatore non trovato: " + lastName);
                                    titleHolder = new TennisPlayer();
                                    titleHolder.setLastName(lastName);
                                }
                                titleHolders.add(titleHolder);
                            }
                        }
                    }
                }
            }
            return titleHolders;
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
