/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package desktopinlämning1;

import java.util.ArrayList;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.image.Image;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;

/**
 *
 * @author rille
 */
public class ProgramLogic {
    
    private static ProgramLogic instance; //Step 2 declare the instance variabel
    
    private ObservableList<Game> obGameList;
    private ObservableList<Developer> obDeveloperList;
    
    private ProgramLogic() //Step 1 declare the constructor and change it to private
    {
        this.obDeveloperList = FXCollections.observableArrayList();
        this.obGameList = FXCollections.observableArrayList();
    }
    
    public static ProgramLogic getInstance() //Step 3 write getInstance method
    {
        if (instance == null)
        {
            instance = new ProgramLogic();
        }
        return instance;
    }
    
    // Retunerar Observable-listan med Developer-objekt.
    public ObservableList<Developer> getDeveloperList()
    {
        return obDeveloperList;
    }
    
    // Sätter Observable-listan med Developer-Objekt som kommer in från DB.
    public void setDeveloperList(List<Developer> tempDeveloperList){
        for(Developer d : tempDeveloperList){
            obDeveloperList.addAll(d);
        }
    }
    
    // Extra metod för att sätta Observable-listan med Game-objekt.
    public void setObGameList(List<Game> gameList){
        
        for(Game g: gameList){
            obGameList.addAll(g);
        }
    }
    
    // Retunerar Observable-listan med Game-objekt.
    public ObservableList<Game> getObGameList(){
        return obGameList;
    }
 
}
