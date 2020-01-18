/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.shares.brokering;

import sharers.brokering.useraccounts.Account;

/**
 *
 * @author kristijanzrno
 */
public class Auth {
    
    public static boolean authenticate(String username, String password){
        AccountUtils accountUtils = new AccountUtils();
        for(Account account : accountUtils.getAccounts()){
            if(account.getAccountName().equals(username) && account.getAccountPassword().equals(password))
                return true;
        }
        return false;
    }
    
     public static boolean authenticateAdmin(String username, String password){
        AccountUtils accountUtils = new AccountUtils();
        for(Account account : accountUtils.getAccounts()){
            if(account.getAccountName().equals(username) && account.getAccountPassword().equals(password) && account.getAccountLevel() > 2)
                return true;
        }
        return false;
    }
    
}
