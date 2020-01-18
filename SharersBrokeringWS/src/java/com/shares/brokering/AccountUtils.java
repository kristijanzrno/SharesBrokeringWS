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
    
    public AccountUtils(){
        //Initialise accounts file if not present
        File f = new File("accounts.xml");
        if (!f.exists()) {
            try {
                f.createNewFile();
                accounts = new AccountList();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }else{
            accounts = (AccountList) XMLUtils.unmarshallObject(f, "sharers.brokering.useraccounts");
        }
            
    }
    
    public boolean createAccount(String username, String password, int level){
        //Check if account already exists
        if(getAccount(username) != null)
            return false;
        Account account = new Account();
        account.setAccountName(username);
        account.setAccountPassword(password);
        account.setAccountLevel(level);
        accounts.getAccounts().add(account);
        saveAccounts();
        return true;
    }
    
    public boolean deleteAccount(String username){
        if(getAccount(username) == null)
            return false;
        for(Account account : accounts.getAccounts()){
            if(account.getAccountName().equals(username)){
                accounts.getAccounts().remove(account);
                break;
            }
        }
        saveAccounts();
        return false;
    }
    
    public Account getAccount(String username){
        for(Account account : accounts.getAccounts()){
            if(account.getAccountName().equals(username)){
                return account;
            }
        }
        return null;
    }
    
    public List<Account> getAccounts(){
        System.out.println("getting accounts");
        return accounts.getAccounts();
    }
    
    private void saveAccounts(){
        XMLUtils.marshallObject(accounts, new File("accounts.xml"));
    }
}
