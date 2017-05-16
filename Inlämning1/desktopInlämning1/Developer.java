/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package desktopinlämning1;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author rille
 */
public class Developer {
    
    private String developerName;
    

    
    public Developer(String developerName){
        this.developerName = developerName;

    }
    
    public Developer(){
        
    }

    public String getDeveloperName() {
        return developerName;
    }
   
    @Override
    public String toString(){
        return developerName;
    }
    
}
