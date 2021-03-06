package com.account.manager.repository;

import com.account.manager.model.Item;
import org.springframework.data.repository.CrudRepository;

import java.time.LocalDate;
import java.util.List;

public interface ItemRepository extends CrudRepository<Item, Long> {

    Item findById(long id);

    List<Item> findAll();

    List<Item> findAllByAccountId(long id);

    List<Item> findAllByActualDateIn(List<LocalDate> allDaysInActualMonth);

}
