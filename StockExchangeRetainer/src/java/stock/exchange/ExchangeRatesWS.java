/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package stock.exchange;

import java.util.HashMap;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Produces;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;
import project.utils.XMLUtils;
import stock.exchange.prices.*;
import stock.exchange.values.*;

/**
 * REST Web Service
 *
 * @author kristijanzrno
 */
@Path("ExchangeRates")
public class ExchangeRatesWS {

    @Context
    private UriInfo context;

    StockPricesClient externalStockPrices;

    // Exchange Rates REST Web Service which utilises another External REST service
    // to fetch the prices, and then saves those prices locally for an offline use
    public ExchangeRatesWS() {
        externalStockPrices = new StockPricesClient();
    }

    // Web method to update the core web service stock prices
    // Takes the stock prices on the input, updates their prices, and outputs them back
    @Path("/updatePrices")
    @PUT
    @Consumes(MediaType.APPLICATION_XML)
    @Produces(MediaType.APPLICATION_XML)
    public StocksList updatePrices(StocksList stocks) {
        HashMap<String, Double> symbolValuePairs = externalStockPrices.getSymbolValuePairs();
        // Commented out due to api quota
        // Uncomment to fetch the new prices from online rest service
        // externalStockPrices.updatePrices();
   
        //  Updating the price, and resetting the last-updated date
        for (Stock stock : stocks.getStocks()) {
            stock.getPrice().setValue(symbolValuePairs.getOrDefault(stock.getCompanySymbol(), stock.getPrice().getValue()));
            stock.getPrice().setLastUpdated(XMLUtils.currentDate());
        }
        return stocks;
    }

    // Web method to update a core web service stock price
    // Takes a stock price on the input, updates its prices, and outputs it back
    @Path("/updatePrice")
    @PUT
    @Consumes(MediaType.APPLICATION_XML)
    @Produces(MediaType.APPLICATION_XML)
    public Stock updatePrice(Stock stock) {
        HashMap<String, Double> symbolValuePairs = externalStockPrices.getSymbolValuePairs();
        // Commented out due to api quota
        // externalStockPrices.updatePrices();
        stock.getPrice().setValue(symbolValuePairs.getOrDefault(stock.getCompanySymbol(), stock.getPrice().getValue()));
        stock.getPrice().setLastUpdated(XMLUtils.currentDate());
        return stock;
    }

    // Returns all the saved prices as an list
    @Path("/getprices")
    @GET
    @Produces(MediaType.APPLICATION_XML)
    public StockPricesList getPrices() {
        return externalStockPrices.getStockPrices();
    }

}
