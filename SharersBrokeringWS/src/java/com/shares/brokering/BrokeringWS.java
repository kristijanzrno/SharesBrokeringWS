/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.shares.brokering;

import Data.*;
import docwebservices.currency.convertor.CurrencyConversionWSService;
import java.util.List;
import javax.ejb.Stateless;
import javax.jws.WebMethod;
import javax.jws.WebService;
import javax.xml.ws.WebServiceRef;
import sharers.brokering.useraccounts.Account;
import sharers.brokering.useraccounts.*;

/**
 *
 * @author kristijanzrno
 */
@WebService(serviceName = "BrokeringWS")
@Stateless()
public class BrokeringWS {

    @WebServiceRef(wsdlLocation = "WEB-INF/wsdl/localhost_8080/CurrencyConvertor/CurrencyConversionWSService.wsdl")
    private CurrencyConversionWSService service;

    public BrokeringWS() {}

    @WebMethod(operationName = "getStock")
    public Stock getStock(String authUsername, String authPassword, String companySymbol, String currency) {
        if (!Auth.authenticate(authUsername, authPassword)) {
            return null;
        }
        return new StockUtils(service).getStock(companySymbol, currency);
    }

    @WebMethod(operationName = "getAllStocks")
    public List<Stock> getAllStocks(String authUsername, String authPassword, String currency) {
        if (!Auth.authenticate(authUsername, authPassword)) {
            return null;
        }
        return new StockUtils(service).getAllStocks(currency);
    }

    @WebMethod(operationName = "buyStock")
    public boolean buyStock(String authUsername, String authPassword, String companySymbol, int value) {
        if (!Auth.authenticate(authUsername, authPassword)) {
            return false;
        }
        return new StockUtils(service).buyStock(authUsername, companySymbol, value);
    }

    @WebMethod(operationName = "sellStock")
    public boolean sellStock(String authUsername, String authPassword, String companySymbol, int value) {
        if (!Auth.authenticate(authUsername, authPassword)) {
            return false;
        }
        return new StockUtils(service).sellStock(authUsername, companySymbol, value);
    }

    @WebMethod(operationName = "createAccount")
    public boolean createAccount(String authUsername, String authPassword, String accountUsername, String accountPassword, int accountLevel) {
        if (!Auth.authenticate(authUsername, authPassword)) {
            return false;
        }
        return new AccountUtils().createAccount(accountUsername, accountPassword, accountLevel);
    }

    @WebMethod(operationName = "deleteAccount")
    public boolean deleteAccount(String authUsername, String authPassword, String accountUsername) {
        if (!Auth.authenticateAdmin(authUsername, authPassword)) {
            return false;
        }
        return new AccountUtils().deleteAccount(accountUsername);
    }

    @WebMethod(operationName = "getAccounts")
    public List<Account> getAccounts(String authUsername, String authPassword) {
        if (!Auth.authenticateAdmin(authUsername, authPassword)) {
            return null;
        }
        return new AccountUtils().getAccounts();
    }
    
    @WebMethod(operationName = "getAllAccountShares")
    public List<BoughtStock> getAllAccountShares(String authUsername, String authPassword) {
        if (!Auth.authenticate(authUsername, authPassword)) {
            return null;
        }
        return new AccountUtils().getAllUsernameStocks(authUsername);
    }
    
    @WebMethod(operationName = "changeStockAccess")
    public boolean changeStockAccess(String authUsername, String authPassword, String companySymbol, boolean blocked){
        if (!Auth.authenticateAdmin(authUsername, authPassword)) {
            return false;
        }
        return new StockUtils(service).changeStockAccess(companySymbol, blocked);
    }
    
    @WebMethod(operationName = "changeAccountAccess")
    public boolean changeAccountAccess(String authUsername, String authPassword, String accountName, boolean blocked){
        if (!Auth.authenticateAdmin(authUsername, authPassword)) {
            return false;
        }
        return new AccountUtils().changeAccountAccess(accountName, blocked);
    }
    
    @WebMethod(operationName = "login")
    public boolean login(String authUsername, String authPassword){
        System.out.println(authUsername + " - " + authPassword);
        return (Auth.authenticate(authUsername, authPassword));
    }
    
    @WebMethod(operationName = "getCurrencyList")
    public List<String> getCurrencyList(String authUsername, String authPassword){
        if (!Auth.authenticate(authUsername, authPassword)) {
            return null;
        }
        return new StockUtils(service).getCurrencyList();
    }
 
}
