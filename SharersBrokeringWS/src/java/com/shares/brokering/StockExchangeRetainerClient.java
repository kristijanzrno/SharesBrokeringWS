/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.shares.brokering;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;

/**
 *
 * @author kristijanzrno
 */
public class StockExchangeRetainerClient {
    private WebTarget webTarget;
    private Client client;
    private static final String SERVICE_URL = "http://localhost:8080/StockExchangeRetainer/webresources";
    
    public StockExchangeRetainerClient(){
        client = ClientBuilder.newClient();
        webTarget = client.target(SERVICE_URL);
    }
    
    public String getPrices(){
        return webTarget.path("ExchangeRates").path("getprices").request().get(String.class);
    }
    
    public String getPrice(String symbol){
        return webTarget.path("ExchangeRates").path("getprice").path(symbol).request().get(String.class);
    }
    
 
    
}
