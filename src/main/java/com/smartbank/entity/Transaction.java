package com.smartbank.entity;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "transactions")
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // The account this transaction row belongs to (each side of a transfer
    // gets its own row - one DEBIT row for the sender, one CREDIT row for
    // the receiver - which is what makes "mini statement per account" easy).
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "account_id", nullable = false)
    private Account account;

    // "CREDIT", "DEBIT". Kept as a String for simplicity - try converting
    // this to a Java enum as a learning exercise.
    @Column(nullable = false, length = 10)
    private String type;

    @Column(nullable = false, precision = 15, scale = 2)
    private BigDecimal amount;

    // The account's balance immediately after this transaction - makes
    // mini-statement rendering trivial without recomputing history.
    @Column(nullable = false, precision = 15, scale = 2)
    private BigDecimal balanceAfter;

    @Column(length = 255)
    private String description;

    // For transfers: the counterparty account number, so a statement can
    // show "Transferred to 100234..." or "Received from 100567...".
    @Column(length = 20)
    private String counterpartyAccountNumber;

    private LocalDateTime timestamp = LocalDateTime.now();

    public Transaction() {
    }

    public Transaction(Account account, String type, BigDecimal amount,
                        BigDecimal balanceAfter, String description,
                        String counterpartyAccountNumber) {
        this.account = account;
        this.type = type;
        this.amount = amount;
        this.balanceAfter = balanceAfter;
        this.description = description;
        this.counterpartyAccountNumber = counterpartyAccountNumber;
    }

    // ===================== Getters & Setters =====================

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Account getAccount() { return account; }
    public void setAccount(Account account) { this.account = account; }

    public String getType() { return type; }
    public void setType(String type) { this.type = type; }

    public BigDecimal getAmount() { return amount; }
    public void setAmount(BigDecimal amount) { this.amount = amount; }

    public BigDecimal getBalanceAfter() { return balanceAfter; }
    public void setBalanceAfter(BigDecimal balanceAfter) { this.balanceAfter = balanceAfter; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public String getCounterpartyAccountNumber() { return counterpartyAccountNumber; }
    public void setCounterpartyAccountNumber(String counterpartyAccountNumber) {
        this.counterpartyAccountNumber = counterpartyAccountNumber;
    }

    public LocalDateTime getTimestamp() { return timestamp; }
    public void setTimestamp(LocalDateTime timestamp) { this.timestamp = timestamp; }
}
