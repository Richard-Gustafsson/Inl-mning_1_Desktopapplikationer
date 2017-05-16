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

/**
 *
 * @author rille
 */
public class ProgramLogic {
    
    private static ProgramLogic instance; //Step 2 declare the instance variabel
    
    private ObservableList<Developer> obDeveloperList;
    
    
    private ProgramLogic() //Step 1 declare the constructor and change it to private
    {
        this.obDeveloperList = FXCollections.observableArrayList();
    }
    
    public static ProgramLogic getInstance() //Step 3 write getInstance method
    {
        if (instance == null)
        {
            instance = new ProgramLogic();
        }
        return instance;
    }
    
    public ObservableList<Developer> getDeveloperList()
    {
        return obDeveloperList;
    }
    
    // Lägger till objekt med developers till listan med developers.
    // Finns lite felhantering för att se till att samma namn inte kan finnas för 2 developers eller fler.
    public boolean setDeveloperList(String n){
        boolean exists = false;
        for(Developer d : obDeveloperList){
            if(d.getDeveloperName().equals(n)){
                exists = true;
                break;
            }
            else{
                exists = false;
            }
        }
        if(exists == false){
            obDeveloperList.add(new Developer(n));
        }
        return exists;
    }
    
    // Skriver över arraylistan med spel till en observablelista för att kunna visa objekten i tableview.
    public ObservableList<Game> getDeveloperGameList(){
        
        ObservableList<Game> obGameList = FXCollections.observableArrayList(); 
        
        for(Developer d : obDeveloperList){
            for(Game g: d.getGameList()){
                obGameList.addAll(d.getGameList());
            }
        } 
        return obGameList;
    }
    
    // Metod som lägger till ett nytt gameobjekt i arraylistan med spel.
    public void addGameToDeveloper(String x, String n, String y, String g){
        System.out.println("Kommer till addGameToDeveloper i Logic.");
        for(Developer d : obDeveloperList){
            if(d.getDeveloperName().equals(x)){
                d.setGameList(n, y, g);
            }
        }
    }

}
