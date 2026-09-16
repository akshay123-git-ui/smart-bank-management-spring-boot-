package com.smartbank.service;

import com.smartbank.dao.LoanDao;
import com.smartbank.dao.UserDao;
import com.smartbank.entity.Loan;
import com.smartbank.entity.User;
import com.smartbank.util.BankException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class LoanServiceImpl implements LoanService {

    @Autowired
    private LoanDao loanDao;

    @Autowired
    private UserDao userDao;

    @Autowired
    private AccountService accountService;

    @Autowired
    private NotificationService notificationService;

    @Override
    @Transactional
    public Loan applyForLoan(Long userId,
                             String loanType,
                             BigDecimal amount,
                             Integer tenureMonths) {

        User user = userDao.findById(userId)
                .orElseThrow(() -> new BankException("User not found."));

        if (amount == null ||
                amount.compareTo(BigDecimal.ZERO) <= 0) {

            throw new BankException(
                    "Loan amount must be greater than zero."
            );
        }

        if (tenureMonths == null || tenureMonths <= 0) {
            throw new BankException(
                    "Tenure must be greater than zero."
            );
        }

        Loan loan = new Loan(
                user,
                loanType,
                amount,
                tenureMonths
        );

        return loanDao.save(loan);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Loan> getLoansForUser(Long userId) {
        return loanDao.findByUserId(userId);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Loan> getAllLoans() {
        return loanDao.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public List<Loan> getPendingLoans() {
        return loanDao.findByStatus("PENDING");
    }

    @Override
    @Transactional
    public void decideLoan(Long loanId,
                           boolean approve,
                           String remarks) {

        Loan loan = loanDao.findById(loanId)
                .orElseThrow(() ->
                        new BankException(
                                "Loan application not found."
                        ));

        if (!"PENDING".equals(loan.getStatus())) {
            throw new BankException(
                    "This loan has already been decided."
            );
        }

        loan.setStatus(
                approve ? "APPROVED" : "REJECTED"
        );

        loan.setRemarks(remarks);
        loan.setDecisionDate(LocalDateTime.now());

        if (approve) {

            // Initialize repayment information
            loan.setOutstandingAmount(
                    loan.getAmount()
            );

            BigDecimal emi = loan.getAmount().divide(
                    BigDecimal.valueOf(
                            loan.getTenureMonths()
                    ),
                    2,
                    RoundingMode.HALF_UP
            );

            loan.setEmiAmount(emi);
            loan.setPaidEmis(0);

            loanDao.save(loan);

            // Disburse loan amount to customer's account
            accountService.deposit(
                    loan.getUser().getId(),
                    loan.getAmount(),
                    "Loan disbursement - "
                            + loan.getLoanType()
            );

            notificationService.notify(
                    loan.getUser().getId(),
                    "Your "
                            + loan.getLoanType()
                            + " loan of ₹"
                            + loan.getAmount()
                            + " was approved and credited.",
                    "LOAN"
            );

        } else {

            loanDao.save(loan);

            notificationService.notify(
                    loan.getUser().getId(),
                    "Your "
                            + loan.getLoanType()
                            + " loan application was rejected.",
                    "LOAN"
            );
        }
    }

    // =====================================================
    // PAY EMI
    // =====================================================

    @Override
    @Transactional
    public void payEmi(Long userId, Long loanId) {

        Loan loan = loanDao.findById(loanId)
                .orElseThrow(() ->
                        new BankException("Loan not found."));

        // Make sure this loan belongs to logged-in customer
        if (!loan.getUser().getId().equals(userId)) {

            throw new BankException(
                    "You cannot pay another user's loan."
            );
        }

        // Only approved loans can be paid
        if (!"APPROVED".equals(loan.getStatus())) {

            throw new BankException(
                    "Only approved loans can receive EMI payments."
            );
        }

        // Already completely paid
        if (loan.getOutstandingAmount()
                .compareTo(BigDecimal.ZERO) <= 0) {

            throw new BankException(
                    "This loan is already fully paid."
            );
        }

        BigDecimal emi = loan.getEmiAmount();

        // Protect against invalid EMI
        if (emi == null ||
                emi.compareTo(BigDecimal.ZERO) <= 0) {

            throw new BankException(
                    "Invalid EMI amount."
            );
        }

        // Last EMI may be smaller than normal EMI
        if (emi.compareTo(
                loan.getOutstandingAmount()) > 0) {

            emi = loan.getOutstandingAmount();
        }

        // Deduct EMI from customer's account
        accountService.withdraw(
                userId,
                emi,
                "Loan EMI payment - "
                        + loan.getLoanType()
        );

        // Reduce outstanding amount
        BigDecimal remaining =
                loan.getOutstandingAmount()
                        .subtract(emi);

        loan.setOutstandingAmount(remaining);

        // Increase paid EMI count
        loan.setPaidEmis(
                loan.getPaidEmis() + 1
        );

        // If completely paid, close loan
        if (remaining.compareTo(BigDecimal.ZERO) == 0) {

            loan.setStatus("CLOSED");
        }

        loanDao.save(loan);

        notificationService.notify(
                userId,
                "EMI of ₹"
                        + emi
                        + " paid successfully for your "
                        + loan.getLoanType()
                        + " loan.",
                "LOAN"
        );
    }
}