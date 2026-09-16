package com.smartbank.service;

import com.smartbank.dao.AccountDao;
import com.smartbank.dao.UserDao;
import com.smartbank.entity.Account;
import com.smartbank.entity.User;
import com.smartbank.util.AccountNumberGenerator;
import com.smartbank.util.BankException;
import com.smartbank.util.PasswordUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao userDao;

    @Autowired
    private AccountDao accountDao;

    @Override
    @Transactional
    public User register(String fullName, String email, String phone, String rawPassword) {
        if (fullName == null || fullName.trim().length() < 2) throw new BankException("Enter a valid full name.");
        if (email == null || !email.contains("@")) throw new BankException("Enter a valid email address.");
        if (phone == null || !phone.matches("\\d{10,15}")) throw new BankException("Phone number must contain 10-15 digits.");
        if (rawPassword == null || rawPassword.length() < 6) throw new BankException("Password must contain at least 6 characters.");
        if (userDao.existsByEmail(email)) {
            throw new BankException("An account with this email already exists.");
        }

        User user = new User(fullName, email, phone, PasswordUtil.hash(rawPassword));
        userDao.save(user);

        // Every new customer gets a bank account immediately - in a real
        // system this might wait for KYC approval, but that's a good
        // extension exercise once the core flow works.
        String accountNumber = AccountNumberGenerator.generateAccountNumber();
        String emailLocalPart = email.contains("@") ? email.substring(0, email.indexOf('@')) : email;
        String upiId = AccountNumberGenerator.generateUpiId(emailLocalPart);

        Account account = new Account(accountNumber, upiId, user);
        accountDao.save(account);

        return user;
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<User> authenticate(String email, String rawPassword) {
        Optional<User> userOpt = userDao.findByEmail(email);
        if (userOpt.isPresent() && PasswordUtil.matches(rawPassword, userOpt.get().getPasswordHash())) {
            return userOpt;
        }
        return Optional.empty();
    }

    @Override
    @Transactional
    public User save(User user) {
        return userDao.save(user);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<User> findById(Long id) {
        return userDao.findById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<User> listAllCustomers() {
        return userDao.findAllCustomers();
    }
}
