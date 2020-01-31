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

    @Test
    public void createCumulatedBalanceMapTest_1(){
        List<BigDecimal> testValues = new ArrayList<>();
        BigDecimal jan = new BigDecimal(1);
        testValues.add(jan);
        BigDecimal feb = new BigDecimal(2);
        testValues.add(feb);
        BigDecimal mar = new BigDecimal(3);
        testValues.add(mar);
        BigDecimal apr = new BigDecimal(4);
        testValues.add(apr);
        BigDecimal may = new BigDecimal(5);
        testValues.add(may);
        BigDecimal jun = new BigDecimal(6);
        testValues.add(jun);
        BigDecimal jul = new BigDecimal(7);
        testValues.add(jul);
        BigDecimal aug = new BigDecimal(8);
        testValues.add(aug);
        BigDecimal sep = new BigDecimal(9);
        testValues.add(sep);
        BigDecimal oct = new BigDecimal(10);
        testValues.add(oct);
        BigDecimal nov = new BigDecimal(11);
        testValues.add(nov);
        BigDecimal dec = new BigDecimal(12);
        testValues.add(dec);
        Map<String, BigDecimal> testMonthlyBalanceMap = itemService.createCumulatedBalanceMap(testValues, "CB");
        Assert.assertTrue(testMonthlyBalanceMap.size() == 12);
    }

    @Test
    public void createCumulatedBalanceMapTest_2(){
        List<BigDecimal> testValues = new ArrayList<>();
        BigDecimal jan = new BigDecimal(1);
        testValues.add(jan);
        BigDecimal feb = new BigDecimal(2);
        testValues.add(feb);
        BigDecimal mar = new BigDecimal(3);
        testValues.add(mar);
        BigDecimal apr = new BigDecimal(4);
        testValues.add(apr);
        BigDecimal may = new BigDecimal(5);
        testValues.add(may);
        BigDecimal jun = new BigDecimal(6);
        testValues.add(jun);
        BigDecimal jul = new BigDecimal(7);
        testValues.add(jul);
        BigDecimal aug = new BigDecimal(8);
        testValues.add(aug);
        BigDecimal sep = new BigDecimal(9);
        testValues.add(sep);
        BigDecimal oct = new BigDecimal(10);
        testValues.add(oct);
        BigDecimal nov = new BigDecimal(11);
        testValues.add(nov);
        BigDecimal dec = new BigDecimal(12);
        testValues.add(dec);
        Map<String, BigDecimal> testMonthlyBalanceMap = itemService.createCumulatedBalanceMap(testValues, "CB");
        BigDecimal testValue = new BigDecimal(78);
        Assert.assertEquals(testValue, testMonthlyBalanceMap.get("decemberCB"));
    }

    @Test
    public void createCashFlowDataTest_1(){
        List<List<String>> testCashFlowData = itemService.createCashFlowData(2019);
        Assert.assertEquals(13, testCashFlowData.get(0).size());
    }

    @Test
    public void createCashFlowDataTest_2(){
        List<List<String>> testCashFlowData = itemService.createCashFlowData(2019);
        Assert.assertEquals("OPENING BALANCE", testCashFlowData.get(0).get(0));
    }

    @Test
    public void createCashFlowDataTest_3(){
        List<List<String>> testCashFlowData = itemService.createCashFlowData(2019);
        Assert.assertEquals("CLOSING BALANCE", testCashFlowData.get(testCashFlowData.size() - 1).get(0));
    }

    @Test
    public void getTypeListTest_1(){
        List<String> types = itemService.getTypeList();
        Assert.assertEquals(2, types.size());
    }

    @Test
    public void getTypeListTest_2(){
        List<String> types = itemService.getTypeList();
        Assert.assertEquals("Charging", types.get(0));
    }

    @Test
    public void getTypeListTest_3(){
        List<String> types = itemService.getTypeList();
        Assert.assertEquals("Crediting", types.get(1));
    }

    @Test
    public void listOfMonthTest_1(){
        List<String> months = itemService.listOfMonth();
        Assert.assertEquals(12, months.size());
    }

    @Test
    public void listOfMonthTest_2(){
        List<String> months = itemService.listOfMonth();
        Assert.assertEquals("january", months.get(0));
    }

    @Test
    public void listOfMonthTest_3(){
        List<String> months = itemService.listOfMonth();
        Assert.assertEquals("december", months.get(11));
    }

    @Test
    public void listOfMonthTest_4(){
        List<String> months = itemService.listOfMonth();
        Assert.assertEquals("march", months.get(2));
    }

}