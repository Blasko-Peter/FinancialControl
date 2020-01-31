package com.account.manager.service;

import com.account.manager.model.Account;
import com.account.manager.model.Item;
import com.account.manager.model.mapping.AccountMapping;
import com.account.manager.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.*;

@Service
public class AccountService {

    private List<String> petersNameOfAccounts = new ArrayList<>(Arrays.asList("Cost Ticket", "Home Cash", "Raiffeisen Bank Card", "Supershop Card"));
    private List<Item> items;
    private List<Account> accounts;

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private ItemService itemService;

    public Account getAccountById(long id){
        return accountFindById(id);
    }

    public void addNewAccount(AccountMapping accountMapping){
        items = new ArrayList<>();
        BigDecimal newBigdecimal = new BigDecimal(0.0);
        Account newAccount = new Account();
        newAccount.setName(accountMapping.getName());
        newAccount.setActive(true);
        newAccount.setItems(items);
        newAccount.setActualBalance(newBigdecimal);
        accountSave(newAccount);
    }

    public void createPetersAccounts(){
        for(String accountName : petersNameOfAccounts){
            AccountMapping accountMapping = new AccountMapping();
            accountMapping.setName(accountName);
            addNewAccount(accountMapping);
        }
    }

    public void loadUpItemsOfAccounts() {
        List<Account> accounts = accountFindAll();
        for(Account account : accounts){
            BigDecimal bd = new BigDecimal(0);
            account.setActualBalance(bd);
            List<Item> items = itemService.itemFindAllByAccountId(account.getId());
            for( Item item : items){
                account.setActualBalance(account.getActualBalance().subtract(item.getCharging()));
                account.setActualBalance(account.getActualBalance().add(item.getCrediting()));
            }
            accountSave(account);
        }
    }

    public Account accountFindById(long id){
        return accountRepository.findById(id);
    }

    private void accountSave(Account account){
        accountRepository.save(account);
    }

    public List<Account> accountFindAll(){
        return accountRepository.findAll();
    }

}
