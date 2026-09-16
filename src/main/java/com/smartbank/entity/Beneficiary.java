package com.smartbank.entity;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "beneficiaries",
       uniqueConstraints = @UniqueConstraint(columnNames = {"owner_id", "account_number"}))
public class Beneficiary {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "owner_id", nullable = false)
    private User owner;

    @Column(nullable = false, length = 100)
    private String name;

    @Column(name = "account_number", nullable = false, length = 20)
    private String accountNumber;

    @Column(length = 20)
    private String ifsc;

    @Column(length = 50)
    private String upiId;

    private LocalDateTime createdAt = LocalDateTime.now();

    public Beneficiary() {}
    public Beneficiary(User owner, String name, String accountNumber, String ifsc, String upiId) {
        this.owner = owner; this.name = name; this.accountNumber = accountNumber;
        this.ifsc = ifsc; this.upiId = upiId;
    }
    public Long getId(){return id;} public void setId(Long id){this.id=id;}
    public User getOwner(){return owner;} public void setOwner(User owner){this.owner=owner;}
    public String getName(){return name;} public void setName(String name){this.name=name;}
    public String getAccountNumber(){return accountNumber;} public void setAccountNumber(String v){this.accountNumber=v;}
    public String getIfsc(){return ifsc;} public void setIfsc(String v){this.ifsc=v;}
    public String getUpiId(){return upiId;} public void setUpiId(String v){this.upiId=v;}
    public LocalDateTime getCreatedAt(){return createdAt;} public void setCreatedAt(LocalDateTime v){this.createdAt=v;}
}
