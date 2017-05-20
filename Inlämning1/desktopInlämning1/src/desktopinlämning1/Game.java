/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package desktopinlämning1;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Richard
 */
public class Game implements Serializable{
    
    private int gameId;
    private String gameName;
    private String yearOfRelease;
    private String genre;

    public Game(int gameId, String gameName, String yearOfRelease, String genre) {
        this.gameId = gameId;
        this.gameName = gameName;
        this.yearOfRelease = yearOfRelease;
        this.genre = genre;
    }
    
    public Game(){}
    
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
