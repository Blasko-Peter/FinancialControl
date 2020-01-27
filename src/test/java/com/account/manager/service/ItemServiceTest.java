package com.account.manager.service;

import com.account.manager.model.Item;
import com.account.manager.model.mapping.ItemMapping;
import com.account.manager.repository.ItemRepository;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ItemServiceTest {

    @Autowired
    private ItemService itemService;

    @Autowired
    private ItemRepository itemRepository;

    @Test
    public void createNewItemTest(){
        BigDecimal charging = new BigDecimal(10.5);
        ItemMapping newItemMapping = new ItemMapping();
        newItemMapping.setAccountId(1);
        newItemMapping.setCategoryId(1);
        newItemMapping.setCharging(charging);
        itemService.createNewItem(newItemMapping);
        Item newItem = itemRepository.findById(2);
        Assert.assertTrue(charging.compareTo(newItem.getCharging()) == 0);
    }

}