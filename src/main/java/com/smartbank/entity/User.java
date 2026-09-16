package com.smartbank.entity;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100)
    private String fullName;

    @Column(nullable = false, unique = true, length = 100)
    private String email;

    @Column(nullable = false, length = 15)
    private String phone;

    // BCrypt hash, never the raw password. See util.PasswordUtil.
    @Column(nullable = false)
    private String passwordHash;

    // BCrypt hash of the 4-digit SmartBank App PIN. It is null until the user creates one.
    @Column(length = 100)
    private String appPinHash;

    // "CUSTOMER" or "ADMIN". A real system might use a separate roles table,
    // but a simple string field keeps this learnable - you can refactor to
    // an enum/roles table later as a good exercise.
    @Column(nullable = false, length = 20)
    private String role = "CUSTOMER";

    private boolean kycVerified = false;

    private LocalDateTime createdAt = LocalDateTime.now();

    // One user has exactly one account in this simplified model.
    // mappedBy = "user" means Account owns the foreign key.
    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Account account;

    public User() {
    }

    public User(String fullName, String email, String phone, String passwordHash) {
        this.fullName = fullName;
        this.email = email;
        this.phone = phone;
        this.passwordHash = passwordHash;
    }

    // ===================== Getters & Setters =====================

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getFullName() { return fullName; }
    public void setFullName(String fullName) { this.fullName = fullName; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }

    public String getPasswordHash() { return passwordHash; }
    public void setPasswordHash(String passwordHash) { this.passwordHash = passwordHash; }

    public String getAppPinHash() { return appPinHash; }
    public void setAppPinHash(String appPinHash) { this.appPinHash = appPinHash; }

    public String getRole() { return role; }
    public void setRole(String role) { this.role = role; }

    public boolean isKycVerified() { return kycVerified; }
    public void setKycVerified(boolean kycVerified) { this.kycVerified = kycVerified; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }

    public Account getAccount() { return account; }
    public void setAccount(Account account) { this.account = account; }
}
