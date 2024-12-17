package org.giannico.russo.persistence.model.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

import java.util.Map;

public enum Category {
    GRAND_SLAM("Grand Slam"),                          // Tornei del Grande Slam (Australian Open, Roland Garros, Wimbledon, US Open)
    ATP_MASTERS_1000("ATP Masters 1000"),              // Tornei Masters 1000 dell'ATP (es. Indian Wells, Monte Carlo, Roma)
    BILLIE_JEAN_KING_CUP("Billie Jean King Cup"),      // Coppa Billie Jean
    ATP("ATP"),                                        // Tornei ATP (es. Acapulco, Marsiglia, San Pietroburgo)
    ATP_500("ATP 500"),                                // Tornei ATP 500 (es. Rotterdam, Barcellona, Vienna)
    ATP_250("ATP 250"),                                // Tornei ATP 250 (es. Doha, Estoril, Ginevra)
    ATP_FINALS("ATP Finals"),                          // ATP Finals (torneo tra i migliori 8 dell’anno)
    CHALLENGER("Challenger"),                          // Tornei del circuito Challenger, livello inferiore rispetto al circuito principale
    ITF_FUTURES("ITF Futures"),                        // Tornei ITF Futures, il livello più basso per punti e premi
    ITF_MEN("ITF Men"),                                // Tornei ITF maschili
    ITF_WOMEN("ITF Women"),                            // Tornei ITF femminili
    WTA("WTA"),                                        // Tornei WTA (es. Acapulco, Marsiglia, San Pietroburgo)
    WTA_1000("WTA 1000"),                              // Tornei WTA 1000 (es. Indian Wells, Miami, Madrid)
    WTA_500("WTA 500"),                                // Tornei WTA 500 (es. Stoccarda, Eastbourne, Pechino)
    WTA_250("WTA 250"),                                // Tornei WTA 250 (es. Bogotà, Palermo, Linz)
    WTA_125("WTA 125"),                                // Tornei WTA 125
    WTA_FINALS("WTA Finals"),                          // WTA Finals (torneo tra le migliori 8 dell’anno)
    WTA_ELITE_TROPHY("WTA Elite Trophy"),              // WTA Elite Trophy (torneo tra le migliori
    WTA_CHALLENGER("WTA Challenger"),                  // Tornei del circuito Challenger WTA
    DAVIS_CUP("Davis Cup"),                            // Coppa Davis (competizione a squadre maschile)
    OLYMPICS("Olympics"),                              // Tornei olimpici di tennis
    EXHIBITION("Exhibition"),                          // Tornei esibizione (senza punti ATP/WTA)
    IN_PROGRESS("In Progress"),                        // Tornei in corso
    UNITED_CUP("United Cup"),                          // United Cup (torneo di tennis tra nazioni)
    UNKNOWN("Unknown");                                // Categoria sconosciuta

    private final String description;

    Category(String description) {
        this.description = description;
    }

    @JsonValue
    public String getDescription() {
        return description;
    }

    // Modifica il metodo fromDescription per gestire anche oggetti JSON, oltre che stringhe
    @JsonCreator
    public static Category fromDescription(Object value) {
        // Caso in cui il valore sia una stringa
        if (value instanceof String) {
            String description = (String) value;
            for (Category category : Category.values()) {
                if (category.getDescription().equals(description)) {
                    return category;
                }
            }
        }
        // Caso in cui il valore sia un oggetto complesso con il campo 'name'
        else if (value instanceof Map) {
            Map<String, Object> map = (Map<String, Object>) value;
            String description = (String) map.get("name");  // Assumendo che la categoria sia nel campo 'name'
            for (Category category : Category.values()) {
                if (category.getDescription().equals(description)) {
                    return category;
                }
            }
        }

        // Se non trovi una corrispondenza
        return UNKNOWN;
    }
}

