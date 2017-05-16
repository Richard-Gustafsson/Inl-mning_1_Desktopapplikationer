/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.desktopinlamninguppgift2.models;

/**
 *
 * @author rille
 */
import com.fasterxml.jackson.annotation.JsonBackReference;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@Entity
public class Developer implements Serializable{
    
    @Id@GeneratedValue
    private int developerId;
    private String developerName;
    @OneToMany(mappedBy ="developer")
    @JsonBackReference        
    private List <Game> games;
    

    public Developer(int developerId, String developerName){
        this.developerId = developerId;
        this.developerName = developerName;
    }
    
    public Developer(){}
    
    public int getDeveloperId(){
        return developerId;
    }
    
    public void setDeveloperId(int developerId){
        this.developerId = developerId;
    }

    public String getDeveloperName() {
        return developerName;
    }
    
    public void setDeveloperName(String developerName){
        this.developerName = developerName;
    }
   
    @Override
    public String toString(){
        return developerName;
    }
    
    public List<Game> getGames() {
        return games;
    }

    public void setPlayers(List<Game> games) {
        this.games = games;
    }
    
}
