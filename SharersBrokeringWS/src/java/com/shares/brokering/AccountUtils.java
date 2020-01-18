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

    public AccountUtils() {
        //Initialise accounts file if not present
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
        //Check if account already exists
        if (getAccount(username) != null) {
            return false;
        }
        Account account = new Account();
        account.setAccountName(username);
        account.setAccountPassword(password);
        account.setAccountLevel(level);
        accounts.getAccounts().add(account);
        saveAccounts();
        return true;
    }

    public boolean deleteAccount(String username) {
        if (getAccount(username) == null) {
            return false;
        }

        for (Account account : accounts.getAccounts()) {
            if (account.getAccountName().equals(username)) {
                accounts.getAccounts().remove(account);
                break;
            }
        }
        saveAccounts();
        return false;
    }

    public boolean addShare(String username, String companySymbol, String companyName, int value) {
        if (getAccountStock(getAccount(username), companySymbol) != null) {
            getAccountStock(getAccount(username), companySymbol)
                    .setNoOfBoughtShares(getAccountStock(getAccount(username), companySymbol).getNoOfBoughtShares() + value);
            //todo change date
        } else {
            BoughtStock stock = new BoughtStock();
            stock.setCompanySymbol(companySymbol);
            stock.setCompanyName(companyName);
            stock.setNoOfBoughtShares(value);
            if (getAccount(username).getAccountBoughtStocks() == null) {
                getAccount(username).setAccountBoughtStocks(new BoughtStocks());
            }
            getAccount(username).getAccountBoughtStocks().getBoughtStock().add(stock);
        }
        saveAccounts();
        return true;
    }

    public boolean removeShare(String username, String companySymbol, int value) {
        if (getAccountStock(getAccount(username), companySymbol) != null && value > 0) {
            int possesedValue = getAccountStock(getAccount(username), companySymbol).getNoOfBoughtShares();
            // Trying to sell more than possesed
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

    public Account getAccount(String username) {
        for (Account account : accounts.getAccounts()) {
            if (account.getAccountName().equals(username)) {
                return account;
            }
        }
        return null;
    }

    public List<Account> getAccounts() {
        return accounts.getAccounts();
    }

    private List<BoughtStock> getAllAccountStocks(Account account) {
        if (account.getAccountBoughtStocks() == null) {
            return null;
        }
        return account.getAccountBoughtStocks().getBoughtStock();
    }

    private BoughtStock getAccountStock(Account account, String companySymbol) {
        if (getAllAccountStocks(account) == null) {
            return null;
        }
        for (BoughtStock boughtStock : getAllAccountStocks(account)) {
            if (boughtStock.getCompanySymbol().toUpperCase().equals(companySymbol.toUpperCase())) {
                return boughtStock;
            }
        }
        return null;
    }

    private void saveAccounts() {
        XMLUtils.marshallObject(accounts, new File("accounts.xml"));
    }
}
