/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.desktopinlamninguppgift2.repository;

import com.mycompany.desktopinlamninguppgift2.models.Developer;
import com.mycompany.desktopinlamninguppgift2.models.Game;
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
//        Developer d = new Developer();
        
        for(int i = 0; i < devsList.size(); i ++){
            if(devsList.get(i).getDeveloperId()==developerId){
//                d = devsList.get(i);
                myGame.setDeveloper(devsList.get(i));
            }
        }
        
        myGame.setGameName(game.getGameName());
        myGame.setGenre(game.getGenre());
        myGame.setYearOfRelease(game.getYearOfRelease());
//        myGame.setDeveloper(d);
        
        session.save(myGame);
        session.getTransaction().commit();
        session.close();

        return myGame; 
        
    }
    
}
