package com.smartbank.service;
import com.smartbank.dao.*;
import com.smartbank.entity.*;
import com.smartbank.util.BankException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
@Service
public class BeneficiaryServiceImpl implements BeneficiaryService {
    @Autowired private BeneficiaryDao beneficiaryDao;
    @Autowired private UserDao userDao;
    @Autowired private AccountDao accountDao;
    @Transactional(readOnly=true) public List<Beneficiary> list(Long userId){return beneficiaryDao.findByOwnerId(userId);}
    @Transactional public void add(Long userId,String name,String accountNumber,String ifsc,String upiId){
        User owner=userDao.findById(userId).orElseThrow(()->new BankException("User not found."));
        if(accountNumber==null || accountNumber.trim().isEmpty()) throw new BankException("Account number is required.");
        if(!accountDao.findByAccountNumber(accountNumber.trim()).isPresent()) throw new BankException("Beneficiary account does not exist.");
        if(name==null || name.trim().isEmpty()) throw new BankException("Beneficiary name is required.");
        beneficiaryDao.save(new Beneficiary(owner,name.trim(),accountNumber.trim(),ifsc,upiId));
    }
    @Transactional public void remove(Long userId,Long id){
        Beneficiary b=beneficiaryDao.findByIdAndOwnerId(id,userId).orElseThrow(()->new BankException("Beneficiary not found."));
        beneficiaryDao.delete(b);
    }
}
