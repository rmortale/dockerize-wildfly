package com.example.demo.hello;

import javax.ws.rs.GET;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

@Path("hello")
public class RestResource {
    
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Hello hello(){
        return new Hello("Hello from Jakarta EE");
    }
}
