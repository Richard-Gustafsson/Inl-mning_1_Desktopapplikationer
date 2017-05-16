/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package desktopinlämning1;

/**
 *
 * @author rille
 */
public class Game {
    
    private String gameName;
    
    public Game(String gameName){
        this.gameName = gameName;
    }
    
    public String getGameName(){
        return gameName;
    }
    
    public String toString(){
        return gameName;
    }
    
}
