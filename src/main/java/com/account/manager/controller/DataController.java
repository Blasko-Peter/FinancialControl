package com.account.manager.controller;

import com.account.manager.model.mapping.AccountMapping;
import com.account.manager.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DataController {

    @Autowired
    private AccountService accountService;

    @PostMapping(value = "/add-new-account-data")
    public String addNewAccountData(@RequestBody AccountMapping accountMapping){
        accountService.addNewAccount(accountMapping);
        return "Success";
    }

}
