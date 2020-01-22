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

    // Simple REST service to preserve the fetched currency rates on an local xml file
    // for the offline use
    public CurrencyRatesWS() {
        // Prices are immediately updated (to an rates.xml file), 
        // but if the service is offline, the previously saved rates.xml will be read
        RatesClient rates = new RatesClient();
    }

    // Returns a list of updated currency rates
    @Path("/getRates")
    @GET
    @Produces(MediaType.APPLICATION_XML)
    public Rates getRates() {
        return (Rates) XMLUtils.unmarshallObject(new File("rates.xml"), "saved.rates");
    }

}
