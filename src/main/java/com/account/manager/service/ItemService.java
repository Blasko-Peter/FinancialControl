package com.account.manager.service;

import com.account.manager.model.Item;
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
import java.time.LocalDate;

@Service
public class ItemService {

    private static String importURL = "/Users/blaskopeter/Downloads/Export Data.xlsx";

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

    public void importItems(){
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
                accountService.addNewItemToAccount(newItem);
            }
            workbook.close();
            fis.close();
        } catch (Exception ex) {
            System.out.println(ex);
        }
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

}
