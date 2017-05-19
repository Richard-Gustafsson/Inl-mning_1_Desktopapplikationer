/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package desktopinlämning1;

import java.io.Serializable;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 *
 * @author rille
 */
public class GameWithProperties implements Serializable{
    
    private IntegerProperty gameId;
    private StringProperty gameName;
    private StringProperty yearOfRelease;
    private StringProperty genre;
    
    public GameWithProperties(int gameId, String gameName, String yearOfRelease, String genre){
        this.gameId = new SimpleIntegerProperty(gameId);
        this.gameName = new SimpleStringProperty(gameName);
        this.yearOfRelease = new SimpleStringProperty(yearOfRelease);
        this.genre = new SimpleStringProperty(genre);
    }
    
    public GameWithProperties(){}

    public final int getGameId() {
        return gameId.get();
    }

    public final void setGameId(int gameId) {
        this.gameId.set(gameId);
    }
    
    public final String getGameName(){
        return gameName.get();
    }
    
    public final String getYearOfRelease(){
        return yearOfRelease.get();
    }
    
    public final String getGenre(){
        return genre.get();
    }
    
    public final void setGameName(String gameName){
        this.gameName.set(gameName);
    }
    
    public final void setYearOfRelease(String yearOfRelease){
        this.yearOfRelease.set(yearOfRelease);
    }
    
    public final void setGenre(String genre){
        this.genre.set(genre);
    }    

}
