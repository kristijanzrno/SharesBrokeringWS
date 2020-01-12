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
    public String test(){
        List<Stock> stocks = XMLUtils.unmarshallList(new File("stocks.xml")).getStocks();
        for(Stock stock : stocks){
            if(stock.getPrice().getValue() == 0.0)
                System.out.println(stock.getCompanySymbol());
        }
        return "";
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
        List<Stock> stocks = XMLUtils.unmarshallList(new File("stocks.xml")).getStocks();
        if(!currency.equals("USD")){
            double conversionRate = getConversionRate("USD", currency);
            for(Stock stock : stocks){
                stock.getPrice().setCurrency(currency);
                stock.getPrice().setValue(stock.getPrice().getValue()*conversionRate);
            }
        }
        return stocks;
    }
    
    @WebMethod(operationName = "buyStock")
    public boolean buyStock(String username, String companySymbol, double value){
        return false;
    }
    
    @WebMethod(operationName = "sellStock")
    public boolean sellStock(String username, String companySymbol, double value){
        return false;
    }

    private double getConversionRate(java.lang.String arg0, java.lang.String arg1) {
        // Note that the injected javax.xml.ws.Service reference as well as port objects are not thread safe.
        // If the calling of port operations may lead to race condition some synchronization is required.
        docwebservices.CurrencyConversionWS port = service.getCurrencyConversionWSPort();
        return port.getConversionRate(arg0, arg1);
    }


    
}