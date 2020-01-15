/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package DOCwebServices;

import java.util.ArrayList;
import java.util.List;
import javax.jws.WebService;
import project.utils.XMLUtils;
import saved.rates.Rate;
import saved.rates.Rates;

/**
 *
 * @author taha-m
 */
@WebService()
public class CurrencyConversionWS {

    public enum ExRate {
        AED ("UAE Dirham", 0.168577),
        ARS ("Argentine Peso", 0.15464),
        AUD ("Australian Dollar", 0.615118),
        BGN ("Bulgarian Lev", 0.437263),
        BRL ("Brazilian Real", 0.36452),
        BWP ("Botswana Pula", 0.0945012),
        CAD ("Canadian Dollar", 0.612737),
        CHF ("Swiss Franc", 0.628501),
        CLP ("Chilean Peso", 0.00130255),
        CNY ("Chinese Yuan", 0.0941966),
        COP ("Colombian Peso", 0.000333297),
        DKK ("Danish Krone", 0.114709),
        EEK ("Estonian Kroon", 0.0546572),
        EGP ("Egypt Pounds", 0.107657),
        EUR ("Euro", 0.855201),
        GBP ("British pound", 1.0),
        HKD ("Hong Kong Dollar", 0.0806557),
        HRK ("Croatian Kuna", 0.115641),
        HUF ("Hungarian Forint", 0.00311833),
        ILS ("Israeli New Shekel", 0.17155),
        INR ("Indian Rupee", 0.0137968),
        ISK ("Iceland Krona", 0.00554904),
        JPY ("Japanese Yen", 0.00749584),
        KRW ("South Korean Won", 0.000552922),
        KZT ("Kazakhstani Tenge", 0.00424244),
        LKR ("Sri Lanka Rupee", 0.00559926),
        LTL ("Lithuanian Litas", 0.247684),
        LVL ("Latvian Lat", 1.2057),
        LYD ("Libyan Dinar", 0.32365),
        MXN ("Mexican Peso", 0.0508402),
        MYR ("Malaysian Ringgit", 0.200469),
        NOK ("Norwegian Kroner", 0.104293),
        NPR ("Nepalese Rupee", 0.0086265),
        NZD ("New Zealand Dollar", 0.485357),
        OMR ("Omani Rial", 1.62658),
        PKR ("Pakistan Rupee", 0.00732149),
        QAR ("Qatari Rial", 0.171819),
        RON ("Romanian Leu", 0.198977),
        RUB ("Russian Ruble", 0.0201399),
        SAR ("Saudi Riyal", 0.166779),
        SDG ("Sudanese Pound",0.260967),
        SEK ("Swedish Krona", 0.091032),
        SGD ("Singapore Dollar", 0.481964),
        THB ("Thai Baht", 0.0208891),
        TRY ("Turkish Lira", 0.432246),
        TTD ("Trinidad/Tobago Dollar", 0.098396),
        TWD ("Taiwan Dollar", 0.0206288),
        UAH ("Ukrainian hryvnia", 0.077864),
        USD ("United States Dollar", 0.625421),
        VEB ("Venezuelan Bolivar", 0.145633),
        ZAR ("South African Rand", 0.0893935);

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
    
    public boolean updateRates(){
        CurrencyRatesClient client = new CurrencyRatesClient();
        Rates rates = (Rates) XMLUtils.unmarshallObjectFromString(client.getRates(), "saved.rates");
        for(ExRate exr : ExRate.values()){
            for(Rate rate : rates.getRate()){
                if(exr.name().equals(rate.getCurrency())){
                    exr.setRateInUSD(rate.getValue());
                    System.out.println(exr.name() + " = " + rate.getValue());
                    break;
                }
            }
        }
        return true;
    }
}
