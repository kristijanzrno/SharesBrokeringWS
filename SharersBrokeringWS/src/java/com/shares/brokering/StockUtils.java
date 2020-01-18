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
    
    public StockUtils(){
        stocksList = (StocksList) XMLUtils.unmarshallObject(new File("stocks.xml"), "Data");
    }
    
    public boolean buyStock(){
        return false;
    }
    
    public boolean sellStock(){
        return false;
    }
    
    public Stock getStock(String symbol){
        for(Stock stock : stocksList.getStocks())
            if(stock.getCompanySymbol().toUpperCase().equals(symbol.toUpperCase()))
                return stock;
        return null;
    }
    
    public List<Stock> getAllStocks(){
        return stocksList.getStocks();
    }
    
    public StocksList getAllStocksObj(){
        return stocksList;
    }
    
    public boolean updatePrice(String companySymbol){
        return true;
    }
    
    public boolean updatePrices(){
        StockExchangeRetainerClient client = new StockExchangeRetainerClient();
        StocksList updatedStocks = client.updatePrices(stocksList);
        if(updatedStocks == null)
            return false;
        stocksList = updatedStocks;
        return true;
    }
    
    private void saveStocks(){
        
    }
    
}
