/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package stock.exchange;

import java.io.File;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Produces;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.MediaType;

/**
 * REST Web Service
 *
 * @author kristijanzrno
 */
@Path("ExchangeRates")
public class ExchangeRatesResource {

    @Context
    private UriInfo context;

    /**
     * Creates a new instance of ExchangeRatesResource
     */
    public ExchangeRatesResource() {
        
    }
    
     
    @GET
    @Produces(MediaType.APPLICATION_XML)
    public String getXml() {
        return "";
        
    }
    
    
    @Path("updatePrices")
    @GET
    @Consumes(MediaType.APPLICATION_XML)
    @Produces(MediaType.APPLICATION_XML)
    public String updatePrices(){
        return "";
    }

    @Path("/getprices")
    @GET
    @Produces(MediaType.APPLICATION_XML)
    public String getPrices(){
        return "prices";
    }
    
    @Path("/getprice/{symbol}")
    @GET
    @Produces(MediaType.APPLICATION_XML)
    @Consumes(MediaType.APPLICATION_XML)
    public String getPrice(@PathParam("symbol") String symbol){
        return symbol;
    }
}
