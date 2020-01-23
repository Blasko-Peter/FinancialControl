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

    public Item saveNewItem(ItemMapping itemMapping){
        Item newItem = new Item();
        newItem.setActualDate(itemMapping.getActualDate());
        newItem.setAccount(accountService.getAccountById(itemMapping.getAccountId()));
        newItem.setPlace(itemMapping.getPlace());
        newItem.setCity(itemMapping.getCity());
        newItem.setCategory(categoryService.getCategoryById(itemMapping.getCategoryId()));
        newItem.setCharging(itemMapping.getCharging());
        newItem.setCrediting(itemMapping.getCrediting());
        newItem.setComment(itemMapping.getComment());
        itemRepository.save(newItem);
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
                ItemMapping itemMapping = new ItemMapping();
                itemMapping.setActualDate(actualDate);
                itemMapping.setAccountId(accountId);
                itemMapping.setPlace(place);
                itemMapping.setCity(city);
                itemMapping.setCategoryId(categoryId);
                itemMapping.setCharging(charging);
                itemMapping.setCrediting(crediting);
                itemMapping.setComment(comment);
                Item newItem = new Item();
                newItem = saveNewItem(itemMapping);
                //accountService.addNewItemToAccont(newItem);
            }
            workbook.close();
            fis.close();
        } catch (Exception ex) {
            System.out.println(ex);
        }
    }

}
