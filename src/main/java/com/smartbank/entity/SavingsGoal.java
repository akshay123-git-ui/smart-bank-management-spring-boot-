package com.smartbank.entity;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "savings_goals")
public class SavingsGoal {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="user_id", nullable=false)
    private User user;

    @Column(nullable=false, length=100)
    private String name;

    @Column(nullable=false, precision=15, scale=2)
    private BigDecimal targetAmount;

    @Column(nullable=false, precision=15, scale=2)
    private BigDecimal savedAmount = BigDecimal.ZERO;

    private LocalDate targetDate;
    private LocalDateTime createdAt = LocalDateTime.now();

    public SavingsGoal() {}
    public SavingsGoal(User user,String name,BigDecimal targetAmount,LocalDate targetDate){
        this.user=user;this.name=name;this.targetAmount=targetAmount;this.targetDate=targetDate;
    }
    public Long getId(){return id;} public void setId(Long id){this.id=id;}
    public User getUser(){return user;} public void setUser(User v){user=v;}
    public String getName(){return name;} public void setName(String v){name=v;}
    public BigDecimal getTargetAmount(){return targetAmount;} public void setTargetAmount(BigDecimal v){targetAmount=v;}
    public BigDecimal getSavedAmount(){return savedAmount;} public void setSavedAmount(BigDecimal v){savedAmount=v;}
    public LocalDate getTargetDate(){return targetDate;} public void setTargetDate(LocalDate v){targetDate=v;}
    public LocalDateTime getCreatedAt(){return createdAt;} public void setCreatedAt(LocalDateTime v){createdAt=v;}
    @Transient public BigDecimal getProgressPercent(){
        if(targetAmount==null || targetAmount.signum()==0) return BigDecimal.ZERO;
        BigDecimal p=savedAmount.multiply(BigDecimal.valueOf(100)).divide(targetAmount,2,java.math.RoundingMode.HALF_UP);
        return p.min(BigDecimal.valueOf(100));
    }
}
