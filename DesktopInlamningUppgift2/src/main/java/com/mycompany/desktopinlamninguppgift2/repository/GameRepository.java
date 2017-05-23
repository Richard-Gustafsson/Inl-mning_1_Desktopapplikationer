/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.desktopinlamninguppgift2.repository;

import com.mycompany.desktopinlamninguppgift2.models.Developer;
import com.mycompany.desktopinlamninguppgift2.models.Game;
import java.math.BigInteger;
import java.util.List;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

/**
 *
 * @author rille
 */
public class GameRepository {
        
    DeveloperRepository developerDB = new DeveloperRepository();
    
    // Lägger till ett ny Game-objekt i databasen. Objektet får även det Developer-ID
    // som visar vilken Developer det tillhör.
    public Game addGame(int developerId, Game game){
        System.out.println("Kommer in i addGame i GameRepository.");
            
        Session session = NewHibernateUtil.getSession();
        session.beginTransaction();

        Game myGame = new Game();
        List<Developer> devsList = developerDB.getDevelopers();


        for(int i = 0; i < devsList.size(); i++){
            if(devsList.get(i).getDeveloperId()==developerId){
                System.out.println("Här är developerId: " + devsList.get(i));
                myGame.setDeveloper(devsList.get(i));
                System.out.println("KOmmer aldrig hit eller+");
            }
        }

        myGame.setGameName(game.getGameName());
        myGame.setGenre(game.getGenre());
        myGame.setYearOfRelease(game.getYearOfRelease());


        session.save(myGame);
        session.getTransaction().commit();
        session.close();

        return myGame; 
        
    }
    
    // Retunerar en lista med alla dem spel som tillhör en viss Developer.
    public List<Game> getAllGames(int developerId){
        
        Session session = NewHibernateUtil.getSession();
        session.beginTransaction();
            
        Query q = session.createQuery("from Game where developer = :id");
        q.setInteger("id", developerId);
            
        List<Game> allGames = q.list();
            
        return allGames;
        
    }
    
    // Hämtar ett Game-objekt med hjälp av Game-ID.
    public Game getGame(int gameId){
        
        Session session = NewHibernateUtil.getSession();
        session.beginTransaction();
                        
        Game game = (Game) session.get(Game.class, gameId);
               
        return game;
        
    }
    
    // Tar in ett nytt Game-objekt som kommer att ersätta det gamla.
    // Detta kan uppdatera alla de värden objektet har eller bara vissa. 
    public void updateGame(Game game){
            
        Session session = NewHibernateUtil.getSession();
        session.beginTransaction();
            
        Query q = session.createQuery("update Game set gameName = :newName, yearOfRelease = :newYear, genre = :newGenre where gameid = :id");
        q.setParameter("newName", game.getGameName());
        q.setParameter("newYear", game.getYearOfRelease());
        q.setParameter("newGenre", game.getGenre());
        q.setInteger("id", game.getGameId());
            
        q.executeUpdate();
            
        session.getTransaction().commit();
        session.close();
        
    }
    
    // Tar bort det Game-objekt som man speciferat med hjälp av Game-ID.
    public void deleteGame(int gameId){
            
        Session session = NewHibernateUtil.getSession();
        session.beginTransaction();
            
        Query q = session.createQuery("delete from Game where gameID = :id");
        q.setInteger("id", gameId);
        q.executeUpdate();
        session.getTransaction().commit();
        session.close();
    }
    
}
