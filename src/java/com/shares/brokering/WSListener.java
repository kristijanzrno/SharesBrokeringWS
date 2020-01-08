/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.shares.brokering;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

/**
 *
 * @author kristijanzrno
 */
@WebListener
public class WSListener implements ServletContextListener{

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        // TESTING
        File f = new File("testing.xml");
        if(!f.exists()){
            try{
                f.createNewFile();
            }catch(IOException e){
                e.printStackTrace();
            }
        }
        Data.StocksList stocks = new Data.StocksList();
        for(int i = 0; i<10; i++){
            Data.Stock stock = new Data.Stock();
            stock.setCompanyName("Company "+i);
            stock.setCompanySymbol("Symbol "+i);
            stock.setNoOfAvailableShares(i);
            Data.Price price = new Data.Price();
            price.setCurrency("USD"+i);
            price.setValue(1000-i);
            stock.setPrice(price);
            stocks.getStocks().add(stock);
        }
        try {
            FileOutputStream outputStream = new FileOutputStream(f);
            XMLUtils.marshallList(stocks, outputStream);
            outputStream.close();
        } catch (Exception ex) {
            Logger.getLogger(WSListener.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        
    }
    
}
