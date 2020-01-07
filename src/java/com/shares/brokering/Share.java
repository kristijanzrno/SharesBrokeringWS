/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.shares.brokering;

import javax.xml.bind.annotation.XmlAttribute;  
import javax.xml.bind.annotation.XmlElement;  
import javax.xml.bind.annotation.XmlRootElement; 

/**
 *
 * @author kristijanzrno
 */

public class Share {
    private String companyName;
    private String companySymbol;
    private int numberOfAvailableShares;
    private SharePrice price;

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getCompanySymbol() {
        return companySymbol;
    }

    public void setCompanySymbol(String companySymbol) {
        this.companySymbol = companySymbol;
    }

    public int getNumberOfAvailableShares() {
        return numberOfAvailableShares;
    }

    public void setNumberOfAvailableShares(int numberOfAvailableShares) {
        this.numberOfAvailableShares = numberOfAvailableShares;
    }

    public SharePrice getPrice() {
        return price;
    }

    public void setPrice(SharePrice price) {
        this.price = price;
    }
    
}
