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
import docwebservices.CurrencyConversionWSService;
import javax.xml.ws.WebServiceRef;
import project.utils.XMLUtils;

/**
 *
 * @author kristijanzrno
 */
@WebService(serviceName = "BrokeringWS")
@Stateless()
public class BrokeringWS {

    @WebServiceRef(wsdlLocation = "WEB-INF/wsdl/localhost_8080/CurrencyConvertor/CurrencyConversionWSService.wsdl")
    private CurrencyConversionWSService service;

    @WebMethod(operationName = "test")
    public StocksList test() {
        StocksList stocks = (StocksList) XMLUtils.unmarshallObject(new File("stocks.xml"), "Data");
        StockExchangeRetainerClient client = new StockExchangeRetainerClient();
        stocks = client.updatePrices(stocks);
        return stocks;

    }

    @WebMethod(operationName = "getStock")
    public Stock getStock(String stockSymbol, String currency, String authUsername, String authPassword) {
        if(!Auth.authenticate(authUsername, authPassword))
            return null;
        List<Stock> stocks = ((StocksList) XMLUtils.unmarshallObject(new File("stocks.xml"), "Data")).getStocks();
        if (!currency.equals("USD") && !currency.isEmpty()) {
            double conversionRate = getConversionRate(currency, "USD");
            for (Stock stock : stocks) {
                if (stock.getCompanySymbol().equals(stockSymbol)) {
                    stock.getPrice().setCurrency(currency);
                    stock.getPrice().setValue(stock.getPrice().getValue() * conversionRate);
                }
                return stock;
            }
        }
        return null;
    }

    @WebMethod(operationName = "getAllStocks")
    public List<Stock> getAllStocks(String currency, String authUsername, String authPassword) {
        if(!Auth.authenticate(authUsername, authPassword))
            return null;
        List<Stock> stocks = ((StocksList) XMLUtils.unmarshallObject(new File("stocks.xml"), "Data")).getStocks();
        if (!currency.equals("USD") && !currency.isEmpty()) {
            double conversionRate = getConversionRate(currency, "USD");
            for (Stock stock : stocks) {
                stock.getPrice().setCurrency(currency);
                stock.getPrice().setValue(stock.getPrice().getValue() * conversionRate);
            }
        }
        return stocks;
    }

    @WebMethod(operationName = "buyStock")
    public boolean buyStock(String authUsername, String authPassword, String companySymbol, double value) {
        if(!Auth.authenticate(authUsername, authPassword))
            return false;
        return false;
    }

    @WebMethod(operationName = "sellStock")
    public boolean sellStock(String authUsername, String authPassword, String companySymbol, double value) {
        if(!Auth.authenticate(authUsername, authPassword))
            return false;
        return false;
    }
    
    @WebMethod(operationName = "createAccount")
    public boolean createAccount(String authUsername, String authPassword, String accountUsername, String accountPassword, int accountLevel){
        if(!Auth.authenticate(authUsername, authPassword))
            return false;
        return new AccountUtils().createAccount(accountUsername, accountPassword, accountLevel);
    }
    
    @WebMethod(operationName = "deleteAccount")
    public boolean deleteAccount(String authUsername, String authPassword, String accountUsername){
        if(!Auth.authenticateAdmin(authUsername, authPassword))
            return false;
        return new AccountUtils().deleteAccount(accountUsername);
    }
    
    private double getConversionRate(java.lang.String arg0, java.lang.String arg1) {
        // Note that the injected javax.xml.ws.Service reference as well as port objects are not thread safe.
        // If the calling of port operations may lead to race condition some synchronization is required.
        docwebservices.CurrencyConversionWS port = service.getCurrencyConversionWSPort();
        return port.getConversionRate(arg0, arg1);
    }

}
