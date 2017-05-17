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

/**
 *
 * @author rille
 */
public class DeveloperRepository {
    
    public List<Developer> getDevelopers(){
        Session session = NewHibernateUtil.getSession();
        Query query = session.createQuery("from Developer");
        
        List<Developer> developer = query.list();
        System.out.println("Kommer in på tredje.");
        
        
        return developer;
    }
        
    public Developer addDeveloper(Developer developer){
        Session session = NewHibernateUtil.getSession();
        session.beginTransaction();
        Developer myDev = new Developer();
        myDev.setDeveloperName(developer.getDeveloperName());
        session.save(myDev);
        List<Game> games=(List<Game>) developer.getGames();
        myDev.setGames(games);
        session.saveOrUpdate(myDev);
        session.getTransaction().commit();
        session.close();
        
        return myDev;
    }
    
    public List<Developer> getDeveloper(int developerId){
        System.out.println("kommer in i getDeveloper i repository med developerId: " + developerId);
        Session session = NewHibernateUtil.getSession();
        
        Query q = session.createQuery("from Developer where developerId = :id");
       
        q.setInteger("id",developerId);
        
        List<Developer> devs = q.list();
        
        return devs;
    }
}
