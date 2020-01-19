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
import stock.exchange.values.Price;
import stock.exchange.values.StockPrice;
import stock.exchange.values.StockPricesList;

/**
 *
 * @author kristijanzrno
 */
public class StockPricesClient {

    private static final String API_URL = "https://cloud.iexapis.com/stable/stock/market/";
    private static final String API_KEY = "pk_14d94e72ad454684b61e666ba5b6d8f2";

    public StockPricesClient() {

    }

    //Run only on first run
    public void initialisePrices() {
    File f = new File("prices.xml");
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
        }
        StockPricesList stocksPrices = new StockPricesList();
        try (BufferedReader br = new BufferedReader(new FileReader(stocksFile))) {
            String line;
            while ((line = br.readLine()) != null) {
                StockPrice stockPrice = new StockPrice();
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

    public void updatePrices() {
        StockPricesList stockPricesList = (StockPricesList) XMLUtils.unmarshallObject(new File("prices.xml"), "stock.exchange.values");
        //StocksList stocksList = (StocksList) XMLUtils.unmarshallObject(new File("prices.xml"), "stock.exchange.prices");
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
        //Testing call
        //apiCalls.add("https://cloud.iexapis.com/stable/stock/market/batch?symbols=aapl,fb&types=quote&token=pk_14d94e72ad454684b61e666ba5b6d8f2");
        ArrayList<JSONObject> list = new ArrayList<>();
        Thread t = new Thread(new Runnable() {
            public void run() {
                for (String call : apiCalls) {
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
                for (int i = 0; i < stockPricesList.getStockPrice().size(); i++) {

                    JSONObject symbol = null;
                    for (JSONObject object : list) {
                        try {
                            symbol = object.getJSONObject(stockPricesList.getStockPrice().get(i).getCompanySymbol());
                        } catch (JSONException ex) {
                        }
                        if (symbol != null) {
                            break;
                        }
                    }
                    if (symbol != null) {
                        try {
                            System.out.println("reached");
                            String price = symbol.getJSONObject("quote").get("latestPrice").toString();
                            if (price != null && !price.isEmpty()) {
                                double value = Double.parseDouble(price);
                                stockPricesList.getStockPrice().get(i).getPrice().setValue(value);
                            }
                        } catch (Exception e) {
                        }
                    }

                }
                XMLUtils.marshallObject(stockPricesList, new File("prices.xml"));
            }
        });
        t.run();

    }

    public HashMap<String, Double> getSymbolValuePairs(){
        HashMap<String, Double> symbolValuePairs = new HashMap();
        StockPricesList prices = (StockPricesList) XMLUtils.unmarshallObject(new File("prices.xml"), "stock.exchange.values");
        for(StockPrice price : prices.getStockPrice())
            symbolValuePairs.put(price.getCompanySymbol(), price.getPrice().getValue());
        return symbolValuePairs;
    }
}
