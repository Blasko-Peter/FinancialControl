package com.account.manager.service;

import com.account.manager.model.Category;
import com.account.manager.model.Item;
import com.account.manager.model.Months;
import com.account.manager.model.Types;
import com.account.manager.model.mapping.ItemMapping;
import com.account.manager.repository.ItemRepository;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileInputStream;
import java.math.BigDecimal;
import java.time.DateTimeException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ItemService {

    private static String importURL = "/Users/blaskopeter/Downloads/Export Data.xlsx";
    private static int startYear = 2016;
    private static int endYear = 2035;

    @Autowired
    private ItemRepository itemRepository;

    @Autowired
    private AccountService accountService;

    @Autowired
    private CategoryService categoryService;

    public Item createNewItem(ItemMapping itemMapping){
        Item newItem = createNewItemFromItemMapping(itemMapping);
        saveNewItem(newItem);
        return newItem;
    }

    public void importItemsfromFile(){
        try{
            File excelFile = new File(importURL);
            FileInputStream fis = new FileInputStream(excelFile);
            XSSFWorkbook workbook = new XSSFWorkbook(fis);
            XSSFSheet sheet = workbook.getSheetAt(0);
            Row row;
            for(int i = 1; i < sheet.getLastRowNum() + 1; i++){
                row = sheet.getRow(i);
                LocalDate actualDate = LocalDate.parse(row.getCell(0).getStringCellValue());
                long accountId = (long) row.getCell(1).getNumericCellValue();
                String place = row.getCell(2).getStringCellValue();
                String city = row.getCell(3).getStringCellValue();
                long categoryId = (long) row.getCell(4).getNumericCellValue();
                BigDecimal charging = new BigDecimal(row.getCell(5).getStringCellValue());
                BigDecimal crediting = new BigDecimal(row.getCell(6).getStringCellValue());
                String comment = row.getCell(7).getStringCellValue();
                ItemMapping newItemMapping = createItemMappingfromSimpleData(actualDate, accountId, place, city, categoryId, charging, crediting, comment);
                Item newItem = createNewItemFromItemMapping(newItemMapping);
                saveNewItem(newItem);
            }
            workbook.close();
            fis.close();
        } catch (Exception ex) {
            System.out.println(ex);
        }
    }

    public List<Item> itemFindAllByAccountId(long id) {
        return itemRepository.findAllByAccountId(id);
    }

    private ItemMapping createItemMappingfromSimpleData(LocalDate actualDate, long accountId, String place, String city, long categoryId, BigDecimal charging, BigDecimal crediting, String comment){
        ItemMapping newItemMapping = new ItemMapping();
        newItemMapping.setActualDate(actualDate);
        newItemMapping.setAccountId(accountId);
        newItemMapping.setPlace(place);
        newItemMapping.setCity(city);
        newItemMapping.setCategoryId(categoryId);
        newItemMapping.setCharging(charging);
        newItemMapping.setCrediting(crediting);
        newItemMapping.setComment(comment);
        return newItemMapping;
    }

    private Item createNewItemFromItemMapping(ItemMapping itemMapping){
        Item newItem = new Item();
        newItem.setActualDate(itemMapping.getActualDate());
        newItem.setAccount(accountService.getAccountById(itemMapping.getAccountId()));
        newItem.setPlace(itemMapping.getPlace());
        newItem.setCity(itemMapping.getCity());
        newItem.setCategory(categoryService.getCategoryById(itemMapping.getCategoryId()));
        newItem.setCharging(itemMapping.getCharging());
        newItem.setCrediting(itemMapping.getCrediting());
        newItem.setComment(itemMapping.getComment());
        return newItem;
    }

    private void saveNewItem(Item newItem){
        itemRepository.save(newItem);
    }

    public List<Item> getActualMonthlyItems(int actualYear, int actualMonth){
        List<LocalDate> allDaysInActualMonth = new ArrayList<>();
        allDaysInActualMonth = getAllDaysInActualMonth(actualYear, actualMonth);
        List<Item> items = new ArrayList<>();
        items = getAllItemsInActualMonth(allDaysInActualMonth);
        return items;
    }

    private List<LocalDate> getAllDaysInActualMonth(int actualYear, int actualMonth){
        List<LocalDate> dates = new ArrayList<>();
        String actualDate = actualYear + "-";
        if(actualMonth < 10){
            actualDate += "0" + actualMonth;
        } else {
            actualDate += actualMonth;
        }
        for(int i = 1; i < 32; i++){
            String newDate = actualDate + "-";
            if(i < 10){
                newDate += "0" + i;
            } else {
                newDate += i;
            }
            try {
                LocalDate ld = LocalDate.parse(newDate);
                dates.add(ld);
            } catch (DateTimeException ex) {
                continue;
            }
        }
        return dates;
    }

    private List<Item> getAllItemsInActualMonth(List<LocalDate> allDaysInActualMonth){
        return itemRepository.findAllByActualDateIn(allDaysInActualMonth);
    }

    public Map<String, BigDecimal> createMonthlyBalanceMap(int actualYear, String code) {
        List<BigDecimal> values = new ArrayList<>();
        for(int i = 1; i < 13; i++){
            BigDecimal balance = new BigDecimal(0);
            List<Item> items = new ArrayList<>();
            items = getActualMonthlyItems(actualYear, i);
            for(Item item : items){
                balance = balance.subtract(item.getCharging());
                balance = balance.add(item.getCrediting());
            }
            values.add(balance);
        }
        Map<String, BigDecimal> monthlyBalanceMap = new HashMap<>();
        monthlyBalanceMap = getMonthlyBalanceMap(values, code);
        return monthlyBalanceMap;
    }

    private Map<String, BigDecimal> getMonthlyBalanceMap(List<BigDecimal> monthlyValues, String code){
        Map<String, BigDecimal> createMap = new HashMap<>();
        for(int i = 0; i < 12; i++){
            String key = "";
            key += Months.fromId(i + 1) + code;
            createMap.put(key, monthlyValues.get(i));
        }
        return createMap;
    }

    public Map<String, BigDecimal> createCumulatedBalanceMap(List<BigDecimal> monthlyBalance, String code){
        List<BigDecimal> cumulatedBalance = new ArrayList<>();
        BigDecimal cumulatedData = new BigDecimal(0);
        for(BigDecimal monthlyData : monthlyBalance){
            cumulatedData = cumulatedData.add(monthlyData);
            cumulatedBalance.add(cumulatedData);
        }
        Map<String, BigDecimal> monthlyBalanceMap = new HashMap<>();
        monthlyBalanceMap = getMonthlyBalanceMap(cumulatedBalance, code);
        return monthlyBalanceMap;
    }

    public List<List<String>> createCashFlowData(int actualYear){
        BigDecimal openingBalance = new BigDecimal(0.0);
        List<Map<String, BigDecimal>> cashFlowMapList = new ArrayList<>();
        List<Category> allCategories = categoryService.makeCategoriesToCashFlow();
        for(int i = 1; i < 13; i++){
            BigDecimal newClosingBalance = new BigDecimal(0.0);
            Map<String, BigDecimal> basicCashFlowMapList = createBasicCashFlowMapList(allCategories);
            List<Item> actualItems = getActualMonthlyItems(actualYear, i);
            Map<String, BigDecimal> loadUpCashFlowMapList = loadUpItemsToBasicCashFlowMap(basicCashFlowMapList, actualItems);
            loadUpCashFlowMapList.put("OPENING BALANCE", openingBalance);
            newClosingBalance = (loadUpCashFlowMapList.get("REVENUES").subtract(loadUpCashFlowMapList.get("EXPENDITURES"))).add(openingBalance);
            loadUpCashFlowMapList.put("CLOSING BALANCE", newClosingBalance);
            openingBalance = newClosingBalance;
            cashFlowMapList.add(loadUpCashFlowMapList);
        }
        List<List<String>> cashFlowData = createCashFlowDataRows(allCategories, cashFlowMapList);
        return cashFlowData;
    }

    private Map<String, BigDecimal> createBasicCashFlowMapList(List<Category> allCategories){
        Map<String, BigDecimal> cashFlowMap = new HashMap<>();
        for(Category category : allCategories){
            BigDecimal basicValue = new BigDecimal(0.0);
            cashFlowMap.put(category.getName(), basicValue);
        }
        return cashFlowMap;
    }

    private Map<String, BigDecimal> loadUpItemsToBasicCashFlowMap(Map<String, BigDecimal> basicCashFlowMap, List<Item> actualItems){
        BigDecimal revenue = new BigDecimal(0.0);
        BigDecimal expenditure = new BigDecimal(0.0);
        for(Item item : actualItems){
            if(item.getCategory().getType().equals("Crediting")){
                BigDecimal sumCrediting = new BigDecimal(0.0);
                sumCrediting = basicCashFlowMap.get(item.getCategory().getName()).add(item.getCrediting());
                basicCashFlowMap.put(item.getCategory().getName(), sumCrediting);
                revenue = revenue.add(item.getCrediting());
            } else {
                BigDecimal sumCharging = new BigDecimal(0.0);
                sumCharging = basicCashFlowMap.get(item.getCategory().getName()).add(item.getCharging());
                basicCashFlowMap.put(item.getCategory().getName(), sumCharging);
                expenditure = expenditure.add(item.getCharging());
            }
        }
        basicCashFlowMap.put("REVENUES", revenue);
        basicCashFlowMap.put("EXPENDITURES", expenditure);
        return basicCashFlowMap;
    }

    private List<List<String>> createCashFlowDataRows(List<Category> allCategories, List<Map<String, BigDecimal>> cashFlowMapList){
        List<List<String>> cashFlowData = new ArrayList<>();
        for(Category category : allCategories){
            List<String> row = new ArrayList<>();
            row.add(category.getName());
            for(int j = 0; j < cashFlowMapList.size(); j++){
                BigDecimal bd = cashFlowMapList.get(j).get(category.getName());
                String bdString = String.valueOf(bd);
                row.add(bdString);
            }
            cashFlowData.add(row);
        }
        return cashFlowData;
    }

    public List<String> getTypeList(){
        List<String> types = new ArrayList<>();
        for(int i = 0; i < 2; i++){
            String key = "";
            key += Types.fromId(i);
            types.add(key);
        }
        return types;
    }

    public List<String> listOfMonth(){
        List<String> months = new ArrayList<>();
        for(int i = 1; i < 13; i++){
            String month = String.valueOf(Months.fromId(i));
            months.add(month);
        }
        return months;
    }

    public List<Integer> listOfYear(){
        List<Integer> years = new ArrayList<>();
        for(int i = startYear; i < endYear + 1; i++){
            years.add(i);
        }
        return years;
    }

    public Item getItemById(long id){
        return itemRepository.findById(id);
    }

    public void deleteItemById(long id){
        itemRepository.deleteById(id);
    }

}
