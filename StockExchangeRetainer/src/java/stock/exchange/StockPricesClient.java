/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package stock.exchange;

import java.io.File;
import java.util.ArrayList;
import org.json.JSONException;
import org.json.JSONObject;
import stock.exchange.prices.*;

/**
 *
 * @author kristijanzrno
 */
public class StockPricesClient {
    
    private static final String API_URL = "https://cloud.iexapis.com/stable/stock/market/";
    private static final String API_KEY = "pk_14d94e72ad454684b61e666ba5b6d8f2";
    
    public StockPricesClient(){
        
    }
    
        //Run only on first run
        public void initialisePrices(){
            
        }
    
        public void updatePrices(){
         StocksList stocksList = (StocksList) XMLUtils.unmarshallList(new File("prices.xml"), "stock.exchange.prices");
        // API limits is 100 stocks per call, but there are 3000+ prices to be fetched
        // To solve this, multiple api calls will be executed
        
        ArrayList<String> apiCalls = new ArrayList<>();
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
            XMLUtils.marshallList(stocksList, new File("prices.xml"));
        }});
        t.run();
        
    }
    
}
