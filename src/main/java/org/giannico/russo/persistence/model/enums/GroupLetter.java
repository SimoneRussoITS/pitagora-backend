package org.giannico.russo.persistence.model.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum GroupLetter {
    A("A"),
    B("B"),
    C("C"),
    D("D"),
    E("E"),
    F("F"),
    G("G"),
    H("H");

    private final String description;

    GroupLetter(String description) {
        this.description = description;
    }

    @JsonValue
    public String getDescription() {
        return description;
    }

    @JsonCreator
    public static GroupLetter fromDescription(String description) {
        for (GroupLetter groupLetter : GroupLetter.values()) {
            if (groupLetter.getDescription().equals(description)) {
                return groupLetter;
            }
        }
        throw new IllegalArgumentException("Unknown group letter description: " + description);
    }
}
