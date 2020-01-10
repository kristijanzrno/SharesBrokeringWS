/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.shares.brokering;


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
       StockInitialiser initialiser = new StockInitialiser();
       // Use during server setup only
       //initialiser.initialise();
       
       //Updating prices commented out due to api quota limit
       //initialiser.updatePrices();
       
       
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        
    }
    
}
