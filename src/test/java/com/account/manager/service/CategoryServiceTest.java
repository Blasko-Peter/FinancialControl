package com.account.manager.service;

import com.account.manager.model.Category;
import com.account.manager.model.mapping.CategoryMapping;
import com.account.manager.repository.CategoryRepository;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class CategoryServiceTest {

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private CategoryRepository categoryRepository;

    @Test
    public void getCategoryByIdTest(){
        Category testCategory = categoryService.getCategoryById(1);
        Assert.assertNotNull(testCategory);
    }

    @Test
    public void addNewCategoryTest(){
        CategoryMapping newCategoryMapping = new CategoryMapping();
        newCategoryMapping.setName("Test Category 2");
        newCategoryMapping.setType("Charging");
        newCategoryMapping.setIsAvtive(true);
        categoryService.addNewCategory(newCategoryMapping);
        Assert.assertNotNull(categoryRepository.findFirstByName("Test Category 2"));
    }

    @Test
    public void createInactiveCategoriesTest(){
        categoryService.createInactiveCategories();
        Assert.assertNotNull(categoryRepository.findFirstByName("CLOSING BALANCE"));
    }


    @Test
    public void createPetersCategoriesTest(){
        categoryService.createPetersCategories();
        List<Category> allCategories = categoryService.getAllCategories(true);
        Assert.assertNotNull(categoryRepository.findFirstByName("Household"));
    }

    @Test
    public void makeCategoriesToCashFlowTest(){
        List<Category> categoriesToCashFlow = categoryService.makeCategoriesToCashFlow();
        int numberOfLastCategory = categoriesToCashFlow.size() - 1;
        Assert.assertEquals("CLOSING BALANCE", categoriesToCashFlow.get(numberOfLastCategory).getName());
    }

}