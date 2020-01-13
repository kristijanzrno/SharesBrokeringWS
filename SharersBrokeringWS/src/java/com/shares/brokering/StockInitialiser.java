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
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.json.*;

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
                price.setValue(0.0);
                stock.setPrice(price);
                stocks.getStocks().add(stock);
            }
        } catch (Exception ex) {
            Logger.getLogger(WSListener.class.getName()).log(Level.SEVERE, null, ex);
        }
        XMLUtils.marshallList(stocks, new File("stocks.xml"));
    }
    
   /*
    public void updatePrices(){
        Data.StocksList stocksList = XMLUtils.unmarshallList(new File("stocks.xml"));
        // API limits is 100 stocks per call, but there are 3000+ prices to be fetched
        // To solve this, multiple api calls will be executed
        ArrayList<String> apiCalls = new ArrayList<String>();
        String symbols = "";
        int counter = 0;
        for(int i = 0; i<stocksList.getStocks().size(); i++){
            symbols += stocksList.getStocks().get(i).getCompanySymbol().toLowerCase()+",";
            counter++;
            if(counter == 99 || i == stocksList.getStocks().size()-1){
                String call = API_URL + "batch?symbols="+symbols+"&types=quote&token="+API_KEY;
                apiCalls.add(call);
                symbols = "";
                counter = 0;
            }
        }
        //Testing call
        //apiCalls.add("https://cloud.iexapis.com/stable/stock/market/batch?symbols=aapl,fb&types=quote&token=pk_14d94e72ad454684b61e666ba5b6d8f2");
        ArrayList<JSONObject> list = new ArrayList<>();
        Thread t = new Thread(new Runnable() {
            public void run() { 
            for(String call : apiCalls){
                try {
                    String json = URLUtils.readURL(call);
                    //System.out.println(call);
                    JSONObject obj = new JSONObject(json);
                    list.add(obj);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException ex) {
                }
            }
            for(int i = 0; i<stocksList.getStocks().size(); i++){
                
                JSONObject symbol = null;
                for(JSONObject object : list){
                    try {
                        symbol = object.getJSONObject(stocksList.getStocks().get(i).getCompanySymbol());
                    } catch (JSONException ex) {
                    }
                    if(symbol != null)
                        break;
                }
                if(symbol != null){
                    try {
                        System.out.println("reached");
                        String price = symbol.getJSONObject("quote").get("latestPrice").toString();
                        if(price != null && !price.isEmpty()){
                            double value = Double.parseDouble(price);
                            stocksList.getStocks().get(i).getPrice().setValue(value);
                        }
                    } catch (Exception e) {
                    }
                }
                
            }
            XMLUtils.marshallList(stocksList, new File("stocks.xml"));
        }});
        t.run();
        
    }*/
    
}
