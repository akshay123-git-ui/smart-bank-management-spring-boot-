package com.smartbank.entity;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "loans")
public class Loan {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(nullable = false, length = 50)
    private String loanType; // e.g. "PERSONAL", "HOME", "EDUCATION"

    @Column(nullable = false, precision = 15, scale = 2)
    private BigDecimal amount;

    @Column(nullable = false)
    private Integer tenureMonths;

    // "PENDING", "APPROVED", "REJECTED"
    @Column(nullable = false, length = 20)
    private String status = "PENDING";

    private LocalDateTime appliedDate = LocalDateTime.now();

    private LocalDateTime decisionDate;

    @Column(length = 255)
    private String remarks; // admin's note when approving/rejecting

    public Loan() {
    }

    public Loan(User user, String loanType, BigDecimal amount, Integer tenureMonths) {
        this.user = user;
        this.loanType = loanType;
        this.amount = amount;
        this.tenureMonths = tenureMonths;

        // Simple demo EMI calculation
        this.outstandingAmount = amount;

        if (tenureMonths != null && tenureMonths > 0) {
            this.emiAmount = amount.divide(
                    BigDecimal.valueOf(tenureMonths),
                    2,
                    java.math.RoundingMode.HALF_UP
            );
        } else {
            this.emiAmount = amount;
        }

        this.paidEmis = 0;
    }

    // ===================== Getters & Setters =====================

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public User getUser() { return user; }
    public void setUser(User user) { this.user = user; }

    public String getLoanType() { return loanType; }
    public void setLoanType(String loanType) { this.loanType = loanType; }

    public BigDecimal getAmount() { return amount; }
    public void setAmount(BigDecimal amount) { this.amount = amount; }

    public Integer getTenureMonths() { return tenureMonths; }
    public void setTenureMonths(Integer tenureMonths) { this.tenureMonths = tenureMonths; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public LocalDateTime getAppliedDate() { return appliedDate; }
    public void setAppliedDate(LocalDateTime appliedDate) { this.appliedDate = appliedDate; }

    public LocalDateTime getDecisionDate() { return decisionDate; }
    public void setDecisionDate(LocalDateTime decisionDate) { this.decisionDate = decisionDate; }

    public String getRemarks() { return remarks; }
    public void setRemarks(String remarks) { this.remarks = remarks; }
    @Column(nullable = false, precision = 15, scale = 2)
    private BigDecimal outstandingAmount;

    @Column(nullable = false, precision = 15, scale = 2)
    private BigDecimal emiAmount;

    @Column(nullable = false)
    private Integer paidEmis = 0;
    public BigDecimal getOutstandingAmount() {
        return outstandingAmount;
    }

    public void setOutstandingAmount(BigDecimal outstandingAmount) {
        this.outstandingAmount = outstandingAmount;
    }

    public BigDecimal getEmiAmount() {
        return emiAmount;
    }

    public void setEmiAmount(BigDecimal emiAmount) {
        this.emiAmount = emiAmount;
    }

    public Integer getPaidEmis() {
        return paidEmis;
    }

    public void setPaidEmis(Integer paidEmis) {
        this.paidEmis = paidEmis;
    }
}
