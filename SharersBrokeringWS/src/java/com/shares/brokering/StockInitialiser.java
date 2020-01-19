/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.shares.brokering;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;
import project.utils.XMLUtils;

/**
 *
 * @author kristijanzrno
 */
public class StockInitialiser {

    public StockInitialiser() {
    }

    public void initialise() {
        File f = new File("stocks.xml");
        if (!f.exists()) {
            try {
                f.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        URL fileURL = this.getClass().getClassLoader().getResource("stocks.txt");
        File stocksFile = new File("");
        try {
            stocksFile = new File(fileURL.toURI());
        } catch (URISyntaxException ex) {
            Logger.getLogger(WSListener.class.getName()).log(Level.SEVERE, null, ex);
        }
        Data.StocksList stocks = new Data.StocksList();
        try (BufferedReader br = new BufferedReader(new FileReader(stocksFile))) {
            String line;
            while ((line = br.readLine()) != null) {
                Data.Stock stock = new Data.Stock();
                stock.setCompanySymbol(line.split(";;")[0]);
                stock.setCompanyName(line.split(";;")[1]);
                stock.setNoOfAvailableShares(50000);
                Data.Price price = new Data.Price();
                price.setCurrency("USD");
                price.setLastUpdated(XMLUtils.currentDate());
                price.setValue(0.0);
                stock.setPrice(price);
                stocks.getStocks().add(stock);
            }
        } catch (Exception ex) {
            Logger.getLogger(WSListener.class.getName()).log(Level.SEVERE, null, ex);
        }
        XMLUtils.marshallObject(stocks, new File("stocks.xml"));
    }

}
