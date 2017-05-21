/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package desktopinlämning1;

import java.io.Serializable;

/**
 *
 * @author rille
 */
public class Developer implements Serializable {
    
    
    private int developerId;
    private String developerName;

    
    public Developer(int developerId, String developerName){
        this.developerId = developerId;
        this.developerName = developerName;
    }
    
    public Developer(){}
    
    public int getDeveloperId(){
        return developerId;
    }
    
    public void setDeveloperId(int developerId){
        this.developerId = developerId;
    }

    public String getDeveloperName() {
        return developerName;
    }
    
    public void setDeveloperName(String developerName){
        this.developerName = developerName;
    }
   
    @Override
    public String toString(){
        return developerName;
    } 
}
