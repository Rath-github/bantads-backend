package com.ufpr.conta.account.command.Repository;

import com.ufpr.conta.account.command.models.Account;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountRepository extends JpaRepository<Account, Long> {
}