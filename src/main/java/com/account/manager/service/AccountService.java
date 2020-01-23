package com.account.manager.service;

import com.account.manager.model.Account;
import com.account.manager.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AccountService {

    @Autowired
    private AccountRepository accountRepository;

    public Account getAccountById(long id){
        return accountRepository.findById(id);
    }

}
