package com.ufpr.conta.account.command.models;

import com.ufpr.conta.account.command.models.ModelCommand;
import com.ufpr.conta.account.query.models.ModelQuery;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.List;

@Entity
@Table(name = "accounts")
public class ModelCommand implements Serializable {
    private static final Long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "user_id")
    private Long userId;

    @Column(name = "balance")
    private Double balance;

    @Column(name = "acc_limit")
    @NotNull(message = "Limite da conta é obrigatório!")
    private Double limit;

    @OneToMany(mappedBy = "account", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    private List<TransactionQ> transactions;

    public ModelCommand() {
    }

    public ModelCommand(Long id, Long userId, Double balance, List<TransactionQ> transactions, Double limit) {
        this.id = id;
        this.userId = userId;
        this.balance = balance;
        this.transactions = transactions;
        this.limit = limit;
    }

    public boolean allowTransaction(Double amount) {
        return limit + balance >= amount;
    }

    public void updateBalance(Double amount) {
        this.balance += amount;
    }

    // Getters and setters

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

    public Double getLimit() {
        return limit;
    }

    public void setLimit(Double limit) {
        this.limit = limit;
    }

    public List<TransactionQ> getTransactions() {
        return transactions;
    }

    public void setTransactions(List<TransactionQ> transactions) {
        this.transactions = transactions;
    }

    public static Long getSerialVersionUID() {
        return serialVersionUID;
    }

    // Convertendo para DTO
    public AccountDTO toDto() {
        AccountDTO dto = new AccountDTO();
        dto.setId(this.id);
        dto.setUserId(this.userId);
        dto.setBalance(this.balance);
        dto.setLimit(this.limit);
        // Convertendo as transações para DTO
        dto.setTransactions(this.transactions.stream()
                .map(TransactionQ::toDto)
                .collect(Collectors.toList()));
        return dto;
    }

    // Convertendo para Command
    public AccountC toCommand() {
        AccountC command = new AccountC();
        command.setId(this.id);
        command.setUserId(this.userId);
        command.setBalance(this.balance);
        command.setLimit(this.limit);
        // Convertendo as transações para Command
        command.setTransactions(this.transactions.stream()
                .map(TransactionQ::toCommand)
                .collect(Collectors.toList()));
        return command;
    }
}