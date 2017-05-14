/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.desktopinlamninguppgift2.repository;

import com.mycompany.desktopinlamninguppgift2.models.Developer;
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
        Session session = NewHibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        session.save(developer);
        session.getTransaction().commit();
        session.close();
        
        return developer;
    }    
}
