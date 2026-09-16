package com.smartbank.dao;

import com.smartbank.entity.User;

import java.util.List;
import java.util.Optional;

public interface UserDao {
    User save(User user);
    Optional<User> findById(Long id);
    Optional<User> findByEmail(String email);
    List<User> findAllCustomers();
    boolean existsByEmail(String email);
}
