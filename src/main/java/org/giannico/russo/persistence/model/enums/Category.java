package org.giannico.russo.persistence.model.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum Category {
    GRAND_SLAM("Grand Slam"),               // Tornei del Grande Slam (Australian Open, Roland Garros, Wimbledon, US Open)
    ATP_MASTERS_1000("ATP Masters 1000"),   // Tornei Masters 1000 dell'ATP (es. Indian Wells, Monte Carlo, Roma)
    ATP_500("ATP 500"),                     // Tornei ATP 500 (es. Rotterdam, Barcellona, Vienna)
    ATP_250("ATP 250"),                     // Tornei ATP 250 (es. Doha, Estoril, Ginevra)
    ATP_FINALS("ATP Finals"),               // ATP Finals (torneo tra i migliori 8 dell’anno)
    ATP_CHALLENGER("ATP Challenger"),       // Tornei del circuito Challenger, livello inferiore rispetto al circuito principale
    ITF_FUTURES("ITF Futures"),             // Tornei ITF Futures, il livello più basso per punti e premi
    DAVIS_CUP("Davis Cup"),                 // Coppa Davis (competizione a squadre maschile)
    OLYMPICS("Olympics"),                   // Tornei olimpici di tennis
    EXHIBITION("Exhibition");               // Tornei esibizione (senza punti ATP/WTA)

    private final String description;

    Category(String description) {
        this.description = description;
    }

    @JsonValue
    public String getDescription() {
        return description;
    }

    @JsonCreator
    public static Category fromDescription(String description) {
        for (Category category : Category.values()) {
            if (category.getDescription().equals(description)) {
                return category;
            }
        }
        throw new IllegalArgumentException("Unknown category description: " + description);
    }
}

