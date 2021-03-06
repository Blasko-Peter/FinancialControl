package com.account.manager.service;

import com.account.manager.model.Account;
import com.account.manager.model.Category;
import com.account.manager.model.mapping.AccountMapping;
import com.account.manager.model.mapping.ItemMapping;
import com.account.manager.repository.AccountRepository;
import com.account.manager.repository.CategoryRepository;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.math.BigDecimal;
import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class AccountServiceTest {

    @Autowired
    private AccountService accountService;

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private ItemService itemService;

    @Autowired
    private CategoryRepository categoryRepository;

    @Test
    public void addNewAccountTest(){
        AccountMapping accountMapping = new AccountMapping();
        accountMapping.setName("Test Account 1");
        accountService.addNewAccount(accountMapping);
        Assert.assertNotNull(accountRepository.findFirstByName("Test Account 1"));
    }

    @Test
    public void loadUpItemsOfAccountsTest(){
        AccountMapping accountMapping = new AccountMapping();
        accountMapping.setName("Test Account 2");
        accountService.addNewAccount(accountMapping);
        Account testAccount = accountRepository.findFirstByName("Test Account 2");
        Category newCategory = new Category();
        newCategory.setName("Test Category 1");
        newCategory.setType("Crediting");
        newCategory.setIsActive(true);
        categoryRepository.save(newCategory);
        ItemMapping newItemMapping1 = new ItemMapping();
        newItemMapping1.setAccountId(testAccount.getId());
        newItemMapping1.setCategoryId(1);
        newItemMapping1.setCharging(new BigDecimal(5));
        newItemMapping1.setCrediting(new BigDecimal(7));
        itemService.createNewItem(newItemMapping1);
        ItemMapping newItemMapping2 = new ItemMapping();
        newItemMapping2.setAccountId(testAccount.getId());
        newItemMapping2.setCategoryId(1);
        newItemMapping2.setCharging(new BigDecimal(5));
        newItemMapping2.setCrediting(new BigDecimal(0));
        itemService.createNewItem(newItemMapping2);
        accountService.loadUpItemsOfAccounts();
        BigDecimal testValue = new BigDecimal(-3);
        Assert.assertTrue(testValue.compareTo(accountRepository.findFirstByName("Test Account 2").getActualBalance()) == 0);
    }

    @Test
    public void getAccountByIdTest(){
        Account testAccount = accountService.getAccountById(1);
        Assert.assertEquals("Test Account 1", testAccount.getName());
    }

    @Test
    public void createPetersAccountsTest(){
        accountService.createPetersAccounts();
        Assert.assertNotNull(accountRepository.findFirstByName("Supershop Card"));
    }

    @Test
    public void accountFindAllTest(){
        AccountMapping accountMapping = new AccountMapping();
        accountMapping.setName("Test Account 2");
        accountService.addNewAccount(accountMapping);
        List<Account> allAccounts = accountService.accountFindAll();
        Assert.assertNotNull(allAccounts.size());
    }

}