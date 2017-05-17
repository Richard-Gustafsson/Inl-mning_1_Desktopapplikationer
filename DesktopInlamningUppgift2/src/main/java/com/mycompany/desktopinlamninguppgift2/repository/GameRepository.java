/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.desktopinlamninguppgift2.repository;

import com.mycompany.desktopinlamninguppgift2.models.Developer;
import com.mycompany.desktopinlamninguppgift2.models.Game;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

/**
 *
 * @author rille
 */
public class GameRepository {
    
        public Game addGame(int developerId, Game game){
        Session session = NewHibernateUtil.getSession();
        session.beginTransaction();
        
        Game myGame = new Game();
        Developer d = new Developer();
        Query q = session.createQuery("from Developer where developerId = :id");
        q.setInteger("id", developerId);
        
        
        myGame.setGameName(game.getGameName());
        myGame.setGenre(game.getGenre());
        myGame.setYearOfRelease(game.getYearOfRelease());
        
        myGame.setDeveloper();
        session.save(myGame);
        session.getTransaction().commit();
        session.close();

          return myGame; 
        
    }
    
}
