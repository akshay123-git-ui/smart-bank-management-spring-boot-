package com.smartbank.entity;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "notifications")
public class Notification {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(nullable = false, length = 255)
    private String message;

    @Column(length = 30)
    private String type = "INFO";

    private boolean readFlag = false;
    private LocalDateTime createdAt = LocalDateTime.now();

    public Notification() {}
    public Notification(User user, String message, String type) {
        this.user=user; this.message=message; this.type=type;
    }
    public Long getId(){return id;} public void setId(Long id){this.id=id;}
    public User getUser(){return user;} public void setUser(User user){this.user=user;}
    public String getMessage(){return message;} public void setMessage(String v){this.message=v;}
    public String getType(){return type;} public void setType(String v){this.type=v;}
    public boolean isReadFlag(){return readFlag;} public void setReadFlag(boolean v){this.readFlag=v;}
    public LocalDateTime getCreatedAt(){return createdAt;} public void setCreatedAt(LocalDateTime v){this.createdAt=v;}
}
