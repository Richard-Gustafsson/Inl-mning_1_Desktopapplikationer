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
    
    // Hämtar alla Developers som finns i databasen och sparar i en lista som sedan retuneras till klient. 
    public List<Developer> getDevelopers(){
        
        Session session = NewHibernateUtil.getSession();
        Query query = session.createQuery("from Developer");
        
        List<Developer> developer = query.list();
        
        return developer;
    }
    
    // Lägger till ett nytt objekt av Developer i databasen. Varje Developer har ett unikt ID.
    // Varje Developer har en lista med spel. 
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
    
    // Hämtar en Developer med hjälp av Developer-ID.
    public Developer getDeveloper(int developerId){
        
        Session session = NewHibernateUtil.getSession();
        session.beginTransaction();
        Developer dev = (Developer) session.get(Developer.class, developerId);
        session.getTransaction().commit();
        
        return dev;
    }
    
    // Metoden tar emot ett Developer-objekt som ersätter det gamla. Detta för namnbyte.
    public void updateDeveloper(Developer developer){
        
        Session session = NewHibernateUtil.getSession();
        session.beginTransaction();
        
        Query q = session.createQuery("update Developer set developerName = :newName where developerId = :id");
        q.setParameter("newName", developer.getDeveloperName());
        q.setInteger("id", developer.getDeveloperId());
        q.executeUpdate();
        
        session.getTransaction().commit();
        session.close();
    }
    
    // Tar bort det Developer-objekt som man specificerat med hjälp av Developer-ID.
    // Då programmet inte använder sig utav 'Cascade' i databas, så tar man istället
    // bort det spel som tillhör Developern först, om denna har Game-objekt.
    public void deleteDeveloper(int developerId){
        
        Session session = NewHibernateUtil.getSession();
        session.beginTransaction();
        
        Query query = session.createQuery("delete from Game where developer = :id");
        query.setInteger("id", developerId);
        
        query.executeUpdate();
        
        Query q = session.createQuery("delete from Developer where developerId = :id");
        q.setInteger("id", developerId);
        
        q.executeUpdate();
        session.getTransaction().commit();
        session.close();
    }   
}
