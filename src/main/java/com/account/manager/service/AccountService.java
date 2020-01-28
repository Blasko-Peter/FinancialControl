package com.account.manager.service;

import com.account.manager.model.Account;
import com.account.manager.model.Item;
import com.account.manager.model.mapping.AccountMapping;
import com.account.manager.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service
public class AccountService {

    @Autowired
    private AccountRepository accountRepository;

    public Account getAccountById(long id){
        return accountFindById(id);
    }

    public void addNewItemToAccount(Item item){
        Account account = item.getAccount();
        addItemToAccountItems(account, item);
        addValueOfItemToAccountBalance(account, item);
        accountSave(account);
    }

    public void addNewAccount(AccountMapping accountMapping){
        List<Item> items = new ArrayList<>();
        BigDecimal newBigdecimal = new BigDecimal(0.0);
        Account newAccount = new Account();
        newAccount.setName(accountMapping.getName());
        newAccount.setActive(true);
        newAccount.setItems(items);
        newAccount.setActualBalance(newBigdecimal);
    }

    private void addItemToAccountItems(Account account, Item item){
        account.getItems().add(item);
    }

    private void addValueOfItemToAccountBalance(Account account, Item item){
        account.setActualBalance(account.getActualBalance().subtract(item.getCharging()));
        account.setActualBalance(account.getActualBalance().add(item.getCrediting()));
    }

    private Account accountFindById(long id){
        return accountRepository.findById(id);
    }

    private void accountSave(Account account){
        accountRepository.save(account);
    }

}
