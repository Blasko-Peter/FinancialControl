package com.account.manager.controller;

import com.account.manager.model.Item;
import com.account.manager.model.mapping.AccountMapping;
import com.account.manager.model.mapping.CategoryMapping;
import com.account.manager.model.mapping.IdMapping;
import com.account.manager.model.mapping.ItemMapping;
import com.account.manager.service.AccountService;
import com.account.manager.service.CategoryService;
import com.account.manager.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DataController {

    @Autowired
    private AccountService accountService;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private ItemService itemService;

    @PostMapping(value = "/add-new-account-data")
    public String addNewAccountData(@RequestBody AccountMapping accountMapping){
        accountService.addNewAccount(accountMapping);
        return "Success";
    }

    @PostMapping(value = "/add-new-category-data")
    public String addNewCategoryData(@RequestBody CategoryMapping categoryMapping){
        categoryService.addNewCategory(categoryMapping);
        return "Success";
    }

    @PostMapping(value = "/add-new-item-data")
    public String createNewItem(@RequestBody ItemMapping itemMapping){
        Item newItem = itemService.createNewItem(itemMapping);
        return "Success";
    }

    @PostMapping(value = "/delete-item")
    public String deleteItem(@RequestBody IdMapping idMapping){
        itemService.deleteItemById(idMapping.getId());
        return "Success";
    }

    @PostMapping(value = "/edit-item/{id}")
    public String editItem(@RequestBody ItemMapping itemMapping, @PathVariable("id") Long id){
        itemService.updateItem(itemMapping, id);
        return "Success";
    }

    @PostMapping(value = "/edit-account/{id}")
    public String editAccount(@RequestBody AccountMapping accountMapping, @PathVariable("id") Long id){
        accountService.updateAccount(accountMapping, id);
        return "Success";
    }

}
