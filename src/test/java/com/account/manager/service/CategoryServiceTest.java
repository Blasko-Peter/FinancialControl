package com.account.manager.service;

import com.account.manager.model.Category;
import com.account.manager.repository.CategoryRepository;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CategoryServiceTest {

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private CategoryService categoryService;

    @Test
    public void categorySaveTest(){
        Category newCategory = new Category();
        newCategory.setIsActive(true);
        newCategory.setType("Crediting");
        newCategory.setName("Test Category");
        categoryRepository.save(newCategory);
        Assert.assertNotNull(categoryRepository.findById(1));
    }

    @Test
    public void getCategoryByIdTest(){
        Assert.assertNotNull(categoryService.getCategoryById(1));
    }

}