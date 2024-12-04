package org.giannico.russo.persistence.model.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum SetNumber {
    FIRST("1"),
    SECOND("2"),
    THIRD("3"),
    FOURTH("4"),
    FIFTH("5");

    private final String description;

    SetNumber(String description) {
        this.description = description;
    }

    @JsonValue
    public String getDescription() {
        return description;
    }

    @JsonCreator
    public static SetNumber fromDescription(String description) {
        for (SetNumber setNumber : SetNumber.values()) {
            if (setNumber.getDescription().equals(description)) {
                return setNumber;
            }
        }
        throw new IllegalArgumentException("Unknown set number description: " + description);
    }
}
