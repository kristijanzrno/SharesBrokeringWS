/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.shares.brokering;

import Data.Stock;
import Data.StocksList;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Response;

/**
 *
 * @author kristijanzrno
 */
public class StockExchangeRetainerClient {

    private WebTarget webTarget;
    private Client client;
    private static final String SERVICE_URL = "http://localhost:8080/StockExchangeRetainer/webresources";

    public StockExchangeRetainerClient() {
        client = ClientBuilder.newClient();
        webTarget = client.target(SERVICE_URL);
    }

    public String getPrices() {
        return webTarget.path("ExchangeRates").path("getprices").request().get(String.class);
    }

    public String getPrice(String symbol) {
        return webTarget.path("ExchangeRates").path("getprice").path(symbol).request().get(String.class);
    }

    public Stock updatePrice(Stock stock) {
        return webTarget.path("ExchangeRates").path("updatePrice").request().get(Stock.class);
    }

    public StocksList updatePrices(StocksList stocks) {
        Response resp = webTarget.path("ExchangeRates").path("updatePrices").request().put(Entity.xml(stocks));
        stocks = resp.readEntity(StocksList.class);
        return stocks;
    }

}
