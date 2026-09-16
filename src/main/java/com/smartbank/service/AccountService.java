package com.smartbank.service;
import com.smartbank.entity.Account;
import java.math.BigDecimal;
import java.util.Optional;
public interface AccountService {
    Optional<Account> getAccountByUserId(Long userId);
    Optional<Account> getAccountByUpiId(String upiId);
    void transfer(Long fromUserId,String recipientIdentifier,BigDecimal amount,String description);
    void deposit(Long userId,BigDecimal amount,String description);
    void withdraw(Long userId,BigDecimal amount,String description);
}
