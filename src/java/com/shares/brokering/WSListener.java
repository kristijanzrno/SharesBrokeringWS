/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.shares.brokering;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
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
