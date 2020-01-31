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
    private List<Category> inActiveCategories;
    private Map<String, BigDecimal> monthlyBalanceMap;
    private Map<String, BigDecimal> cumulatedBalanceMap;
    private List<Account> accounts;
    private List<Category> categories;
    private List<Item> itemsOfActualMonth;
    private List<String> types;
    private List<List<String>> cashFlowdata;
    private BigDecimal cumulatedValue;

    @Autowired
    private AccountService accountService;

    @Autowired
    private ItemService itemService;

    @Autowired
    private CategoryService categoryService;

    @GetMapping(value = "/")
    public String index(Model model) {
        inActiveCategories = categoryService.getAllCategories(false);
        if(inActiveCategories.isEmpty()){
            categoryService.createInactiveCategories();
            inActiveCategories = categoryService.getAllCategories(false);
        }
        accountService.loadUpItemsOfAccounts();
        monthlyBalanceMap = new HashMap<>();
        monthlyBalanceMap = itemService.createMonthlyBalanceMap(actualYear, "MB");
        cumulatedBalanceMap = new HashMap<>();
        cumulatedBalanceMap = itemService.createCumulatedBalanceMap(monthlyBalanceMap);
        accounts = new ArrayList<>();
        accounts = accountService.accountFindAll();
        categories = new ArrayList<>();
        categories = categoryService.getAllCategories(true);
        itemsOfActualMonth = new ArrayList<>();
        itemsOfActualMonth = itemService.getActualMonthlyItems(actualYear, actualMonth);
        types = new ArrayList<>();
        types = itemService.getTypeList();
        cashFlowdata = new ArrayList<>();
        cashFlowdata = itemService.createCashFlowData(actualYear);
        cumulatedValue = new BigDecimal(0);
        cumulatedValue = cumulatedValue.add(cumulatedBalanceMap.get("decemberCB"));
        model.addAttribute("actualYear", actualYear);
        model.addAttribute("actualMonth", actualMonth);
        model.addAttribute("monthlyBalance", monthlyBalanceMap);
        model.addAttribute("cumulatedBalance", cumulatedBalanceMap);
        if(accounts.isEmpty()){
            model.addAttribute("accounts", "No data");
        } else {
            model.addAttribute("accounts", accounts);
        }
        if(categories.isEmpty()){
            model.addAttribute("categories", "No data");
        } else {
            model.addAttribute("categories", categories);
        }
        if(itemsOfActualMonth.isEmpty()){
            model.addAttribute("items", "No data");
        } else {
            model.addAttribute("items", itemsOfActualMonth);
        }
        model.addAttribute("types", types);
        model.addAttribute("cashFlowDataRows", cashFlowdata);
        model.addAttribute("cumulatedValue", cumulatedValue);
        return "index";
    }

    @GetMapping(value = "/import-peters-accounts")
    public String importAccounts(){
        accountService.createPetersAccounts();
        return "redirect:/";
    }

    @GetMapping(value = "/import-peters-categories")
    public String importCategories(){
        categoryService.createPetersCategories();
        return "redirect:/";
    }

    @GetMapping(value = "/import-items-from-file")
    public String importDataFromFile(){
        itemService.importItemsfromFile();
        return "redirect:/";
    }

}
