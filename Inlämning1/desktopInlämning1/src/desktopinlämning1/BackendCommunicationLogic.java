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
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;

/**
 *
 * @author rille
 */
public class BackendCommunicationLogic {
    
    
    Client client = ClientBuilder.newClient();
    ProgramLogic logic = ProgramLogic.getInstance();
    
    public BackendCommunicationLogic(){}
    

    /* Metoden får in ett sträng värde från Controller. Skapar ett nytt objekt av typen 
    Developer och sätter sedan Developer-namnet med sträng värdet. 
    */
    public Developer addDeveloper(String n){
        
        Developer d = new Developer(); 
        d.setDeveloperName(n);
        
        Developer developer = client.target("http://localhost:8080/DesktopInlamningUppgift2/webapi/developers")
                .request(MediaType.APPLICATION_JSON)
                .post(Entity.json(d), Developer.class);
        
        return d;
    }
    
    /* Metoden får in ett sträng värde från Controller från en markerad Developer. Skapar ett nytt objekt av typen
    Developer. Därefter hittas Developerns ID för att kunna ta bort från DB.
    */
    public void deleteDeveloper(String n){
        Developer tempDev = new Developer();
        
        for(Developer d : logic.getDeveloperList()){
            if(d.getDeveloperName()==n){
                tempDev.setDeveloperId(d.getDeveloperId());
            }
        }
        
        client
                .target("http://localhost:8080/DesktopInlamningUppgift2/webapi/developers/"+tempDev.getDeveloperId())
                .request(MediaType.APPLICATION_JSON)
                .delete();
               
    }
    
    /* Metoden frågar DB efter alla Developers som finns. Detta retuneras sedan till Klient i form
    utav en lista. 
    */
    public List<Developer> getAllDevelopers(){
        List<Developer> tempDeveloperList = client
                .target("http://localhost:8080/DesktopInlamningUppgift2/webapi/developers")
                .request(MediaType.APPLICATION_JSON)
                .get(new GenericType<List<Developer>>() {});
        
        return tempDeveloperList;
        
    }
    
    /* Metoden frågar DB efter en specifik Developer. Detta igenom att med hjälp av
    sträng värdet från Klient, leta upp det ID som Developern har. Retunerar sedan 
    en lista för att kunna sätta ListView i klient. 
    */
    public ObservableList<Developer> getDeveloper(String s){

        Developer tempDev = new Developer();
        ObservableList list = FXCollections.observableArrayList();
        for(Developer d : logic.getDeveloperList()){
            
            if(d.getDeveloperName().equals(s)){
                tempDev.setDeveloperId(d.getDeveloperId());
            }
        }
        
        Developer dev = client 
                .target("http://localhost:8080/DesktopInlamningUppgift2/webapi/developers/"+tempDev.getDeveloperId())
                .request(MediaType.APPLICATION_JSON)
                .get(Developer.class);
        
        
        list.add(dev);
        return list;
    }
    
    /* Metoden tar in två värden, den Developer man vill ändra och det nya värdet för namnet
    på Developern ifråga. Skapar sedan ett nytt objekt av Developer och sätter det nya värdet när
    man hittat rätt ID i DB.
    */
    public void updateDeveloper(String n, String o){
        Developer tempDev = new Developer();
        
        for(Developer d : logic.getDeveloperList()){
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
    
    /* Metoden får en värden från klient, vilken Developer som skall få det nya Game-objektet,
    samt alla de värden som Game-objektet skall ha, som t.ex. namn. Skapar sedan det nya objektet
    till DB.
    */
    public void addGame(String x, String n, String y, String g){
        Developer tempDev = new Developer();
        Game tempGame = new Game();
        
        tempGame.setGameName(n);
        tempGame.setYearOfRelease(y);
        tempGame.setGenre(g);
        
        for(Developer d : logic.getDeveloperList()){
            if(d.getDeveloperName()==x){
                tempDev.setDeveloperId(d.getDeveloperId());
            }
        }
       
        client
                .target("http://localhost:8080/DesktopInlamningUppgift2/webapi/developers/"+tempDev.getDeveloperId()+"/games")
                .request(MediaType.APPLICATION_JSON)
                .post(Entity.json(tempGame), Game.class);
        
    }
    
    /* Metoden tar in ett värde från klient, detta värde motsvarar den Developer man klickat på 
    i listan. Därefter hittas de spel som finns för Developern. Detta retuneras till klient i form
    utav en lista. 
    */
    public List<Game> getAllGames(String x){
       
        Developer tempDev = new Developer();
        Game game = new Game();
        
        for(Developer d : logic.getDeveloperList()){
            if(d.getDeveloperName()==x){
                tempDev.setDeveloperId(d.getDeveloperId());
            }
        }
        
        List<Game> games=client
                .target("http://localhost:8080/DesktopInlamningUppgift2/webapi/developers/"+tempDev.getDeveloperId()+"/games")
                .request(MediaType.APPLICATION_JSON)
                .get(new GenericType<List<Game>>(){});
        

        return games;
    }
 
    /* Metoden tar in ett värde från klient, det värde man sökt efter motsvarar namnet på ett Game-objekt. 
    Här behövs ingen kontakt med server/DB, då det räcker att gå igenom listorna för alla Developers och deras spel.
    Detta retuneras sedan i form utav en lista till klient.
    */
    public ObservableList<Game> getGame(String s){
        
        ObservableList<Game> tempGameList = FXCollections.observableArrayList();

        for(Developer d : logic.getDeveloperList()){
            for(Game g : getAllGames(d.getDeveloperName())){
                if(g.getGameName().equals(s)){
                    tempGameList.add(g);
                    break;
                }
            }
        }
        
     return tempGameList;
    }
    
    /* Metoden hittar det Game-objekt som man vill radera genom att först leta 
    upp vilken Developer det tillhör och sedan det Game-objekt som det gäller. Tar sedan
    ID:et för denna för att hitta och radera i DB.
    */
    public void deleteGame(String n, String x){
        Developer tempDev = new Developer();
        Game tempGame = new Game();
        
        for(Developer d : logic.getDeveloperList()){
            if(d.getDeveloperName().equals(n)){
                tempDev = d;
            }
        }
        
        for(Game g : getAllGames(n)){
            if(g.getGameName().equals(x)){
                tempGame = g;
            }
        }
        
        client
                .target("http://localhost:8080/DesktopInlamningUppgift2/webapi/developers/"+tempDev.getDeveloperId()+"/games/"+tempGame.getGameId())
                .request(MediaType.APPLICATION_JSON)
                .delete();
               
    }
    
    /* Metoden får en värden för vilken Developer det är som äger Game-objektet, det markerade
    Game-objektet, samt de gamla och nya värdet. Med det gamla värdet jämför man sedan vad det är i Game-objektet som
    användaren vill ändra. När man hittat rätt attribut för Game-objektet så sätter man det nya värdet på rätt plats.
    */
    public void updateGame(String devName, String gameName, String oldVal, String newVal){
        
        Developer tempDev = new Developer();
        Game tempGame = new Game();
        
        for(Developer d : logic.getDeveloperList()){
            if(d.getDeveloperName().equals(devName)){
                tempDev = d;
            }
        }
        
        for(Game g : getAllGames(devName)){
            if(g.getGameName().equals(gameName)){
                tempGame = g;
            }
        }
        
        if(tempGame.getGameName().equals(oldVal)){
            tempGame.setGameName(newVal);
        }
        else if(tempGame.getYearOfRelease().equals(oldVal)){
            tempGame.setYearOfRelease(newVal);
        }
        else if(tempGame.getGenre().equals(oldVal)){
            tempGame.setGenre(newVal);
        }
        
        client
                .target("http://localhost:8080/DesktopInlamningUppgift2/webapi/developers/"+tempDev.getDeveloperId()+"/games/"+tempGame.getGameId())
                .request()
                .put(Entity.entity(tempGame, MediaType.APPLICATION_JSON));
            
    }
    
}
