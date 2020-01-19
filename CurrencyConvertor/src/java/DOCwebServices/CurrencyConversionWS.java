/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package DOCwebServices;

import java.util.ArrayList;
import java.util.List;
import javax.jws.WebService;
import saved.currency.rates.*;

/**
 *
 * @author taha-m
 */
@WebService()
public class CurrencyConversionWS {
    
    public CurrencyConversionWS(){
        updateRates();
    }

    public enum ExRate {
        AUD ("Australian Dollar", 1.4513863882),
        BGN ("Bulgarian Lev", 1.7607129996),
        BRL ("Brazilian Real", 4.1762693554),
        CAD ("Canadian Dollar", 1.3051854519),
        CHF ("Swiss Franc", 0.966510623),
        CNY ("Chinese Yuan", 6.8586604249),
        DKK ("Danish Krone", 6.7274936982),
        EUR ("Euro", 0.9002520706),
        GBP ("British pound", 0.7661595247),
        HKD ("Hong Kong Dollar", 7.7684551674),
        HRK ("Croatian Kuna", 6.6958948506),
        HUF ("Hungarian Forint", 302.1155923659),
        ILS ("Israeli New Shekel", 3.4544472452),
        INR ("Indian Rupee", 71.0809326611),
        ISK ("Iceland Krona", 123.6946344977),
        JPY ("Japanese Yen", 110.1098307526),
        KRW ("South Korean Won", 1159.8577601728),
        MXN ("Mexican Peso", 18.755671588),
        MYR ("Malaysian Ringgit", 4.0548253511),
        NOK ("Norwegian Kroner", 8.902592726),
        NZD ("New Zealand Dollar", 1.5108030248),
        RON ("Romanian Leu", 4.303474973),
        RUB ("Russian Ruble", 61.441753691),
        SEK ("Swedish Krona", 9.4931580843),
        SGD ("Singapore Dollar", 1.3467770976),
        THB ("Thai Baht", 30.3799063738),
        TRY ("Turkish Lira", 5.8807166006),
        USD ("United States Dollar", 1.0),
        VEB ("Venezuelan Bolivar", 0.145633),
        ZAR ("South African Rand", 14.4564277998);

        private double rateInUSD;
        private final String curName;
        ExRate(String curName, double rateInUSD) {
            this.rateInUSD = rateInUSD;
            this.curName = curName;
        }
        double rateInUSD()   { return rateInUSD; }
        void setRateInUSD(double value){
            this.rateInUSD = value;
        }
        String curName()   { return curName; }
    }

     public double GetConversionRate(String cur1, String cur2) {
        try {
            double rate1 = ExRate.valueOf(cur1).rateInUSD;
            double rate2 = ExRate.valueOf(cur2).rateInUSD;
            return rate1/rate2;
        }
        catch (IllegalArgumentException iae) {
            return -1;
            
        }
    }
    
    public List<String> GetCurrencyCodes() {
        List<String> codes = new ArrayList();
        for (ExRate exr : ExRate.values()) {
            codes.add(exr.name() + " - " + exr.curName);
        }
        return codes;
    }
    
    private void updateRates(){
        CurrencyRatesClient client = new CurrencyRatesClient();
        Rates rates = client.getRates();
        for(ExRate exr : ExRate.values()){
            for(Rate rate : rates.getRate()){
                if(exr.name().equals(rate.getCurrency())){
                    exr.setRateInUSD(rate.getValue());
                    System.out.println(exr.name() + " = " + rate.getValue());
                    break;
                }
            }
        }
    }
}
