package com.ufpr.conta.account.command.services;

import com.ufpr.conta.account.command.Repository.AccountCommandRepository;
import com.ufpr.conta.account.command.services.AccountCommandService;
import com.ufpr.conta.account.dto.AccountDto;
import com.ufpr.conta.account.model.Account;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AccountCommandService {

    @Autowired
    private AccountCommandRepository accountCommandRepository;

    public void createAccount(AccountDto accountDto) {
        Account account = new Account();
        account.setCustomerId(accountDto.getCustomerId());
        account.setBalance(accountDto.getBalance());
        accountCommandRepository.save(account);
    }
}