/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package stock.exchange;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

/**
 *
 * @author kristijanzrno
 */
@WebListener
public class WSListener implements ServletContextListener {
    
    // Runs the initialisation service on the first run (same as in the core WS)
    // Use during server setup ONLY
    
    @Override
    public void contextInitialized(ServletContextEvent sce) {
        //StockPricesClient client = new StockPricesClient();
        //client.initialisePrices();
        //client.updatePrices();

    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {

    }

}
