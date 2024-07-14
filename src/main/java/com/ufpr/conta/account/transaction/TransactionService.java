package com.ufpr.conta.account.transaction;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class TransactionService {

    @Transactional
    public void executeTransaction(Runnable action) {
        action.run();
    }
}