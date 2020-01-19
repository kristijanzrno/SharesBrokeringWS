/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package currency.rates;

import java.io.File;
import java.util.Iterator;
import org.json.JSONObject;
import project.utils.URLUtils;
import project.utils.XMLUtils;

/**
 *
 * @author kristijanzrno
 */
public class RatesClient {
    private static final String API_URL = "https://api.exchangeratesapi.io/latest?base=USD";
    
    public RatesClient(){
        fetchRates();
    }
    
    public boolean fetchRates(){
        //fetch
        try{
            saved.rates.Rates parsedRates  = new saved.rates.Rates();
            String json = URLUtils.readURL(API_URL);
            JSONObject rates = new JSONObject(json).getJSONObject("rates");
            if(rates == null)
                return false;
            Iterator<String> keys = rates.keys();
            while(keys.hasNext()){
                String key = keys.next();
                double value = rates.getDouble(key);
                saved.rates.Rate rate = new saved.rates.Rate();
                rate.setCurrency(key);
                rate.setValue(value);
                parsedRates.getRate().add(rate);
            }
            saveRates(parsedRates);
            
        }catch (Exception e){
            return false;
        }
       
        return true;
    }
    
    private boolean saveRates(saved.rates.Rates rates){
        XMLUtils.marshallObject(rates, new File("rates.xml"));
        return false;
    }

}
