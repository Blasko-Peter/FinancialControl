package com.account.manager.service;

import com.account.manager.model.Account;
import com.account.manager.model.Category;
import com.account.manager.model.Item;
import com.account.manager.model.mapping.AccountMapping;
import com.account.manager.repository.AccountRepository;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class AccountServiceTest {

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private AccountService accountService;

    @Test
    public void accountSaveTest(){
        List<Item> items = new ArrayList<>();
        BigDecimal bigdecimal = new BigDecimal(0.0);
        Account account = new Account();
        account.setName("Test Account");
        account.setActive(true);
        account.setItems(items);
        account.setActualBalance(bigdecimal);
        accountRepository.save(account);
        Assert.assertNotNull(accountRepository.findById(1));
    }

    @Test
    public void getAccountByIdTest(){
        Assert.assertNotNull(accountService.getAccountById(1));
    }

    @Test
    public void addNewItemToAccountTest(){
        List<Item> items = new ArrayList<>();
        BigDecimal bigdecimal = new BigDecimal(0.0);
        BigDecimal value1 = new BigDecimal(10.1);
        BigDecimal value2 = new BigDecimal(11.1);
        BigDecimal testValue = new BigDecimal(1.0);
        Account account = new Account();
        account.setName("Test Account One");
        account.setActive(true);
        account.setItems(items);
        account.setActualBalance(bigdecimal);
        Category newCategory = new Category();
        newCategory.setId(1);
        Item newItem = new Item();
        newItem.setCharging(value1);
        newItem.setCrediting(value2);
        newItem.setAccount(account);
        newItem.setCategory(newCategory);
        accountService.addNewItemToAccount(newItem);
        Account testAccount = accountRepository.findById(1);
        Assert.assertTrue(testValue.compareTo(testAccount.getActualBalance()) == 0);
    }

    @Test
    public void addNewAccountTest(){
        AccountMapping accountMapping = new AccountMapping();
        accountMapping.setName("Test Account");
        accountService.addNewAccount(accountMapping);
        Account testAccount = accountRepository.findById(2);
        Assert.assertEquals("Test Account", testAccount.getName());
    }

}