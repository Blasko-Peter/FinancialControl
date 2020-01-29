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

    private List<String> nameOfInactiveCategories = new ArrayList<>(Arrays.asList("OPENING BALANCE", "REVENUES", "EXPENDITURES", "CLOSING BALANCE"));
    private List<String> nameOfPetersCategories = new ArrayList<>(Arrays.asList("Account Opening", "Auto", "Bank Charge", "Bonus", "Clothing", "Deposit Money", "Drugstore", "Food", "Gift", "Health", "Household", "Interest Income", "Leisure Time", "Loan", "Other Housing", "Other Income", "Save Up", "Tax", "Travel", "Withdraw Money"));
    private List<String> typeOfPetersCategories = new ArrayList<>(Arrays.asList("Crediting", "Charging", "Charging", "Crediting", "Charging", "Crediting", "Charging", "Charging","Charging", "Charging", "Charging", "Crediting", "Charging", "Charging", "Charging", "Crediting", "Charging", "Charging", "Charging", "Charging"));

    @Autowired
    private CategoryRepository categoryRepository;

    public Category getCategoryById(long id){
        return categoryFindById(id);
    }

    private Category categoryFindById(long id){
        return categoryRepository.findById(id);
    }

    public void createInactiveCategories(){
        for(String nameOfInvalidCategory : nameOfInactiveCategories){
            CategoryMapping newCategoryMapping = new CategoryMapping();
            newCategoryMapping.setName(nameOfInvalidCategory);
            newCategoryMapping.setType("Crediting");
            newCategoryMapping.setIsAvtive(false);
            addNewCategory(newCategoryMapping);
        }
    }

    public void addNewCategory(CategoryMapping categoryMapping){
        Category newCategory = new Category();
        newCategory.setName(categoryMapping.getName());
        newCategory.setType(categoryMapping.getType());
        newCategory.setIsActive(categoryMapping.getIsActive());
        saveNewCategory(newCategory);
    }

    private void saveNewCategory(Category newCategory){
        categoryRepository.save(newCategory);
    }

    public void createPetersCategories(){
        for(int i = 0; i < nameOfPetersCategories.size(); i++){
            CategoryMapping newCategoryMapping = new CategoryMapping();
            newCategoryMapping.setIsAvtive(true);
            newCategoryMapping.setName(nameOfPetersCategories.get(i));
            newCategoryMapping.setType(typeOfPetersCategories.get(i));
            addNewCategory(newCategoryMapping);
        }
    }

    public List<Category> getAllCategories(boolean isActive){
        return categoryRepository.findAllByIsActive(isActive);
    }

}
