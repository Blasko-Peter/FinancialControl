package com.account.manager.controller;

import com.account.manager.model.mapping.AccountMapping;
import com.account.manager.model.mapping.CategoryMapping;
import com.account.manager.service.AccountService;
import com.account.manager.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DataController {

    @Autowired
    private AccountService accountService;

    @Autowired
    private CategoryService categoryService;

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

}
