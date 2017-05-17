/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.desktopinlamninguppgift2.resources;

import com.mycompany.desktopinlamninguppgift2.models.Game;
import com.mycompany.desktopinlamninguppgift2.services.GameService;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
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
}
