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
    private ArrayList<Game> arrayGameList;

    
    public Developer(int developerId, String developerName){
        this.developerId = developerId;
        this.developerName = developerName;
        this.arrayGameList = new ArrayList();
    }
    
    public Developer(){
        
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
    
//    // Sets an observablelist with game objects that belongs to a specific developer
//    public void setGameList(String t, String y, String g){
//        System.out.println("Kommer till setGameList i Developer klass.");
//        arrayGameList.add(new Game(t,y,g));
//    }
//    
//    // Returns an observable list with all games for a specific developer
//    public ArrayList<Game> getGameList(){
//        return arrayGameList;
//    }
    
}
