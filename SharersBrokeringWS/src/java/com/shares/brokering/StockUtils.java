/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.shares.brokering;

import Data.*;
import java.io.File;
import java.util.List;
import project.utils.XMLUtils;

/**
 *
 * @author kristijanzrno
 */
public class StockUtils {

    StocksList stocksList;
    StockExchangeRetainerClient stockExchangeClient;


    public StockUtils() {
        stocksList = (StocksList) XMLUtils.unmarshallObject(new File("stocks.xml"), "Data");
        stockExchangeClient = new StockExchangeRetainerClient();
    }

    public boolean buyStock(String username, String companySymbol, int value) {
        for (Stock stock : stocksList.getStocks()) {
            if (stock.getCompanySymbol().toUpperCase().equals(companySymbol.toUpperCase())) {
                if (stock.getNoOfAvailableShares() >= value) {
                    //add stock to the user
                    if (new AccountUtils().addShare(username, stock.getCompanySymbol(), stock.getCompanyName(), value)) {
                        stock.setNoOfAvailableShares(stock.getNoOfAvailableShares() - value);
                        saveStocks();
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public boolean sellStock(String username, String companySymbol, int value) {
        for (Stock stock : stocksList.getStocks()) {
            if (stock.getCompanySymbol().toUpperCase().equals(companySymbol.toUpperCase())) {
                    //remove stock from the user
                    if (new AccountUtils().removeShare(username, stock.getCompanySymbol(), value)) {
                        stock.setNoOfAvailableShares(stock.getNoOfAvailableShares() + value);
                        saveStocks();
                        return true;
                    }
                }
        }
        return false;
    }

    public Stock getStock(String companySymbol, String currency) {
        for (Stock stock : stocksList.getStocks()) {
            if (stock.getCompanySymbol().toUpperCase().equals(companySymbol.toUpperCase())) {
                if (!currency.equals("USD") && !currency.isEmpty()) {
                    double conversionRate = getConversionRate("USD", currency.toUpperCase());
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

    private static double getConversionRate(java.lang.String arg0, java.lang.String arg1) {
        docwebservices.CurrencyConversionWSService service = new docwebservices.CurrencyConversionWSService();
        docwebservices.CurrencyConversionWS port = service.getCurrencyConversionWSPort();
        return port.getConversionRate(arg0, arg1);
    }

}
