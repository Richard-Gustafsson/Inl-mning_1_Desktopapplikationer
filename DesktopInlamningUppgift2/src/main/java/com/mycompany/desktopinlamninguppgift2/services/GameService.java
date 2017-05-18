/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.desktopinlamninguppgift2.services;

import com.mycompany.desktopinlamninguppgift2.models.Developer;
import com.mycompany.desktopinlamninguppgift2.models.Game;
import com.mycompany.desktopinlamninguppgift2.repository.DeveloperRepository;
import com.mycompany.desktopinlamninguppgift2.repository.GameRepository;
import java.util.List;

/**
 *
 * @author rille
 */
public class GameService {
    GameRepository gameDB = new GameRepository();
    DeveloperRepository developerDB = new DeveloperRepository();
    
    public GameService(){}
    
    public Game addGame(int developerId, Game game){
        
        return gameDB.addGame(developerId, game);
    }
    
    public List<Game> getAllGames(int developerId){
        System.out.println("Kommer in i getAllGames i GameService");
        return gameDB.getAllGames(developerId);
    }
    
    public List<Game> getGame(int gameId){
        System.out.println("Kommer in i getGame i GameService");
        return gameDB.getGame(gameId);
    }
    
    public void updateGame(Game game){
        System.out.println("Kommer in i updateGame i GameService");
        gameDB.updateGame(game);
    }
    
    public void deleteGame(int gameId){
        System.out.println("Kommer in i deleteGame i service");
        gameDB.deleteGame(gameId);
    }
}
