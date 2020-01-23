package com.account.manager.repository;

import com.account.manager.model.Account;
import org.springframework.data.repository.CrudRepository;

public interface AccountRepository extends CrudRepository<Account, Long> {

    Account findById(long id);

}
