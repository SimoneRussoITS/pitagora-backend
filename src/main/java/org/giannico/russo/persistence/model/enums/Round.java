package org.giannico.russo.persistence.model.enums;

public enum Round {
    QUALIFYING_ROUND_1,  // Primo turno delle qualificazioni
    QUALIFYING_ROUND_2,  // Secondo turno delle qualificazioni
    QUALIFYING_ROUND_3,  // Terzo turno delle qualificazioni
    ROUND_ROBIN,         // Fase a gironi (usata in ATP/WTA Finals o Coppa Davis)
    FIRST_ROUND,         // Primo turno
    SECOND_ROUND,        // Secondo turno
    THIRD_ROUND,         // Terzo turno
    FOURTH_ROUND,        // Quarto turno (es. "Ottavi di finale" nei tornei Slam)
    QUARTER_FINAL,       // Quarti di finale
    SEMI_FINAL,          // Semifinale
    FINAL,               // Finale
    PLAY_OFF,            // Spareggio (es. Coppa Davis per qualificarsi)
    BRONZE_MEDAL_MATCH   // Match per il terzo posto (es. Olimpiadi)
}

