SmartBank -- Banking Management System

📌 Project Overview

SmartBank is a web-based Banking Management System developed to provide
secure and convenient banking functionalities for customers and
administrators. The application simulates core banking operations
through a structured and user-friendly web interface.

🛠️ Technologies Used

Java 17

Spring Boot

Spring MVC

Hibernate / JPA

MySQL

JSP

Maven

HTML / CSS / JavaScript

✨ Key Features

User Registration and Login

Secure Password Hashing using BCrypt

4-Digit App PIN Authentication

Customer Account Management

Fund Transfers

Transaction History

Loan Applications and Approval

Beneficiary Management

Savings Goal Management

Notifications

EMI Calculator

Demo UPI QR-Code Generation

Customer and Admin Functionalities

🏗️ Architecture

The application follows a layered Controller--Service--DAO architecture.

User Interface (JSP) → Controller → Service → DAO → Hibernate/JPA →
MySQL

🔐 Security

SmartBank implements BCrypt-based password hashing and a 4-digit App PIN
to provide additional protection for user accounts and sensitive banking
operations.

🗄️ Database

MySQL is used for persistent data storage. The application manages data
related to users, accounts, transactions, loans, beneficiaries, cards,
savings goals, and notifications.

▶️ How to Run

Prerequisites

Java 17

MySQL Server

Maven

Eclipse or another Java IDE

Steps

Clone the repository:

git clone https://github.com/akshay123-git-ui/smart-bank-management-spring-boot-.git

Open the project in Eclipse or your preferred Java IDE.

Create a MySQL database and configure the database connection in
application.properties.

Build the project:

mvn clean install

Run the application:

mvn spring-boot:run

Open:

http://localhost:8080

🎯 Project Objective

The main objective of SmartBank is to demonstrate the development of a
real-world banking application using Java and Spring Boot, with database
integration, authentication, secure password handling, layered
architecture, and multiple banking modules.

👨‍💻 Developer

Akshay
B.Tech -- Computer Science and Engineering
