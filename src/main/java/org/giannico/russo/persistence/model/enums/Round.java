package org.giannico.russo.persistence.model.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum Round {
    QUALIFYING_ROUND_1("Qualifying Round 1"),  // Primo turno delle qualificazioni
    QUALIFYING_ROUND_2("Qualifying Round 2"),  // Secondo turno delle qualificazioni
    QUALIFYING_ROUND_3("Qualifying Round 3"),  // Terzo turno delle qualificazioni
    ROUND_ROBIN("Round Robin"),         // Fase a gironi (usata in ATP/WTA Finals o Coppa Davis)
    FIRST_ROUND("First Round"),         // Primo turno
    SECOND_ROUND("Second Round"),        // Secondo turno
    THIRD_ROUND("Third Round"),         // Terzo turno
    FOURTH_ROUND("Fourth Round"),        // Quarto turno (es. "Ottavi di finale" nei tornei Slam)
    QUARTER_FINAL("Quarter Round"),       // Quarti di finale
    SEMI_FINAL("Semifinal"),          // Semifinale
    FINAL("Final"),               // Finale
    PLAY_OFF("Play-Off"),            // Spareggio (es. Coppa Davis per qualificarsi)
    BRONZE_MEDAL_MATCH("Bronze Medal Match");  // Match per il terzo posto (es. Olimpiadi)

    private final String description;

    Round(String description) {
        this.description = description;
    }

    @JsonValue
    public String getDescription() {
        return description;
    }

    @JsonCreator
    public static Round fromDescription(String description) {
        for (Round round : Round.values()) {
            if (round.getDescription().equals(description)) {
                return round;
            }
        }
        throw new IllegalArgumentException("Unknown round description: " + description);
    }
}

