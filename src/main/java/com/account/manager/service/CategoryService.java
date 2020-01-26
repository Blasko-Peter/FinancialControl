package com.account.manager.service;

import com.account.manager.model.Category;
import com.account.manager.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    public Category getCategoryById(long id){
        return categoryFindById(id);
    }

    private Category categoryFindById(long id){
        return categoryRepository.findById(id);
    }

}
