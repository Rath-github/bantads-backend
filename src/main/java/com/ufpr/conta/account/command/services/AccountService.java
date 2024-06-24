package com.ufpr.conta.account.command.services;

import com.ufpr.conta.account.command.models.Account;
import com.ufpr.conta.account.command.Repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AccountService {

    @Autowired
    private AccountRepository accountRepository;

    public Account createAccount(Account account) {
        return accountRepository.save(account);
    }
}
