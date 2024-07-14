package com.ufpr.conta.account.model;

import lombok.Data;
import jakarta.persistence.*;

@Data
@Entity
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long customerId;
    private Double balance;
}