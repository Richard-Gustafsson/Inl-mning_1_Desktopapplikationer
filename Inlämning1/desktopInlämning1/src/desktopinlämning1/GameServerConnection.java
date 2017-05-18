/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package desktopinlämning1;

import java.io.Serializable;

/**
 *
 * @author Richard
 */
public class GameServerConnection implements Serializable{
    
    private String gameName;
    private String yearOfRelease;
    private String genre;

    public GameServerConnection(String gameName, String yearOfRelease, String genre) {
        this.gameName = gameName;
        this.yearOfRelease = yearOfRelease;
        this.genre = genre;
    }
    
    public GameServerConnection(){}

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
