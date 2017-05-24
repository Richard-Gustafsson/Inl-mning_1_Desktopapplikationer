/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.desktopinlamninguppgift2.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import java.io.Serializable;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

/**
 *
 * @author rille
 */
@Entity
public class Game implements Serializable{
    
    @Id@GeneratedValue
    private int gameId;
    
    private String gameName;
    private String yearOfRelease;
    private String genre;
    
    
    @ManyToOne
    @JsonBackReference
    @JsonIgnore
    Developer developer;

    public Game(int gameId, String gameName, String yearOfRelease, String genre) {
        this.gameId = gameId;
        this.gameName = gameName;
        this.yearOfRelease = yearOfRelease;
        this.genre = genre;
    }
    
    public Game(){}
    
    public void setDeveloper(Developer developer) {
        this.developer = developer;
    }

    public int getGameId() {
        return gameId;
    }

    public void setGameId(int gameId) {
        this.gameId = gameId;
    }

    public String getGameName() {
        return gameName;
    }

    public void setGameName(String gameName) {
        this.gameName = gameName;
    }

    public String getYearOfRelease() {
        return yearOfRelease;
    }

    public void setYearOfRelease(String yearOfRelease) {
        this.yearOfRelease = yearOfRelease;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

}