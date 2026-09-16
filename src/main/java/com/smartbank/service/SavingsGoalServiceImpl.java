package com.smartbank.service;
import com.smartbank.dao.*;
import com.smartbank.entity.*;
import com.smartbank.util.BankException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
@Service
public class SavingsGoalServiceImpl implements SavingsGoalService {
    @Autowired private SavingsGoalDao goalDao;
    @Autowired private UserDao userDao;
    @Transactional(readOnly=true) public List<SavingsGoal> list(Long userId){return goalDao.findByUserId(userId);}
    @Transactional public void create(Long userId,String name,BigDecimal target,LocalDate date){
        User u=userDao.findById(userId).orElseThrow(()->new BankException("User not found."));
        if(target==null || target.signum()<=0) throw new BankException("Target amount must be greater than zero.");
        goalDao.save(new SavingsGoal(u,name,target,date));
    }
    @Transactional public void addSavings(Long userId,Long goalId,BigDecimal amount){
        SavingsGoal g=goalDao.findByIdAndUserId(goalId,userId).orElseThrow(()->new BankException("Goal not found."));
        if(amount==null || amount.signum()<=0) throw new BankException("Amount must be greater than zero.");
        if(g.getSavedAmount().add(amount).compareTo(g.getTargetAmount())>0) throw new BankException("Savings cannot exceed the target amount.");
        g.setSavedAmount(g.getSavedAmount().add(amount)); goalDao.save(g);
    }
}
