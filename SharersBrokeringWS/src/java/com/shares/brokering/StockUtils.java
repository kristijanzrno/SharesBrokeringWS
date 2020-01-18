/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.shares.brokering;

import Data.*;
import docwebservices.CurrencyConversionWSService;
import java.io.File;
import java.util.List;
import javax.xml.ws.WebServiceRef;
import project.utils.XMLUtils;

/**
 *
 * @author kristijanzrno
 */
public class StockUtils {

    StocksList stocksList;
    StockExchangeRetainerClient stockExchangeClient;

    @WebServiceRef(wsdlLocation = "WEB-INF/wsdl/localhost_8080/CurrencyConvertor/CurrencyConversionWSService.wsdl")
    private CurrencyConversionWSService currencyConversionService;

    public StockUtils() {
        stocksList = (StocksList) XMLUtils.unmarshallObject(new File("stocks.xml"), "Data");
        stockExchangeClient = new StockExchangeRetainerClient();
    }

    public boolean buyStock(String username, String companySymbol, double value) {
        return false;
    }

    public boolean sellStock(String username, String companySymbol, double value) {
        return false;
    }

    public Stock getStock(String companySymbol, String currency) {
        for (Stock stock : stocksList.getStocks()) {
            if (stock.getCompanySymbol().toUpperCase().equals(companySymbol.toUpperCase())) {
                if (!currency.equals("USD") && !currency.isEmpty()) {
                    double conversionRate = getConversionRate(currency, "USD");
                    stock.getPrice().setCurrency(currency);
                    stock.getPrice().setValue(stock.getPrice().getValue() * conversionRate);
                }
                return stock;
            }
        }
        return null;
    }

    public List<Stock> getAllStocks(String currency) {
        updatePrices();
        if (!currency.equals("USD") && !currency.isEmpty()) {
            for (Stock stock : stocksList.getStocks()) {
                double conversionRate = getConversionRate(currency, "USD");
                stock.getPrice().setCurrency(currency);
                stock.getPrice().setValue(stock.getPrice().getValue() * conversionRate);
            }
        }
        return stocksList.getStocks();
    }

    public StocksList getAllStocksObj(String currency) {
        if (!currency.equals("USD") && !currency.isEmpty()) {
            for (Stock stock : stocksList.getStocks()) {
                double conversionRate = getConversionRate(currency, "USD");
                stock.getPrice().setCurrency(currency);
                stock.getPrice().setValue(stock.getPrice().getValue() * conversionRate);
            }
        }
        return stocksList;
    }

    public boolean updatePrice(String companySymbol) {
        Stock updatedStock = getStock(companySymbol, "");
        if (updatedStock == null) {
            return false;
        }
        if ((updatedStock = stockExchangeClient.updatePrice(updatedStock)) == null) {
            return false;
        }
        for (Stock stock : stocksList.getStocks()) {
            if (stock.getCompanySymbol().toUpperCase().equals(companySymbol.toUpperCase())) {
                stock = updatedStock;
            }
        }
        saveStocks();
        return true;
    }

    public boolean updatePrices() {
        StocksList updatedStocks = stockExchangeClient.updatePrices(stocksList);
        if (updatedStocks == null) {
            return false;
        }
        stocksList = updatedStocks;
        saveStocks();
        return true;
    }

    private void saveStocks() {
        XMLUtils.marshallObject(stocksList, new File("stocks.xml"));
    }

    private double getConversionRate(java.lang.String arg0, java.lang.String arg1) {
        docwebservices.CurrencyConversionWS port = currencyConversionService.getCurrencyConversionWSPort();
        return port.getConversionRate(arg0, arg1);
    }

}
