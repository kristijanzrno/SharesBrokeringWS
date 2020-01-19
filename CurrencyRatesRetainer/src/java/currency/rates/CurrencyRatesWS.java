/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package currency.rates;

import java.io.File;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Produces;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;
import project.utils.XMLUtils;
import saved.rates.Rates;

/**
 * REST Web Service
 *
 * @author kristijanzrno
 */
@Path("CurrencyRates")
public class CurrencyRatesWS {

    @Context
    private UriInfo context;

    
    public CurrencyRatesWS() {
        RatesClient rates = new RatesClient();
    }

    @Path("/getRates")
    @GET
    @Produces(MediaType.APPLICATION_XML)
    public Rates getRates() {
        return (Rates) XMLUtils.unmarshallObject(new File("rates.xml"), "saved.rates");
    }

}
