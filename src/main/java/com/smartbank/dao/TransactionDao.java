package com.smartbank.dao;
import com.smartbank.entity.Transaction;
import java.util.List;
public interface TransactionDao {
    Transaction save(Transaction transaction);
    List<Transaction> findLastNByAccountId(Long accountId, int n);
    List<Transaction> findAllRecent(int n);
}
