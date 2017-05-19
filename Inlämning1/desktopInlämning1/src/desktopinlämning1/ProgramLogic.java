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
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;

/**
 *
 * @author rille
 */
public class ProgramLogic {
    
    private static ProgramLogic instance; //Step 2 declare the instance variabel
    
    private ObservableList<Developer> obDeveloperList;
    Client client = ClientBuilder.newClient();

    
    
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
    
    public void setDeveloperList(List<Developer> tempDeveloperList){
        for(Developer d : tempDeveloperList){
            obDeveloperList.addAll(d);
        }
    }
    
    public void addDeveloper(String n){
        
        Developer d = new Developer(); 
        d.setDeveloperName(n);
        
        Developer developer = client.target("http://localhost:8080/DesktopInlamningUppgift2/webapi/developers")
                .request(MediaType.APPLICATION_JSON)
                .post(Entity.json(d), Developer.class);
        
    }
    
    public void deleteDeveloper(String n){
        Developer tempDev = new Developer();
        
        for(Developer d : getDeveloperList()){
            if(d.getDeveloperName()==n){
                System.out.println("Hittar objektet" + d);
                tempDev.setDeveloperId(d.getDeveloperId());
            }
        }
        
        System.out.println("Får den tag i rätt id: "+tempDev.getDeveloperId());
        
        client
                .target("http://localhost:8080/DesktopInlamningUppgift2/webapi/developers/"+tempDev.getDeveloperId())
                .request(MediaType.APPLICATION_JSON)
                .delete();
               
    }
    
    public List<Developer> getAllDevelopers(){
        System.out.println("Kommer in för att hämta developers.");
        List<Developer> tempDeveloperList = client
                .target("http://localhost:8080/DesktopInlamningUppgift2/webapi/developers")
                .request(MediaType.APPLICATION_JSON)
                .get(new GenericType<List<Developer>>() {});
        
        return tempDeveloperList;
        
    }
    
    public ObservableList<Developer> getDeveloper(String s){

        Developer tempDev = new Developer();

        for(Developer d : getDeveloperList()){
            
            if(d.getDeveloperName().equals(s)){
                System.out.println("Hittar objektet" + d);
                tempDev.setDeveloperId(d.getDeveloperId());
            }
        }
        
        Developer dev = client 
                .target("http://localhost:8080/DesktopInlamningUppgift2/webapi/developers/"+tempDev.getDeveloperId())
                .request(MediaType.APPLICATION_JSON)
                .get(Developer.class);
        
        
        obDeveloperList.clear();
        obDeveloperList.add(dev);
        return obDeveloperList;
    }
    
    public void updateDeveloper(String n, String o){
        Developer tempDev = new Developer();
        
        for(Developer d : getDeveloperList()){
            if(d.getDeveloperName().equals(o)){
                tempDev.setDeveloperId(d.getDeveloperId());
            }
        }
        tempDev.setDeveloperName(n);
        
        client
                .target("http://localhost:8080/DesktopInlamningUppgift2/webapi/developers/"+tempDev.getDeveloperId())
                .request()
                .put(Entity.entity(tempDev, MediaType.APPLICATION_JSON));
        
        
    }
    
    public void addGame(String x, String n, String y, String g){
        Developer tempDev = new Developer();
        Game tempGame = new Game();
        System.out.println("Kommer vi till 1");
        tempGame.setGameName(n);
        tempGame.setYearOfRelease(y);
        tempGame.setGenre(g);
        System.out.println("Kommer vi till 2");
        
        for(Developer d : getDeveloperList()){
            if(d.getDeveloperName()==x){
                tempDev.setDeveloperId(d.getDeveloperId());
            }
        }
        System.out.println("Komme vi till 3");
        client
                .target("http://localhost:8080/DesktopInlamningUppgift2/webapi/developers/"+tempDev.getDeveloperId()+"/games")
                .request(MediaType.APPLICATION_JSON)
                .post(Entity.json(tempGame), Game.class);
        
    }
    
    public List<GameWithProperties> getAllGames(String x){
        System.out.println("Kommer in i getAllGames i ProgramLogic.");
        Developer tempDev = new Developer();
        Game game = new Game();
        
        
        
        List<GameWithProperties> gwpList = new ArrayList();
        
        
        
        for(Developer d : getDeveloperList()){
            if(d.getDeveloperName()==x){
                tempDev.setDeveloperId(d.getDeveloperId());
            }
        }
        System.out.println("Kommer igenom loopen för att hitta rätt developerId som är: " + tempDev.getDeveloperId());
        
        List<Game> games=client
                .target("http://localhost:8080/DesktopInlamningUppgift2/webapi/developers/"+tempDev.getDeveloperId()+"/games")
                .request(MediaType.APPLICATION_JSON)
                .get(new GenericType<List<Game>>(){});
        
        for(Game g : games){
           System.out.println("Går igenom loopen?" + g.getGameId() + g.getGameName() + g.getYearOfRelease() + g.getGenre());
           gwpList.add(new GameWithProperties(g.getGameId(),g.getGameName(),g.getYearOfRelease(),g.getGenre()));
        }
        System.out.println("Kommer ut ur loopen.");
        
        return gwpList;
    }
    
   
    
    // Lägger till objekt med developers till listan med developers.
    // Finns lite felhantering för att se till att samma namn inte kan finnas för 2 developers eller fler.
//    public boolean setDeveloperList(String n){
//        boolean exists = false;
//        for(Developer d : obDeveloperList){
//            if(d.getDeveloperName().equals(n)){
//                exists = true;
//                break;
//            }
//            else{
//                exists = false;
//            }
//        }
//        if(exists == false){
//            obDeveloperList.add(new Developer(n));
//        }
//        return exists;
//    }
    


    
//    // Metod som lägger till ett nytt gameobjekt i arraylistan med spel.
//    public void addGameToDeveloper(String x, String n, String y, String g){
//        System.out.println("Kommer till addGameToDeveloper i Logic.");
//        for(Developer d : obDeveloperList){
//            if(d.getDeveloperName().equals(x)){
//                d.setGameList(n, y, g);
//            }
//        }
//    }
    
    

}
