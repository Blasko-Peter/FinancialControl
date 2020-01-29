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
import java.util.ArrayList;
import java.util.List;

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
        List<Item> testItems = new ArrayList<>();
        testItems = itemService.itemFindAllByAccountId(1);
        Assert.assertEquals(3, testItems.size());
    }

}