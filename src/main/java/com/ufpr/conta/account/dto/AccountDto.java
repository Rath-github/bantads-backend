package com.ufpr.conta.account.dto;

import lombok.Data;

@Data
public class AccountDto {
    private Long customerId;
    private Double balance;
}