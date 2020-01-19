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
import javax.ws.rs.PathParam;
import javax.ws.rs.core.MediaType;
import stock.exchange.prices.Stock;
import stock.exchange.prices.StocksList;

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

    public ExchangeRatesWS() {
        externalStockPrices = new StockPricesClient();

    }

    @Path("/updatePrices")
    @PUT
    @Consumes(MediaType.APPLICATION_XML)
    @Produces(MediaType.APPLICATION_XML)
    public StocksList updatePrices(StocksList stocks) {
        HashMap<String, Double> symbolValuePairs = externalStockPrices.getSymbolValuePairs();
        //commented out due to api quota
        //externalStockPrices.updatePrices();
        for (Stock stock : stocks.getStocks()) {
            stock.getPrice().setValue(symbolValuePairs.getOrDefault(stock.getCompanySymbol(), stock.getPrice().getValue()));
        }

        return stocks;
    }

    @Path("/updatePrice")
    @PUT
    @Consumes(MediaType.APPLICATION_XML)
    @Produces(MediaType.APPLICATION_XML)
    public Stock updatePrice(Stock stock) {
        HashMap<String, Double> symbolValuePairs = externalStockPrices.getSymbolValuePairs();
        //commented out due to api quota
        //externalStockPrices.updatePrices();
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
