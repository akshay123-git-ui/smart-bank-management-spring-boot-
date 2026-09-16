package com.smartbank.service;
import com.smartbank.entity.SavingsGoal;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
public interface SavingsGoalService {
    List<SavingsGoal> list(Long userId);
    void create(Long userId,String name,BigDecimal target,LocalDate date);
    void addSavings(Long userId,Long goalId,BigDecimal amount);
}
