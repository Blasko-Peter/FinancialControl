package com.account.manager.service;

import com.account.manager.model.Category;
import com.account.manager.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class CategoryService {

    private List<String> nameOfInactiveCategories = new ArrayList<>(Arrays.asList("OPENING BALANCE", "REVENUES", "EXPENDITURES", "CLOSING BALANCE");

    @Autowired
    private CategoryRepository categoryRepository;

    public Category getCategoryById(long id){
        return categoryFindById(id);
    }

    private Category categoryFindById(long id){
        return categoryRepository.findById(id);
    }

    public void createInactiveCategories(){
        Category newCategory21 = new Category();
        newCategory21.setName("OPENING BALANCE");
        newCategory21.setType("Charging");
        newCategory21.setIsActive(false);
        categoryRepository.save(newCategory21);
        Category newCategory22 = new Category();
        newCategory22.setName("REVENUES");
        newCategory22.setType("Charging");
        newCategory22.setIsActive(false);
        categoryRepository.save(newCategory22);
        Category newCategory23 = new Category();
        newCategory23.setName("EXPENDITURES");
        newCategory23.setType("Charging");
        newCategory23.setIsActive(false);
        categoryRepository.save(newCategory23);
        Category newCategory24 = new Category();
        newCategory24.setName("CLOSING BALANCE");
        newCategory24.setType("Charging");
        newCategory24.setIsActive(false);
        categoryRepository.save(newCategory24);
    }

}
