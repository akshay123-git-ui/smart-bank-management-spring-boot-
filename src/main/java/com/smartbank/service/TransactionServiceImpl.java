package com.smartbank.service;
import com.smartbank.dao.TransactionDao;
import com.smartbank.entity.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
@Service
public class TransactionServiceImpl implements TransactionService {
    @Autowired private TransactionDao transactionDao;
    @Transactional(readOnly=true) public List<Transaction> getMiniStatement(Long accountId){return getMiniStatement(accountId,10);}
    @Transactional(readOnly=true) public List<Transaction> getMiniStatement(Long accountId,int n){return transactionDao.findLastNByAccountId(accountId,n);}
    @Transactional(readOnly=true) public List<Transaction> getRecentForAdmin(int n){return transactionDao.findAllRecent(n);}
}
