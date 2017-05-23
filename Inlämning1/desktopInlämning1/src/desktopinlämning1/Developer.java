/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package desktopinlämning1;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 * @author rille
 */
public class Developer implements Serializable {
    
    
    private int developerId;
    private String developerName;
    private List<Game> gameList;
    
    public Developer(int developerId, String developerName){
        this.developerId = developerId;
        this.developerName = developerName;
//        this.gameList = FXCollections.observableArrayList();
        this.gameList = new ArrayList();
    }
    
    public Developer(){}

    public List<Game> getGameList() {
        return gameList;
    }

    public void setGameList(List<Game> gameList) {
        this.gameList = gameList;
    }
    
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
}
