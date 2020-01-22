/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.shares.brokering;

import Data.*;
import docwebservices.currency.convertor.CurrencyConversionWSService;
import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import project.utils.XMLUtils;

/**
 *
 * @author kristijanzrno
 */
public class StockUtils {
    
    // Class created for clean and readable stock management

    StocksList stocksList;
    // Utilises stock exchange retainer, to continously update the stock prices
    StockExchangeRetainerClient stockExchangeClient;

    CurrencyConversionWSService service;
    docwebservices.currency.convertor.CurrencyConversionWS port;

    // Takes currency conversion service as an input
    public StockUtils(CurrencyConversionWSService service) {
        // Initialising currency conversion service & service port once instead of at every "getConversionRate()" call
        // This improves the performance 3-4x on the large dataset (e.g. in getAllStocks() method)
        this.service = service;
        port = service.getCurrencyConversionWSPort();
        stocksList = (StocksList) XMLUtils.unmarshallObject(new File("stocks.xml"), "Data");
        stockExchangeClient = new StockExchangeRetainerClient();
    }

    // Function to buy a stock based on an account username, stock symbol and an amount
    public boolean buyStock(String username, String companySymbol, int value) {
        for (Stock stock : stocksList.getStocks()) {
            if (stock.getCompanySymbol().toUpperCase().equals(companySymbol.toUpperCase())) {
                if (stock.getNoOfAvailableShares() >= value && !stock.isBlocked() && value > 0) {
                    // If stock exists, and has available shares, add it to the user
                    if (new AccountUtils().addShare(username, stock.getCompanySymbol(), stock.getCompanyName(), value)) {
                        stock.setNoOfAvailableShares(stock.getNoOfAvailableShares() - value);
                        saveStocks();
                        return true;
                    }
                }
                break;
            }
        }
        return false;
    }

    public boolean sellStock(String username, String companySymbol, int value) {
        for (Stock stock : stocksList.getStocks()) {
            if (stock.getCompanySymbol().toUpperCase().equals(companySymbol.toUpperCase())) {
                // If stock exists, try removing it from the user
                if (!stock.isBlocked() && new AccountUtils().removeShare(username, stock.getCompanySymbol(), value)) {
                    stock.setNoOfAvailableShares(stock.getNoOfAvailableShares() + value);
                    saveStocks();
                    return true;
                }
                break;
            }
        }
        return false;
    }

    // Returns a Stock object, based on the company symbol and wanted currency
    // Automatically converts the price to the wanted currency if the wanted currency is not "USD" (default)
    public Stock getStock(String companySymbol, String currency) {
        for (Stock stock : stocksList.getStocks()) {
            if (stock.getCompanySymbol().toUpperCase().equals(companySymbol.toUpperCase())) {
                if (!currency.equals("USD") && !currency.isEmpty()) {
                    double conversionRate = getConversionRate(currency.toUpperCase(), "USD");
                    stock.getPrice().setCurrency(currency);
                    stock.getPrice().setValue(stock.getPrice().getValue() * conversionRate);
                }
                return stock;
            }
        }
        return null;
    }

    // Returns all the stocks with their prices converted to a wanted currency
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

    // Returns a list of stocks based on a search parameters
    public List<Stock> searchStocks(String searchFor, String orderBy, String currency) {
        List<Stock> stocks = new ArrayList<>();
        for (Stock stock : stocksList.getStocks()) {
            if (stock.getCompanyName().toLowerCase().contains(searchFor.toLowerCase()) || stock.getCompanySymbol().toLowerCase().contains(searchFor.toLowerCase()) || searchFor.isEmpty()) {
                // If the stock is searched for, convert its price to the wanted currency
                // and add it to the list that is gonna be returned
                if (!currency.equals("USD") && !currency.isEmpty()) {
                    double conversionRate = getConversionRate(currency.toUpperCase(), "USD");
                    stock.getPrice().setCurrency(currency.toUpperCase());
                    stock.getPrice().setValue(stock.getPrice().getValue() * conversionRate);
                }
                stocks.add(stock);
            }
        }
        // Order the list before returning it
        switch (orderBy) {
            case "name-asc":
                Collections.sort(stocks, (o1, o2) -> o1.getCompanyName().toLowerCase().compareTo(o2.getCompanyName().toLowerCase()));
                break;
            case "name-desc":
                Collections.sort(stocks, (o2, o1) -> o1.getCompanyName().compareTo(o2.getCompanyName()));
                break;
            case "price-lth":
                Collections.sort(stocks, Comparator.comparingDouble(o -> o.getPrice().getValue()));
                break;
            case "price-htl":
                Collections.sort(stocks, Comparator.comparingDouble((Stock o) -> o.getPrice().getValue()).reversed());
                break;
            case "available-shares-lth":
                Collections.sort(stocks, Comparator.comparingInt(o -> o.getNoOfAvailableShares()));
                break;
            case "available-shares-htl":
                Collections.sort(stocks, Comparator.comparingInt((Stock o) -> o.getNoOfAvailableShares()).reversed());
                break;
        }
        return stocks;
    }

    // Returns a list of all stock objects on the service
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

    // Function to update the price of a specific stock
    // Utilises the external REST service that presists the prices for an offline use
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
    
    // Function to update the price of a specific stock
    // Utilises the external REST service that presists the prices for an offline use
    public boolean updatePrices() {
        StocksList updatedStocks = stockExchangeClient.updatePrices(stocksList);
        if (updatedStocks == null) {
            return false;
        }
        stocksList = updatedStocks;
        saveStocks();
        return true;
    }

    // Changes the stock access (tradable yes/no)
    public boolean changeStockAccess(String companySymbol, boolean blocked) {
        getStock(companySymbol, "USD").setBlocked(blocked);
        saveStocks();
        return true;
    }

    // Saves the stocks utilising the jaxb marshalling to an xml file
    private void saveStocks() {
        XMLUtils.marshallObject(stocksList, new File("stocks.xml"));
    }

    // Function to get the currency list from an external SOAP service
    public List<String> getCurrencyList() {
        return port.getCurrencyCodes();
    }
    
    // Function to get the conversion rate from an external SOAP service
    public double getConversionRate(java.lang.String arg0, java.lang.String arg1) {
        return port.getConversionRate(arg0, arg1);
    }
    
}
