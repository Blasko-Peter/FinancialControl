package com.account.manager.controller;

import com.account.manager.model.Account;
import com.account.manager.model.Category;
import com.account.manager.model.Item;
import com.account.manager.service.AccountService;
import com.account.manager.service.CategoryService;
import com.account.manager.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class ViewController {

    private int actualYear = LocalDate.now().getYear();
    private int actualMonth = LocalDate.now().getMonth().getValue();
    private Map<String, BigDecimal> monthlyBalanceMap;

    private List<Item> itemsOfActualMonth;
    private List<Account> accounts;
    private Map<String, BigDecimal> cumulatedBalanceMap;
    private BigDecimal cumulatedValue;
    private List<String> types;
    private List<Category> categories;

    @Autowired
    private AccountService accountService;

    @Autowired
    private ItemService itemService;

    @Autowired
    private CategoryService categoryService;

    @GetMapping(value = "/")
    public String index(Model model) {

        return "index";
    }

}
