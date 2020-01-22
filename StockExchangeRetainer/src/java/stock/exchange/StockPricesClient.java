/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package stock.exchange;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.HashMap;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import org.json.JSONException;
import org.json.JSONObject;
import project.utils.URLUtils;
import project.utils.XMLUtils;
import stock.exchange.values.CompanyPrice;
import stock.exchange.values.Price;
import stock.exchange.values.StockPricesList;

/**
 *
 * @author kristijanzrno
 */
public class StockPricesClient {

    // API Info
    private static final String API_URL = "https://cloud.iexapis.com/stable/stock/market/";
    private static final String API_KEY = "pk_14d94e72ad454684b61e666ba5b6d8f2";

    public StockPricesClient() {
    }

    // Executed only on first run to create the "prices.xml" file
    // When this is done, this file will be updated with new prices from an online source
    public void initialisePrices() {
        // Creating the required file
        File f = new File("prices.xml");
        if (!f.exists()) {
            try {
                f.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        // Populating it with price objects
        URL fileURL = this.getClass().getClassLoader().getResource("stocks.txt");
        File stocksFile = new File("");
        try {
            stocksFile = new File(fileURL.toURI());
        } catch (URISyntaxException ex) {
        }
        StockPricesList stocksPrices = new StockPricesList();
        try (BufferedReader br = new BufferedReader(new FileReader(stocksFile))) {
            String line;
            while ((line = br.readLine()) != null) {
                CompanyPrice stockPrice = new CompanyPrice();
                stockPrice.setCompanySymbol(line.split(";;")[0]);
                Price price = new Price();
                price.setCurrency("USD");
                price.setValue(1.0);
                GregorianCalendar calendar = new GregorianCalendar();
                XMLGregorianCalendar xmlDate = DatatypeFactory.newInstance().newXMLGregorianCalendar(calendar);
                price.setLastUpdated(xmlDate);
                stockPrice.setPrice(new Price());
                stocksPrices.getStockPrice().add(stockPrice);
            }
        } catch (Exception ex) {
        }

        XMLUtils.marshallObject(stocksPrices, new File("prices.xml"));
    }

    // Updating the locally saved company stock prices
    public void updatePrices() {
        StockPricesList stockPricesList = (StockPricesList) XMLUtils.unmarshallObject(new File("prices.xml"), "stock.exchange.values");
        // API limits is 100 stocks per call, but there are 3000+ prices to be fetched
        // To solve this, multiple api calls will be executed
        ArrayList<String> apiCalls = new ArrayList<>();
        String symbols = "";
        int counter = 0;
        for (int i = 0; i < stockPricesList.getStockPrice().size(); i++) {
            symbols += stockPricesList.getStockPrice().get(i).getCompanySymbol().toLowerCase() + ",";
            counter++;
            if (counter == 99 || i == stockPricesList.getStockPrice().size() - 1) {
                String call = API_URL + "batch?symbols=" + symbols + "&types=quote&token=" + API_KEY;
                apiCalls.add(call);
                symbols = "";
                counter = 0;
            }
        }
        // Testing call
        // apiCalls.add("https://cloud.iexapis.com/stable/stock/market/batch?symbols=aapl,fb&types=quote&token=pk_14d94e72ad454684b61e666ba5b6d8f2");
        // Use the above call for testing

        // Making the requests asynchronously to let the service keep running 
        // normally while the requests are handed in
        ArrayList<JSONObject> list = new ArrayList<>();
        Thread t = new Thread(new Runnable() {
            public void run() {
                for (String call : apiCalls) {
                    try {
                        String json = URLUtils.readURL(call);
                        JSONObject obj = new JSONObject(json);
                        list.add(obj);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    try {
                        Thread.sleep(2000);
                    } catch (InterruptedException ex) {}
                }
                for (int i = 0; i < stockPricesList.getStockPrice().size(); i++) {

                    JSONObject symbol = null;
                    for (JSONObject object : list) {
                        try {
                            symbol = object.getJSONObject(stockPricesList.getStockPrice().get(i).getCompanySymbol());
                        } catch (JSONException ex) {}
                        if (symbol != null) {
                            break;
                        }
                    }
                    if (symbol != null) {
                        try {
                            // At this point, the json object has been decomposed to the required information
                            String price = symbol.getJSONObject("quote").get("latestPrice").toString();
                            if (price != null && !price.isEmpty()) {
                                // Assigning the newly fetched price to the price element in the local prices list
                                double value = Double.parseDouble(price);
                                stockPricesList.getStockPrice().get(i).getPrice().setValue(value);
                            }
                        } catch (Exception e) {}
                    }
                }
                // Saving the newly updated prices
                XMLUtils.marshallObject(stockPricesList, new File("prices.xml"));
            }
        });
        t.run();

    }

    // Function to return all the saved stock prices
    public StockPricesList getStockPrices() {
        return (StockPricesList) XMLUtils.unmarshallObject(new File("prices.xml"), "stock.exchange.values");
    }
    
    // Function to return a saved stock price based on the company symbol
    public CompanyPrice getStockPrice(String symbol) {
        for (CompanyPrice price : getStockPrices().getStockPrice()) {
            if (price.getCompanySymbol().toUpperCase().equals(symbol.toUpperCase())) {
                return price;
            }
        }
        return null;
    }

    // Returning the symbol-price as an hashmap to improve the searching performance when needed
    // Makes a big difference when working with 3000 stocks (in comparison with looping over the prices list over and over again)
    public HashMap<String, Double> getSymbolValuePairs() {
        HashMap<String, Double> symbolValuePairs = new HashMap();
        StockPricesList prices = (StockPricesList) XMLUtils.unmarshallObject(new File("prices.xml"), "stock.exchange.values");
        for (CompanyPrice price : prices.getStockPrice()) {
            symbolValuePairs.put(price.getCompanySymbol(), price.getPrice().getValue());
        }
        return symbolValuePairs;
    }
}
