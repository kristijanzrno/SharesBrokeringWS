/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package currency.rates;

import java.io.File;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PUT;
import javax.ws.rs.core.MediaType;
import javax.xml.bind.JAXBIntrospector;

/**
 * REST Web Service
 *
 * @author kristijanzrno
 */
@Path("generic")
public class CurrencyRates {

    @Context
    private UriInfo context;

    /**
     * Creates a new instance of CurrencyRates
     */
    public CurrencyRates() {
        Rates rates = new Rates();
    }

    /**
     * Retrieves representation of an instance of currency.rates.CurrencyRates
     * @return an instance of java.lang.String
     */
    
    @GET
    @Produces(MediaType.APPLICATION_XML)
    public String getXml() {
         return XMLUtils.readToString(new File("rates.xml"));
    }

    /**
     * PUT method for updating or creating an instance of CurrencyRates
     * @param content representation for the resource
     */
    @PUT
    @Consumes(MediaType.APPLICATION_XML)
    public void putXml(String content) {
       
    }
}
