package com.ufpr.conta.account.command.controllers;

import com.ufpr.conta.account.model.Account;
import com.ufpr.conta.account.command.models.ModelCommand;
import com.ufpr.conta.account.services.contaServices;;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/command/accounts")
public class AccountCommandController {

    @Autowired
    private AccountCommandService accountService;

    @PostMapping
    public Account createAccount(@RequestBody Account account) {
        return accountService.createAccount(account);
    }
}
