/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.desktopinlamninguppgift2.services;

import com.mycompany.desktopinlamninguppgift2.models.Developer;
import com.mycompany.desktopinlamninguppgift2.repository.DeveloperRepository;
import java.util.List;

/**
 *
 * @author rille
 */
public class DeveloperService {
    
    DeveloperRepository developerDB = new DeveloperRepository();
    
    public DeveloperService(){}
    
    public List<Developer> getDevelopers(){   
        return developerDB.getDevelopers();
    }
    
    public Developer addDeveloper(Developer developer){
        return developerDB.addDeveloper(developer);
    }
    
    public Developer getDeveloper(int developerId){     
        return developerDB.getDeveloper(developerId);
    }
    
    public void updateDeveloper(Developer developer){
        developerDB.updateDeveloper(developer);
    }
    
    public void deleteDeveloper(int developerId){
        developerDB.deleteDeveloper(developerId);
    }
}
