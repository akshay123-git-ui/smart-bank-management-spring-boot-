package com.smartbank.dao;

import com.smartbank.entity.Loan;

import java.util.List;
import java.util.Optional;

public interface LoanDao {
    Loan save(Loan loan);
    Optional<Loan> findById(Long id);
    List<Loan> findByUserId(Long userId);
    List<Loan> findAll();
    List<Loan> findByStatus(String status);
}
