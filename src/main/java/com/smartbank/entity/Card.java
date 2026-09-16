package com.smartbank.entity;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name="cards")
public class Card {
    @Id @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;
    @OneToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="user_id", nullable=false, unique=true)
    private User user;
    @Column(nullable=false, unique=true, length=19)
    private String maskedNumber;
    @Column(nullable=false, length=10)
    private String cardType="DEBIT";
    @Column(nullable=false, length=20)
    private String status="ACTIVE";
    private LocalDate expiryDate;
    @Column(length=5)
    private String lastFour;

    public Card(){}
    public Card(User user,String maskedNumber,String lastFour,LocalDate expiryDate){
        this.user=user;this.maskedNumber=maskedNumber;this.lastFour=lastFour;this.expiryDate=expiryDate;
    }
    public Long getId(){return id;} public void setId(Long v){id=v;}
    public User getUser(){return user;} public void setUser(User v){user=v;}
    public String getMaskedNumber(){return maskedNumber;} public void setMaskedNumber(String v){maskedNumber=v;}
    public String getCardType(){return cardType;} public void setCardType(String v){cardType=v;}
    public String getStatus(){return status;} public void setStatus(String v){status=v;}
    public LocalDate getExpiryDate(){return expiryDate;} public void setExpiryDate(LocalDate v){expiryDate=v;}
    public String getLastFour(){return lastFour;} public void setLastFour(String v){lastFour=v;}
}
