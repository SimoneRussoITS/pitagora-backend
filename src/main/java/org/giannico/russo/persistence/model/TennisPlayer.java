package org.giannico.russo.persistence.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.quarkus.mongodb.panache.PanacheMongoRepository;
import io.quarkus.mongodb.panache.common.MongoEntity;
import org.bson.types.ObjectId;

import java.time.LocalDate;

@MongoEntity(collection = "tennis-player")
public class TennisPlayer {
    private String id;
    private String firstName;
    private String lastName;
    private LocalDate birthDay;
    private int atpRanking;
    private String nationality;
    private int weight;
    private int height;

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
}
