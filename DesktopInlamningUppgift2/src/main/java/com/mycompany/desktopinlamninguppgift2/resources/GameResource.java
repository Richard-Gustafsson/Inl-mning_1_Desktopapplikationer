/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.desktopinlamninguppgift2.resources;

import com.mycompany.desktopinlamninguppgift2.models.Game;
import com.mycompany.desktopinlamninguppgift2.services.GameService;
import java.util.List;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

/**
 *
 * @author rille
 */

@Path("/")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class GameResource {
    
    GameService gs = new GameService();
   
    
    @POST
    public Game addGame(@PathParam("developerId") int developerId, Game game){
        System.out.println("Kommer in i addGame i gameResource.");
        return gs.addGame(developerId, game);
    }
    
    @GET
    public List<Game> getAllGames(@PathParam("developerId") int developerId){
        System.out.println("Kommer in i getAllGames i gameResource");
        return gs.getAllGames(developerId);
    }
    
    @GET
    @Path("/{gameId}")
    public List<Game> getGame(@PathParam("gameId") int gameId){
        System.out.println("Kommer in i getGame i GameResouce");
        return gs.getGame(gameId);
    }
    
    @PUT
    @Path("/{gameId}")
    public void updateGame(@PathParam("gameId") int gameId, Game game){
        System.out.println("Kommer in i updategame i resource");
        game.setGameId(gameId);
        gs.updateGame(game);
    }
    
    @DELETE
    @Path("/{gameId}")
    public void deleteGame(@PathParam("gameId") int gameId){
        System.out.println("Kommer in i deleteGame i resource");
        gs.deleteGame(gameId);
    }
}
