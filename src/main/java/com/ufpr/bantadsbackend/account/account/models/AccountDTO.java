package com.bantads.account.account.models;

import com.bantads.account.account.models.command.AccountC;
import com.bantads.account.account.models.query.AccountQ;
import com.ufpr.bantadsbackend.account.account.models.TransactionDTO;

import java.io.Serializable;
import java.util.List;

public class AccountDTO implements Serializable {
    private Long id;
    private Long userId;
    private Double balance;
    private Double limit;
    private List<TransactionDTO> transactions;

    public AccountDTO() {
    }

    public AccountDTO(Long id, Long userId, Double balance, List<TransactionDTO> transactions, Double limit) {
        this.id = id;
        this.userId = userId;
        this.balance = balance;
        this.transactions = transactions;
        this.limit = limit;
    }





    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Double getBalance() {
        return balance;
    }

    public void setBalance(Double balance) {
        this.balance = balance;
    }

    public List<TransactionDTO> getTransactions() {
        return transactions;
    }

    public void setTransactions(List<TransactionDTO> transactions) {
        this.transactions = transactions;
    }

    public Double getLimit() {
        return limit;
    }

    public void setLimit(Double limit) {
        this.limit = limit;
    }

}
