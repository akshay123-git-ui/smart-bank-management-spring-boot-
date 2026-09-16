package com.smartbank.service;

import com.smartbank.entity.Loan;

import java.math.BigDecimal;
import java.util.List;

public interface LoanService {

    Loan applyForLoan(Long userId, String loanType,
                      BigDecimal amount, Integer tenureMonths);

    List<Loan> getLoansForUser(Long userId);

    List<Loan> getAllLoans();

    List<Loan> getPendingLoans();

    void decideLoan(Long loanId, boolean approve, String remarks);

    void payEmi(Long userId, Long loanId);
}