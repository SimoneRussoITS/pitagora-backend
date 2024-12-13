package org.giannico.russo.persistence.model;

public class Season {
    private int year;
    private String location;

    public Season(int year, String s) {
        this.year = year;
        this.location = s;
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
}
