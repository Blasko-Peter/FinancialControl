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
        Assert.assertEquals("Test Category 2", categoryRepository.findById(2).getName());
    }

}