package com.smartbank.dao;

import com.smartbank.entity.Account;

import java.util.Optional;

public interface AccountDao {
    Account save(Account account);
    Optional<Account> findByAccountNumber(String accountNumber);
    Optional<Account> findByUpiId(String upiId);
    Optional<Account> findByUserId(Long userId);
}
