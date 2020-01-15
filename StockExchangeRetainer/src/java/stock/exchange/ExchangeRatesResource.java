/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package stock.exchange;

import java.util.HashMap;
import javafx.util.Pair;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Produces;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import stock.exchange.prices.Stock;
import stock.exchange.prices.StocksList;
import stock.exchange.values.StockPrice;

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

    @Path("/updatePrices")
    @PUT
    @Consumes(MediaType.APPLICATION_XML)
    @Produces(MediaType.APPLICATION_XML)
    public StocksList updatePrices(StocksList stocks) {
        StockPricesClient client = new StockPricesClient();
        HashMap<String, Double> symbolValuePairs = client.getSymbolValuePairs();
        //commented out due to api quota
        //client.updatePrices();
        for (Stock stock : stocks.getStocks()) {
            System.out.println(stock.getCompanySymbol());
            stock.getPrice().setValue(symbolValuePairs.getOrDefault(stock.getCompanySymbol(), stock.getPrice().getValue()));
        }
        return stocks;
    }

    @Path("/updatePrice")
    @PUT
    @Consumes(MediaType.APPLICATION_XML)
    @Produces(MediaType.APPLICATION_XML)
    public Stock updatePrice(Stock stock) {
        StockPricesClient client = new StockPricesClient();
        HashMap<String, Double> symbolValuePairs = client.getSymbolValuePairs();
        //commented out due to api quota
        //client.updatePrices();
        stock.getPrice().setValue(symbolValuePairs.getOrDefault(stock.getCompanySymbol(), stock.getPrice().getValue()));
        return stock;
    }

    @Path("/getprices")
    @GET
    @Produces(MediaType.APPLICATION_XML)
    public String getPrices() {
        return "";
    }

    @Path("/getprice/{symbol}")
    @GET
    @Produces(MediaType.APPLICATION_XML)
    public String getPrice(@PathParam("symbol") String symbol) {
        return symbol;
    }
}
