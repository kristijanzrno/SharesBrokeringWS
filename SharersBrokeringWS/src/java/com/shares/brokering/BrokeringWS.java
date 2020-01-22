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

    // CurrencyConvertor service reference
    @WebServiceRef(wsdlLocation = "WEB-INF/wsdl/localhost_8080/CurrencyConvertor/CurrencyConversionWSService.wsdl")
    private CurrencyConversionWSService service;

    public BrokeringWS() {}

    // Web method to return stock info based on a stock symbol
    @WebMethod(operationName = "getStock")
    public Stock getStock(String authUsername, String authPassword, String companySymbol, String currency) {
        if (!Auth.authenticate(authUsername, authPassword)) {
            return null;
        }
        return new StockUtils(service).getStock(companySymbol, currency);
    }

    // Web method to return all the contained stocks on the service
    @WebMethod(operationName = "getAllStocks")
    public List<Stock> getAllStocks(String authUsername, String authPassword, String currency) {
        if (!Auth.authenticate(authUsername, authPassword)) {
            return null;
        }
        return new StockUtils(service).getAllStocks(currency);
    }

    // Web method to buy a stock share based on a company symbol and an amount value
    @WebMethod(operationName = "buyStock")
    public boolean buyStock(String authUsername, String authPassword, String companySymbol, int value) {
        if (!Auth.authenticate(authUsername, authPassword)) {
            return false;
        }
        return new StockUtils(service).buyStock(authUsername, companySymbol, value);
    }
    
    // Web method to buy a stock share based on a company symbol and an amount value
    @WebMethod(operationName = "sellStock")
    public boolean sellStock(String authUsername, String authPassword, String companySymbol, int value) {
        if (!Auth.authenticate(authUsername, authPassword)) {
            return false;
        }
        return new StockUtils(service).sellStock(authUsername, companySymbol, value);
    }

    // Web method to create an user account
    @WebMethod(operationName = "createAccount")
    public boolean createAccount(String authUsername, String authPassword, String accountUsername, String accountPassword, int accountLevel) {
        if (!Auth.authenticate(authUsername, authPassword)) {
            return false;
        }
        return new AccountUtils().createAccount(accountUsername, accountPassword, accountLevel);
    }
    
    // Web method to delete an user account
    // Available only to administrators
    @WebMethod(operationName = "deleteAccount")
    public boolean deleteAccount(String authUsername, String authPassword, String accountUsername) {
        if (!Auth.authenticateAdmin(authUsername, authPassword)) {
            return false;
        }
        return new AccountUtils().deleteAccount(accountUsername);
    }

    // Web method to list all user accounts
    // Available only to administrators
    @WebMethod(operationName = "getAccounts")
    public List<Account> getAccounts(String authUsername, String authPassword) {
        if (!Auth.authenticateAdmin(authUsername, authPassword)) {
            return null;
        }
        return new AccountUtils().getAccounts();
    }

    // Web method to return all the shares possesed by a specific account
    @WebMethod(operationName = "getAllAccountShares")
    public List<BoughtStock> getAllAccountShares(String authUsername, String authPassword) {
        if (!Auth.authenticate(authUsername, authPassword)) {
            return null;
        }
        return new AccountUtils().getAllUsernameStocks(authUsername);
    }

    // Web method to change the stock access (block/unblock stock trading)
    @WebMethod(operationName = "changeStockAccess")
    public boolean changeStockAccess(String authUsername, String authPassword, String companySymbol, boolean blocked) {
        if (!Auth.authenticateAdmin(authUsername, authPassword)) {
            return false;
        }
        return new StockUtils(service).changeStockAccess(companySymbol, blocked);
    }

    // Web method to change user account access (ban/unban)
    @WebMethod(operationName = "changeAccountAccess")
    public boolean changeAccountAccess(String authUsername, String authPassword, String accountName, boolean blocked) {
        if (!Auth.authenticateAdmin(authUsername, authPassword)) {
            return false;
        }
        return new AccountUtils().changeAccountAccess(accountName, blocked);
    }

    // Web method for a initial authentication
    @WebMethod(operationName = "login")
    public boolean login(String authUsername, String authPassword) {
        return (Auth.authenticate(authUsername, authPassword));
    }
    
    // Web method to check if an user is administrator
    @WebMethod(operationName = "isAdmin")
    public boolean isAdmin(String authUsername, String authPassword){
        return (Auth.authenticateAdmin(authUsername, authPassword));
    }

    // Web method to return all the supported currencies
    @WebMethod(operationName = "getCurrencyList")
    public List<String> getCurrencyList(String authUsername, String authPassword) {
        if (!Auth.authenticate(authUsername, authPassword)) {
            return null;
        }
        return new StockUtils(service).getCurrencyList();
    }

    // Web method to change an account password
    @WebMethod(operationName = "changeAccountPassword")
    public boolean changeAccountPassword(String authUsername, String authPassword, String newPassword) {
        if (!Auth.authenticate(authUsername, authPassword)) {
            return false;
        }
        return new AccountUtils().changeAccountPassword(authUsername, newPassword);
    }

    // Web method that enables the search functionality
    @WebMethod(operationName = "searchStocks")
    public List<Stock> searchStocks(String authUsername, String authPassword, String searchFor, String orderBy, String currency) {
        if (!Auth.authenticate(authUsername, authPassword)) {
            return null;
        }
        return new StockUtils(service).searchStocks(searchFor, orderBy, currency);
    }

}
