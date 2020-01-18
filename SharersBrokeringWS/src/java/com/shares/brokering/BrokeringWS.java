/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.shares.brokering;

import java.util.List;
import javax.jws.WebService;
import javax.jws.WebMethod;
import javax.ejb.Stateless;
import Data.*;
import sharers.brokering.useraccounts.Account;

/**
 *
 * @author kristijanzrno
 */
@WebService(serviceName = "BrokeringWS")
@Stateless()
public class BrokeringWS {

    AccountUtils accountUtils;

    public BrokeringWS() {
        accountUtils = new AccountUtils();
    }

    @WebMethod(operationName = "getStock")
    public Stock getStock(String authUsername, String authPassword, String companySymbol, String currency) {
        if (!Auth.authenticate(authUsername, authPassword)) {
            return null;
        }
        return new StockUtils().getStock(companySymbol, currency);
    }

    @WebMethod(operationName = "getAllStocks")
    public List<Stock> getAllStocks(String authUsername, String authPassword, String currency) {
        if (!Auth.authenticate(authUsername, authPassword)) {
            return null;
        }
        return new StockUtils().getAllStocks(currency);
    }

    @WebMethod(operationName = "buyStock")
    public boolean buyStock(String authUsername, String authPassword, String companySymbol, int value) {
        if (!Auth.authenticate(authUsername, authPassword)) {
            return false;
        }
        return new StockUtils().buyStock(authUsername, companySymbol, value);
    }

    @WebMethod(operationName = "sellStock")
    public boolean sellStock(String authUsername, String authPassword, String companySymbol, int value) {
        if (!Auth.authenticate(authUsername, authPassword)) {
            return false;
        }
        return new StockUtils().sellStock(authUsername, companySymbol, value);
    }

    @WebMethod(operationName = "createAccount")
    public boolean createAccount(String authUsername, String authPassword, String accountUsername, String accountPassword, int accountLevel) {
        if (!Auth.authenticate(authUsername, authPassword)) {
            return false;
        }
        return accountUtils.createAccount(accountUsername, accountPassword, accountLevel);
    }

    @WebMethod(operationName = "deleteAccount")
    public boolean deleteAccount(String authUsername, String authPassword, String accountUsername) {
        if (!Auth.authenticateAdmin(authUsername, authPassword)) {
            return false;
        }
        return accountUtils.deleteAccount(accountUsername);
    }

    @WebMethod(operationName = "getAccounts")
    public List<Account> getAccounts(String authUsername, String authPassword) {
        if (!Auth.authenticateAdmin(authUsername, authPassword)) {
            return null;
        }
        return accountUtils.getAccounts();
    }

}
