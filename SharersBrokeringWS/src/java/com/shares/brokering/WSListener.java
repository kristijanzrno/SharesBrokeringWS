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
public class WSListener implements ServletContextListener {

    // Listens when the service is deployed
    // And initialises it on the first use if the line is uncommented
    
    @Override
    public void contextInitialized(ServletContextEvent sce) {
        StockInitialiser initialiser = new StockInitialiser();
        //Use during server setup only
        //initialiser.initialise();

    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {

    }

}
