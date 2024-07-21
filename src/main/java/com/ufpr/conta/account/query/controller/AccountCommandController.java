package com.ufpr.conta.account.query.controller;

import com.ufpr.conta.account.services.ContaServices;
import com.ufpr.conta.account.dto.AccountDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/command/accounts")
public class AccountCommandController {

    @Autowired
    private AccountCommandService accountCommandService;

    @PostMapping
    public void createAccount(@RequestBody AccountDto accountDto) {
        accountCommandService.createAccount(accountDto);
    }
}