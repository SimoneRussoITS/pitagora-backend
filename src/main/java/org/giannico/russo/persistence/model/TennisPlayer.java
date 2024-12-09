package org.giannico.russo.persistence.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.quarkus.mongodb.panache.common.MongoEntity;

import java.time.*;
import java.util.Map;

@MongoEntity(collection = "tennis-player")
public class TennisPlayer {
    @JsonIgnore
    private String id;
    private String firstName;
    private String lastName;
    private LocalDate birthDay;
    private int atpRanking;
    private String nationality;
    private int weight;
    private int height;

    public TennisPlayer() {

    }

    public TennisPlayer(String id, String firstName, String lastName, LocalDate birthDay, int atpRanking, String nationality, int weight, int height) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.birthDay = birthDay;
        this.atpRanking = atpRanking;
        this.nationality = nationality;
        this.weight = weight;
        this.height = height;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public LocalDate getBirthDay() {
        return birthDay;
    }

    public void setBirthDay(LocalDate birthDay) {
        this.birthDay = birthDay;
    }

    public int getAtpRanking() {
        return atpRanking;
    }

    public void setAtpRanking(int atpRanking) {
        this.atpRanking = atpRanking;
    }

    public String getNationality() {
        return nationality;
    }

    public void setNationality(String nationality) {
        this.nationality = nationality;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    String name = "Moeller, Elmer";

    @JsonCreator
    public TennisPlayer(
            @JsonProperty("team") Map<String, Object> team,
            Map<Integer, Integer> idRankMap) {
        this.id = team.get("id").toString();

        this.firstName = team.get("fullName").toString();
        System.out.println(this.firstName);
        this.lastName = team.get("fullName").toString().split(",")[0].trim();

        if (this.firstName.equals(name)){
            System.out.println("1");
        }

        Map<String, Object> playerInfo = (Map<String, Object>) team.get("playerTeamInfo");
        int birthDateTimestamp = (int) playerInfo.get("birthDateTimestamp");
        this.birthDay = Instant.ofEpochMilli(birthDateTimestamp).atZone(ZoneId.systemDefault()).toLocalDate();

        if (this.firstName.equals(name)){
            System.out.println("2");
        }

        this.atpRanking = team.get("ranking") != null
                ? (int) team.get("ranking")
                : (idRankMap != null ? idRankMap.getOrDefault(Integer.parseInt(this.id), 0) : 0);

        if (this.firstName.equals(name)){
            System.out.println("2b");
        }

        this.nationality = ((Map<String, String>) team.get("country")).get("name");

        if (this.firstName.equals(name)){
            System.out.println("3");
        }

        this.weight = playerInfo.get("weight") != null ? (int) playerInfo.get("weight") : 75;

        if (this.firstName.equals(name)){
            System.out.println("4");
        }
        this.height = playerInfo.get("height") != null ? (int) (((Double) playerInfo.get("height")) * 100) : 183;

        if (this.firstName.equals(name)){
            System.out.println("5");
        }
    }
}
