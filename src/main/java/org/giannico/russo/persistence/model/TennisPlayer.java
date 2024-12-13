package org.giannico.russo.persistence.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.quarkus.mongodb.panache.common.MongoEntity;
import org.bson.codecs.pojo.annotations.BsonProperty;
import org.bson.types.ObjectId;

import java.time.*;
import java.util.Map;

@MongoEntity(collection = "tennis-player")
public class TennisPlayer {
    @JsonIgnore
    @BsonProperty("_id")
    private ObjectId id;
    @BsonProperty("firstName")
    private String firstName;
    @BsonProperty("lastName")
    private String lastName;
    @BsonProperty("birthDay")
    private LocalDate birthDay;
    @BsonProperty("atpRanking")
    private int atpRanking;
    @BsonProperty("nationality")
    private String nationality;
    @BsonProperty("weight")
    private int weight;
    @BsonProperty("height")
    private int height;

    public TennisPlayer() {

    }
    public TennisPlayer(ObjectId id, String firstName, String lastName, LocalDate birthDay, int atpRanking, String nationality, int weight, int height) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.birthDay = birthDay;
        this.atpRanking = atpRanking;
        this.nationality = nationality;
        this.weight = weight;
        this.height = height;
    }

    public ObjectId getId() {
        return id;
    }

    public void setId(ObjectId id) {
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


    @JsonCreator
    public TennisPlayer(
            @JsonProperty("team") Map<String, Object> team,
            Map<Integer, Integer> idRankMap) {

        String fullName = team.get("fullName").toString();
        if (fullName.contains(",")) {
            String[] parts = fullName.split(",");
            this.firstName = parts[1].trim();
            this.lastName = parts[0].trim();
        } else {
            String[] parts = fullName.split(" ");
            if (parts.length == 2) {
                this.firstName = parts[0].trim();
                this.lastName = parts[1].trim();
            } else if (parts.length == 1) {
                this.firstName = parts[0].trim();
                this.lastName = "";
            } else {
                throw new IllegalArgumentException("Formato nome non valido: " + fullName);
            }
        }

        Map<String, Object> playerInfo = (Map<String, Object>) team.get("playerTeamInfo");
        int birthDateTimestamp = (int) playerInfo.get("birthDateTimestamp");
        this.birthDay = Instant.ofEpochMilli(birthDateTimestamp).atZone(ZoneId.systemDefault()).toLocalDate();

        this.atpRanking = team.get("ranking") != null
                ? (int) team.get("ranking")
                : (idRankMap != null ? idRankMap.getOrDefault(Integer.parseInt(String.valueOf(this.id)), 0) : 0);
        this.nationality = ((Map<String, String>) team.get("country")).get("name");
        this.weight = playerInfo.get("weight") != null ? (int) playerInfo.get("weight") : 75;
        this.height = playerInfo.get("height") != null ? (int) (((Double) playerInfo.get("height")) * 100) : 183;
    }
}
