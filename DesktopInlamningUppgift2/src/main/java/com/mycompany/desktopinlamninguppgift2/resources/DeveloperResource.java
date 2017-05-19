/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.desktopinlamninguppgift2.resources;
import com.mycompany.desktopinlamninguppgift2.models.Developer;
import com.mycompany.desktopinlamninguppgift2.services.DeveloperService;
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

@Path("/developers")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class DeveloperResource {
    DeveloperService ds = new DeveloperService();
    
    @GET
    public List<Developer> getAllDevelopers() {
        System.out.println("Kommer in på första.");
        return ds.getDevelopers();
        
    }
    
    @POST
    public Developer addDeveloper(Developer developer){
        return ds.addDeveloper(developer);
    }
    
    
    @GET
    @Path("/{developerId}")
    public Developer getDeveloper(@PathParam("developerId") int developerId){
        System.out.println("kommer in i getDeveloper i resource");
        return ds.getDeveloper(developerId);
    }
    
    @PUT
    @Path("/{developerId}")
    public void updateDeveloper(@PathParam("developerId") int developerId, Developer developer){
        System.out.println("Kommer in i updatedeveloper i resource");
        developer.setDeveloperId(developerId);
        ds.updateDeveloper(developer);
    }
    
    @DELETE
    @Path("/{developerId}")
    public void deleteDeveloper(@PathParam("developerId") int developerId){
        System.out.println("KOmmer in i deleteDeveloper i resource");
        
        ds.deleteDeveloper(developerId);
    }
    
    @Path("/{developerId}/games")
    public GameResource getGameResource(){
        return new GameResource();
    }
    

}
