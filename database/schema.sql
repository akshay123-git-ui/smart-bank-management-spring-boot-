-- ============================================================
-- Reference schema for Smart Bank Management System.
--
-- You do NOT need to run this manually - hibernate.hbm2ddl.auto=update
-- in applicationContext.xml will create these tables automatically the
-- first time you start Tomcat. This file exists so you can read the
-- data model in one place, and as a fallback if you ever want to set
-- hbm2ddl.auto to "validate" instead and manage schema by hand.
-- ============================================================

CREATE DATABASE IF NOT EXISTS smart_bank_db;
USE smart_bank_db;

CREATE TABLE users (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    full_name VARCHAR(100) NOT NULL,
    email VARCHAR(100) NOT NULL UNIQUE,
    phone VARCHAR(15) NOT NULL,
    password_hash VARCHAR(255) NOT NULL,
    app_pin_hash VARCHAR(100),
    role VARCHAR(20) NOT NULL DEFAULT 'CUSTOMER',
    kyc_verified BOOLEAN DEFAULT FALSE,
    created_at DATETIME
);

CREATE TABLE accounts (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    account_number VARCHAR(20) NOT NULL UNIQUE,
    upi_id VARCHAR(50) UNIQUE,
    balance DECIMAL(15,2) NOT NULL DEFAULT 0,
    created_at DATETIME,
    user_id BIGINT NOT NULL UNIQUE,
    FOREIGN KEY (user_id) REFERENCES users(id)
);

CREATE TABLE transactions (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    account_id BIGINT NOT NULL,
    type VARCHAR(10) NOT NULL,
    amount DECIMAL(15,2) NOT NULL,
    balance_after DECIMAL(15,2) NOT NULL,
    description VARCHAR(255),
    counterparty_account_number VARCHAR(20),
    timestamp DATETIME,
    FOREIGN KEY (account_id) REFERENCES accounts(id)
);

CREATE TABLE loans (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    user_id BIGINT NOT NULL,
    loan_type VARCHAR(50) NOT NULL,
    amount DECIMAL(15,2) NOT NULL,
    tenure_months INT NOT NULL,
    status VARCHAR(20) NOT NULL DEFAULT 'PENDING',
    applied_date DATETIME,
    decision_date DATETIME,
    remarks VARCHAR(255),
    FOREIGN KEY (user_id) REFERENCES users(id)
);

-- To create an admin account: register normally through the app's
-- /register page first (this correctly creates the linked bank account
-- and hashes the password), then promote that user:
--
-- UPDATE users SET role = 'ADMIN' WHERE email = 'youradmin@example.com';


-- Portfolio extension tables. Hibernate can create these automatically with hbm2ddl.auto=update.
CREATE TABLE IF NOT EXISTS beneficiaries (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    owner_id BIGINT NOT NULL,
    name VARCHAR(100) NOT NULL,
    account_number VARCHAR(20) NOT NULL,
    ifsc VARCHAR(20),
    upi_id VARCHAR(50),
    created_at DATETIME,
    UNIQUE KEY uk_beneficiary_owner_account (owner_id, account_number),
    FOREIGN KEY (owner_id) REFERENCES users(id)
);

CREATE TABLE IF NOT EXISTS notifications (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    user_id BIGINT NOT NULL,
    message VARCHAR(255) NOT NULL,
    type VARCHAR(30),
    read_flag BOOLEAN DEFAULT FALSE,
    created_at DATETIME,
    FOREIGN KEY (user_id) REFERENCES users(id)
);

CREATE TABLE IF NOT EXISTS savings_goals (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    user_id BIGINT NOT NULL,
    name VARCHAR(100) NOT NULL,
    target_amount DECIMAL(15,2) NOT NULL,
    saved_amount DECIMAL(15,2) NOT NULL DEFAULT 0,
    target_date DATE,
    created_at DATETIME,
    FOREIGN KEY (user_id) REFERENCES users(id)
);

CREATE TABLE IF NOT EXISTS cards (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    user_id BIGINT NOT NULL UNIQUE,
    masked_number VARCHAR(19) NOT NULL UNIQUE,
    card_type VARCHAR(10) NOT NULL DEFAULT 'DEBIT',
    status VARCHAR(20) NOT NULL DEFAULT 'ACTIVE',
    expiry_date DATE,
    last_four VARCHAR(5),
    FOREIGN KEY (user_id) REFERENCES users(id)
);
