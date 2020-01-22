package com.shares.brokering;

import java.io.File;
import java.io.IOException;
import java.util.List;
import project.utils.XMLUtils;
import sharers.brokering.useraccounts.*;

/**
 *
 * @author kristijanzrno
 */
public class AccountUtils {

    AccountList accounts;

    // Class created for clean and readable account management
    
    public AccountUtils() {
        // Initialise accounts file if not present
        // Usually executes on the first run on the server
        File f = new File("accounts.xml");
        if (!f.exists()) {
            try {
                f.createNewFile();
                accounts = new AccountList();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            accounts = (AccountList) XMLUtils.unmarshallObject(f, "sharers.brokering.useraccounts");
            if (accounts == null) {
                accounts = new AccountList();
            }

        }

    }

    public boolean createAccount(String username, String password, int level) {
        // Check if account already exists
        if (getAccount(username) != null)
            return false;
        // If not, create the new account and save it
        Account account = new Account();
        account.setAccountName(username);
        account.setAccountPassword(password);
        account.setAccountLevel(level);
        accounts.getAccounts().add(account);
        saveAccounts();
        return true;
    }

    public boolean deleteAccount(String username) {
        // Check if account exists
        if (getAccount(username) == null)
            return false;
        // If yes, find it, delete it and save the accounts
        for (Account account : accounts.getAccounts()) {
            if (account.getAccountName().equals(username)) {
                accounts.getAccounts().remove(account);
                break;
            }
        }
        saveAccounts();
        return true;
    }

    public boolean addShare(String username, String companySymbol, String companyName, int value) {
        // Check if account already posses shares of this company
        // If yes, add the new bought shares to the already possesed shares
        if (getAccountStock(getAccount(username), companySymbol) != null) {
            getAccountStock(getAccount(username), companySymbol)
                    .setNoOfBoughtShares(getAccountStock(getAccount(username), companySymbol).getNoOfBoughtShares() + value);
            getAccountStock(getAccount(username), companySymbol).setDateBought(XMLUtils.currentDate());
        // If not, create a new share and assign it to the account
        } else {
            BoughtStock stock = new BoughtStock();
            stock.setCompanySymbol(companySymbol);
            stock.setCompanyName(companyName);
            stock.setNoOfBoughtShares(value);
            stock.setDateBought(XMLUtils.currentDate());
            if (getAccount(username).getAccountBoughtStocks() == null) {
                getAccount(username).setAccountBoughtStocks(new BoughtStocks());
            }
            // Add the created stock to the account
            getAccount(username).getAccountBoughtStocks().getBoughtStock().add(stock);
        }
        saveAccounts();
        return true;
    }

    public boolean removeShare(String username, String companySymbol, int value) {
        // Check if the account posseses the share and that the sell value is greater than 0
        if (getAccountStock(getAccount(username), companySymbol) != null && value > 0) {
            int possesedValue = getAccountStock(getAccount(username), companySymbol).getNoOfBoughtShares();
            // Check if user is trying to sell more than possesed
            if (value > possesedValue) {
                return false;
            }
            // Selling within normal parameters
            if (possesedValue == value) {
                // Sell all
                getAllAccountStocks(getAccount(username)).remove(getAccountStock(getAccount(username), companySymbol));
            } else {
                // Selling specific amount
                getAccountStock(getAccount(username), companySymbol).setNoOfBoughtShares(possesedValue - value);
            }
            saveAccounts();
            return true;
        }

        return false;
    }

    // Get the account object with the username
    public Account getAccount(String username) {
        for (Account account : accounts.getAccounts()) {
            if (account.getAccountName().equals(username)) {
                return account;
            }
        }
        return null;
    }

    // Returns a list of all accounts
    public List<Account> getAccounts() {
        return accounts.getAccounts();
    }

    // Returns a list of all account shares
    public List<BoughtStock> getAllAccountStocks(Account account) {
        if (account.getAccountBoughtStocks() == null) {
            return null;
        }
        return account.getAccountBoughtStocks().getBoughtStock();
    }
    
    // Returns a list of all account shares, for a account name
     public List<BoughtStock> getAllUsernameStocks(String account) {
        if (getAccount(account).getAccountBoughtStocks() == null) {
            return null;
        }
        return getAccount(account).getAccountBoughtStocks().getBoughtStock();
    }
     
    // Changing the account access level (block/unblock)
    public boolean changeAccountAccess(String account, boolean blocked){
        getAccount(account).setBlocked(blocked);
        saveAccounts();
        return true;
    }
    // Changing the password
    public boolean changeAccountPassword(String account, String password){
        getAccount(account).setAccountPassword(password);
        saveAccounts();
        return true;
    }
    
    // Getting the info about specific account share
    private BoughtStock getAccountStock(Account account, String companySymbol) {
        if (getAllAccountStocks(account) == null) {
            return null;
        }
        for (BoughtStock boughtStock : getAllAccountStocks(account)) {
            if (boughtStock.getCompanySymbol().toUpperCase().equals(companySymbol.toUpperCase())) {
                return boughtStock;
            }
        }
        // If the share has not been found, return null
        return null;
    }

    // Saving the accounts using jaxb binding to "accounts.xml" file
    private void saveAccounts() {
        XMLUtils.marshallObject(accounts, new File("accounts.xml"));
    }
}
