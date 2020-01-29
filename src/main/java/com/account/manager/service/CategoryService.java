package com.account.manager.service;

import com.account.manager.model.Category;
import com.account.manager.model.mapping.CategoryMapping;
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
        
    }

    public void addNewCategory(CategoryMapping categoryMapping){
        Category newCategory = new Category();
        newCategory.setName(categoryMapping.getName());
        newCategory.setType(categoryMapping.getType());
        newCategory.setIsActive(true);
        categoryRepository.save(newCategory);
    }


}
