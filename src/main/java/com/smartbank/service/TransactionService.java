package com.smartbank.service;
import com.smartbank.entity.Transaction;
import java.util.List;
public interface TransactionService {
    List<Transaction> getMiniStatement(Long accountId);
    List<Transaction> getMiniStatement(Long accountId,int n);
    List<Transaction> getRecentForAdmin(int n);
}
