package com.ufpr.conta.account.query.service;

import com.ufpr.conta.account.query.repository.AccountQueryRepository;
import com.ufpr.conta.account.model.Account;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AccountQueryService {

    @Autowired
    private AccountQueryRepository accountQueryRepository;

    public List<Account> getAllAccounts() {
        return accountQueryRepository.findAll();
    }

    public Account getAccountById(Long id) {
        return accountQueryRepository.findById(id).orElse(null);
    }
}