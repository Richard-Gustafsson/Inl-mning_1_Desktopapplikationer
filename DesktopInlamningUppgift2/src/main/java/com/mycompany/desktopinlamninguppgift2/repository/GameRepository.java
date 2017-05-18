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
    
        public Game addGame(int developerId, Game game){
            Session session = NewHibernateUtil.getSession();
            session.beginTransaction();

            Game myGame = new Game();
            List<Developer> devsList = developerDB.getDevelopers();


            for(int i = 0; i < devsList.size(); i ++){
                if(devsList.get(i).getDeveloperId()==developerId){

                    myGame.setDeveloper(devsList.get(i));
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
        
        public List<Game> getAllGames(int developerId){
            System.out.println("Kommer in i getAllGames i GameRepository");
            Session session = NewHibernateUtil.getSession();
            session.beginTransaction();
            
            Query q = session.createQuery("from Game where developer = :id");
            q.setInteger("id", developerId);
            
            List<Game> allGames = q.list();
            
            return allGames;
        }
        
        public List<Game> getGame(int gameId){
            Session session = NewHibernateUtil.getSession();
            session.beginTransaction();
            
            Query q = session.createQuery("from Game where gameId = :id");
            q.setInteger("id", gameId);
            
            List<Game> game = q.list();
            
            return game;
        }
        
        public void updateGame(Game game){
            System.out.println("Kommer in i updateGame i GameRepository");
            Session session = NewHibernateUtil.getSession();
            session.beginTransaction();
            
            Query q = session.createQuery("update Game set gameName = :newName, yearOfRelease = :newYear, genre = :newGenre where gameid = :id");
            q.setParameter("newName", game.getGameName());
            q.setParameter("newYear", game.getYearOfRelease());
            q.setParameter("newGenre", game.getGenre());
            q.setInteger("id", game.getGameId());
            System.out.println("KOmmer jag hit?");
            q.executeUpdate();
            System.out.println("Kommer jag hit med?");
            session.getTransaction().commit();
            session.close();
        }
        
        public void deleteGame(int gameId){
            System.out.println("KOmmer in i deleteGame i repository");
            Session session = NewHibernateUtil.getSession();
            session.beginTransaction();
            
            Query q = session.createQuery("delete from Game where gameID = :id");
            q.setInteger("id", gameId);
            q.executeUpdate();
            session.getTransaction().commit();
            session.close();
        }
    
}
