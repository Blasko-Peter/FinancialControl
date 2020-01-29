package com.account.manager.repository;

import com.account.manager.model.Account;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface AccountRepository extends CrudRepository<Account, Long> {

    Account findById(long id);

    List<Account> findAll();

    Account findFirstByName(String name);

}
