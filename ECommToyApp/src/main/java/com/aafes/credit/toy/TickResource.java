/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.aafes.credit.toy;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.concurrent.TimeUnit;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.UriInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * REST Web Service
 *
 * @author Ramya
 */
@Path("/tick")
public class TickResource {
    
    private static final Logger log = LoggerFactory.getLogger(TickResource.class.getName());

    @Context
    private UriInfo context;

    /**
     * Creates a new instance of TickResource
     */
    public TickResource() {
    }

    /**
     * Retrieves representation of an instance of
     * com.aafes.credit.toy.TickResource
     *
     * @return an instance of java.lang.String
     */
    @POST
    @Consumes(MediaType.TEXT_PLAIN)
    @Produces(MediaType.TEXT_PLAIN)
    public String processPost(String input) throws InterruptedException {
        
        String strHostName = getHostName();
        
        
        String response = null;
        
        //connect
        ToyDAO toyDAO = new ToyDAO();
        log.info("Trying to connect to hadcass01");
        toyDAO.connect("hadcass01");
        log.info("Connected to hadcass01");
        
        //look up 
        log.info("Trying to look up: " +input);
        Toy result = toyDAO.find(input);
        log.info("Result: " +result);
        
        if (result == null)
        {
            //write
            Toy toy1 = new Toy();
            toy1.setColumn1(input);
            toy1.setColumn2(strHostName);
            toyDAO.save(toy1);  
            response = input + " is not available in the table, so added now";
        } 
        else 
        {
            response = input + " is available in the table";
        }
        
        //wait 5 secs
        TimeUnit.MICROSECONDS.sleep(50);
        //log.info("Wait 5 seconds");
         
        //return
        log.info("Return response: " +response);
        return response;
    }

    private String getHostName() 
    {
        InetAddress ip;
        String hostname="";
        try {
                ip = InetAddress.getLocalHost();
                hostname = ip.getHostName();
                //System.out.println("Your current IP address : " + ip);
                //System.out.println("Your current Hostname : " + hostname);
            } 
        catch (UnknownHostException e) 
        {
            e.printStackTrace();
        }
        return hostname;
    }
}
