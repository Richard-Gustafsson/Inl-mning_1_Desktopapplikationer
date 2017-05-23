/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.desktopinlamninguppgift2.services;

import com.mycompany.desktopinlamninguppgift2.models.Game;
import com.mycompany.desktopinlamninguppgift2.repository.GameRepository;
import java.util.List;

/**
 *
 * @author rille
 */
public class GameService {
    
    GameRepository gameDB = new GameRepository();

    public GameService(){}
    
    public Game addGame(int developerId, Game game){
        System.out.println("Kommer in i addGame i Service");
        return gameDB.addGame(developerId, game);
    }
    
    public List<Game> getAllGames(int developerId){
        return gameDB.getAllGames(developerId);
    }
    
    public Game getGame(int gameId){
        return gameDB.getGame(gameId);
    }
    
    public void updateGame(Game game){
        gameDB.updateGame(game);
    }
    
    public void deleteGame(int gameId){
        gameDB.deleteGame(gameId);
    }  
}
