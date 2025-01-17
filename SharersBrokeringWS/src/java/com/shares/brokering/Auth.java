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

    // Authentication function for regular user accounts
    // Checks the given username and password against the ones stored in the database
    public static boolean authenticate(String username, String password) {
        AccountUtils accountUtils = new AccountUtils();
        for (Account account : accountUtils.getAccounts()) {
            if (account.getAccountName().equals(username) && account.getAccountPassword().equals(password) && !account.isBlocked()) {
                return true;
            }
        }
        return false;
    }

    // Authentication function for administrator accounts
    // To count as an administrator, account level must be equal to value 3
    // Checks the given username and password against the ones stored in the database
    public static boolean authenticateAdmin(String username, String password) {
        AccountUtils accountUtils = new AccountUtils();
        for (Account account : accountUtils.getAccounts()) {
            if (account.getAccountName().equals(username) && account.getAccountPassword().equals(password) && account.getAccountLevel() > 2) {
                return true;
            }
        }
        return false;
    }

}
