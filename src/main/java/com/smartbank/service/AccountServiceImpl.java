package com.smartbank.service;
import com.smartbank.dao.AccountDao;
import com.smartbank.dao.TransactionDao;
import com.smartbank.entity.*;
import com.smartbank.util.BankException;
import org.hibernate.LockMode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.math.BigDecimal;
import java.util.Optional;
@Service
public class AccountServiceImpl implements AccountService {
    @Autowired private AccountDao accountDao;
    @Autowired private TransactionDao transactionDao;
    @Autowired private NotificationService notificationService;

    @Transactional(readOnly=true)
    public Optional<Account> getAccountByUserId(Long userId){return accountDao.findByUserId(userId);}

    @Transactional(readOnly=true)
    public Optional<Account> getAccountByUpiId(String upiId){return accountDao.findByUpiId(upiId);}

    @Transactional
    public void transfer(Long fromUserId,String recipientIdentifier,BigDecimal amount,String description){
        validateAmount(amount);
        Account sender=accountDao.findByUserId(fromUserId).orElseThrow(()->new BankException("Sender account not found."));
        Account recipient=accountDao.findByAccountNumber(recipientIdentifier)
                .orElseGet(()->accountDao.findByUpiId(recipientIdentifier)
                .orElseThrow(()->new BankException("Recipient account not found.")));
        if(sender.getId().equals(recipient.getId())) throw new BankException("You cannot transfer money to your own account.");
        if(sender.getBalance().compareTo(amount)<0) throw new BankException("Insufficient balance.");

        BigDecimal senderNew=sender.getBalance().subtract(amount);
        BigDecimal recipientNew=recipient.getBalance().add(amount);
        sender.setBalance(senderNew); recipient.setBalance(recipientNew);
        accountDao.save(sender); accountDao.save(recipient);
        String desc=(description==null||description.trim().isEmpty())?"Fund transfer":description.trim();
        transactionDao.save(new Transaction(sender,"DEBIT",amount,senderNew,desc,recipient.getAccountNumber()));
        transactionDao.save(new Transaction(recipient,"CREDIT",amount,recipientNew,desc,sender.getAccountNumber()));
        notificationService.notify(fromUserId,"₹"+amount+" sent to account "+recipient.getAccountNumber()+".","TRANSFER");
        notificationService.notify(recipient.getUser().getId(),"₹"+amount+" received from account "+sender.getAccountNumber()+".","CREDIT");
    }

    @Transactional
    public void deposit(Long userId,BigDecimal amount,String description){
        validateAmount(amount);
        Account a=accountDao.findByUserId(userId).orElseThrow(()->new BankException("Account not found."));
        BigDecimal b=a.getBalance().add(amount); a.setBalance(b); accountDao.save(a);
        transactionDao.save(new Transaction(a,"CREDIT",amount,b,
                (description==null||description.trim().isEmpty())?"Cash deposit":description,null));
        notificationService.notify(userId,"₹"+amount+" credited to your account.","CREDIT");
    }

    @Transactional
    public void withdraw(Long userId,BigDecimal amount,String description){
        validateAmount(amount);
        Account a=accountDao.findByUserId(userId).orElseThrow(()->new BankException("Account not found."));
        if(a.getBalance().compareTo(amount)<0) throw new BankException("Insufficient balance.");
        BigDecimal b=a.getBalance().subtract(amount); a.setBalance(b); accountDao.save(a);
        transactionDao.save(new Transaction(a,"DEBIT",amount,b,
                (description==null||description.trim().isEmpty())?"Cash withdrawal":description,null));
        notificationService.notify(userId,"₹"+amount+" debited from your account.","DEBIT");
    }
    private void validateAmount(BigDecimal amount){if(amount==null||amount.compareTo(BigDecimal.ZERO)<=0)throw new BankException("Amount must be greater than zero.");}
}
