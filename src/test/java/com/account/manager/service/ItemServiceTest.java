package com.account.manager.service;

import com.account.manager.model.Item;
import com.account.manager.model.mapping.ItemMapping;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class ItemServiceTest {

    @Autowired
    private ItemService itemService;

    @Test
    public void createNewItemTest(){
        ItemMapping itemMapping = new ItemMapping();
        itemMapping.setCategoryId(1);
        itemMapping.setAccountId(1);
        itemMapping.setCharging(new BigDecimal(0));
        itemMapping.setCrediting(new BigDecimal(10));
        Item testItem = itemService.createNewItem(itemMapping);
        BigDecimal testvalue = new BigDecimal(10);
        Assert.assertTrue(testvalue.compareTo(testItem.getCrediting()) == 0);
    }

    @Test
    public void itemFindAllByAccountIdTest(){
        ItemMapping itemMapping = new ItemMapping();
        itemMapping.setCategoryId(1);
        itemMapping.setAccountId(1);
        itemMapping.setCharging(new BigDecimal(0));
        itemMapping.setCrediting(new BigDecimal(5));
        Item testItem = itemService.createNewItem(itemMapping);
        List<Item> testItems = new ArrayList<>();
        testItems = itemService.itemFindAllByAccountId(1);
        Assert.assertNotNull(testItems.size());
    }

    @Test
    public void importItemsfromFileTest(){
        itemService.importItemsfromFile();
        List<Item> allItems = itemService.itemFindAllByAccountId(3);
        Assert.assertTrue(allItems.size() > 500);
    }

    @Test
    public void getActualMonthlyItemsTest_1(){
        int actualYear = 2024;
        int actualMonth = 2;
        List<Item> allItemsInActualMonth = itemService.getActualMonthlyItems(actualYear, actualMonth);
        Assert.assertTrue(allItemsInActualMonth.size() == 0);
    }

    @Test
    public void getActualMonthlyItemsTest_2(){
        int actualYear = 2024;
        int actualMonth = 2;
        String date = "2024-02-29";
        LocalDate localDate = LocalDate.parse(date);
        ItemMapping itemMapping = new ItemMapping();
        itemMapping.setActualDate(localDate);
        itemMapping.setCategoryId(1);
        itemMapping.setAccountId(1);
        itemMapping.setCharging(new BigDecimal(0));
        itemMapping.setCrediting(new BigDecimal(15));
        Item testItem = itemService.createNewItem(itemMapping);
        List<Item> allItemsInActualMonth = itemService.getActualMonthlyItems(actualYear, actualMonth);
        Assert.assertTrue(allItemsInActualMonth.size() == 1);
    }

    @Test
    public void createMonthlyBalanceMap_1(){
        Map<String, BigDecimal> testMonthlyBalanceMap = itemService.createMonthlyBalanceMap(2025, "MB");
        Assert.assertTrue(testMonthlyBalanceMap.size() == 12);
    }

    @Test
    public void createMonthlyBalanceMapTest_2(){
        String date = "2028-02-29";
        LocalDate localDate = LocalDate.parse(date);
        ItemMapping itemMapping1 = new ItemMapping();
        itemMapping1.setActualDate(localDate);
        itemMapping1.setCategoryId(1);
        itemMapping1.setAccountId(1);
        itemMapping1.setCharging(new BigDecimal(0));
        itemMapping1.setCrediting(new BigDecimal(15));
        Item testItem1 = itemService.createNewItem(itemMapping1);
        ItemMapping itemMapping2 = new ItemMapping();
        itemMapping2.setActualDate(localDate);
        itemMapping2.setCategoryId(1);
        itemMapping2.setAccountId(1);
        itemMapping2.setCharging(new BigDecimal(10));
        itemMapping2.setCrediting(new BigDecimal(0));
        Item testItem2 = itemService.createNewItem(itemMapping2);
        Map<String, BigDecimal> testMonthlyBalanceMap = itemService.createMonthlyBalanceMap(2028, "MB");
        BigDecimal getValue = testMonthlyBalanceMap.get("februaryMB");
        BigDecimal testValue = new BigDecimal(5);
        Assert.assertTrue(testValue.compareTo(getValue) == 0);
    }

}