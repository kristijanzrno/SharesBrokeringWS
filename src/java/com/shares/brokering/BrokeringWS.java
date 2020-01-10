/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.shares.brokering;

import java.io.File;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.jws.WebService;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.ejb.Stateless;
import Data.*;
/**
 *
 * @author kristijanzrno
 */
@WebService(serviceName = "BrokeringWS")
@Stateless()
public class BrokeringWS {

    /**
     * This is a sample web service operation
     */
    @WebMethod(operationName = "hello")
    public String hello(@WebParam(name = "name") String txt) {
        return "Hello " + txt + " !";
        
    }
    
    @WebMethod(operationName = "getStock")
    public Stock getStock(String stockSymbol, String currency){
        Stock stock = new Stock();
        stock.setCompanyName("name");
        stock.setCompanySymbol(stockSymbol);
        Price price = new Price();
        price.setCurrency(currency);
        price.setValue(234);
        stock.setPrice(price);
        return stock;
    }
    
    @WebMethod(operationName = "getAllStocks")
    public List<Stock> getAllStocks(String currency){
        return XMLUtils.unmarshallList(new File("stocks.xml")).getStocks();
    }
    
    @WebMethod(operationName = "buyStock")
    public boolean buyStock(String username, String companySymbol, double value){
        return false;
    }
    
    @WebMethod(operationName = "sellStock")
    public boolean sellStock(String username, String companySymbol, double value){
        return false;
    }
}
