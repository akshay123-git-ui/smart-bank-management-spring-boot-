package com.smartbank.dao;
import com.smartbank.entity.SavingsGoal;
import java.util.List;
import java.util.Optional;
public interface SavingsGoalDao {
    SavingsGoal save(SavingsGoal g);
    List<SavingsGoal> findByUserId(Long userId);
    Optional<SavingsGoal> findByIdAndUserId(Long id,Long userId);
}
