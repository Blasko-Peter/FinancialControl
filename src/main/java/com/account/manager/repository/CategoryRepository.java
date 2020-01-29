package com.account.manager.repository;

import com.account.manager.model.Category;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface CategoryRepository extends CrudRepository<Category, Long> {

    Category findById(long id);

    List<Category> findAllByIsActive(boolean isActive);
}
