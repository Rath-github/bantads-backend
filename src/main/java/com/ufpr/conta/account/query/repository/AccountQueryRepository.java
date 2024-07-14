package com.ufpr.conta.account.query.repository;

import com.ufpr.conta.account.model.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountQueryRepository extends JpaRepository<Account, Long> {
}