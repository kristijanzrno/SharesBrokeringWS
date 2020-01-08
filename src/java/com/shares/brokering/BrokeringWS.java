/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.shares.brokering;

import java.io.File;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.jws.WebService;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.ejb.Stateless;

/**
 *
 * @author kristijanzrno
 */
@WebService(serviceName = "BrokeringWS")
@Stateless()
public class BrokeringWS {

    /**
     * This is a sample web service operation
     */
    @WebMethod(operationName = "hello")
    public String hello(@WebParam(name = "name") String txt) {
        return "Hello " + txt + " !";
    }
    
    @WebMethod(operationName = "test")
    public String test(){
        List<Data.Stock> stocks = XMLUtils.unmarshallList(new File("testing.xml"));
        String data = "*****************************************************";
        for(Data.Stock stock : stocks){
            data += ("\n Company Name: " + stock.getCompanyName());
            data += ("\n Symbol: " + stock.getCompanySymbol());
            data += ("\n Available Shares: " + stock.getNoOfAvailableShares());
            data += ("\n Currency: " + stock.getPrice().getCurrency());
            data += ("\n Value: " + stock.getPrice().getValue());
            data += ("*****************************************************");
        }
        return data;
    }
    
}
