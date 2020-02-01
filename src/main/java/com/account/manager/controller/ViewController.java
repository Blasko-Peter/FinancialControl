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
import org.springframework.web.bind.annotation.PathVariable;

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

    @GetMapping(value = "/add-new-account")
    public String addNewAccount(){
        return "newAccount";
    }

    @GetMapping(value = "/add-new-category")
    public String addNewCategory(Model model){
        model.addAttribute("types", types);
        return "newCategory";
    }

    @GetMapping(value = "/edit/{id}")
    public String editItem(@PathVariable("id") Long id, Model model){
        Item actualItem = itemService.getItemById(id);
        categories = new ArrayList<>();
        categories = categoryService.getAllCategories(true);
        accounts = new ArrayList<>();
        accounts = accountService.accountFindAll();
        types = new ArrayList<>();
        types = itemService.getTypeList();
        model.addAttribute("item", actualItem);
        model.addAttribute("categories", categories);
        model.addAttribute("accounts", accounts);
        model.addAttribute("types", types);
        return "edit";
    }

    @GetMapping(value = "/actual-date-setting")
    public String settingActualDate(Model model){
        List<String> months = new ArrayList<>();
        months = itemService.listOfMonth();
        List<Integer> years = new ArrayList<>();
        years = itemService.listOfYear();
        model.addAttribute("months", months);
        model.addAttribute("years", years);
        return "actualDate";
    }

    @GetMapping(value = "/setting/{settingYear}/{settingMonth}")
    public String settingYearAndMonth(@PathVariable("settingYear") Integer settingYear, @PathVariable("settingMonth") Integer settingMonth){
        actualYear = settingYear;
        actualMonth = settingMonth;
        return "redirect:/";
    }

    @GetMapping(value = "/export-data")
    public String exportAllItems(){
        itemService.exportData();
        return "redirect:/";
    }

    @GetMapping(value = "/add-new-item")
    public String addNewItem(Model model){
        model.addAttribute("accounts", accounts);
        model.addAttribute("categories", categories);
        model.addAttribute("types", types);
        return "newItem";
    }

}
