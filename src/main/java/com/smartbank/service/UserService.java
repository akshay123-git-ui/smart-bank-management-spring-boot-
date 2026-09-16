package com.smartbank.service;

import com.smartbank.entity.User;

import java.util.List;
import java.util.Optional;

public interface UserService {
    User register(String fullName, String email, String phone, String rawPassword);
    Optional<User> authenticate(String email, String rawPassword);
    Optional<User> findById(Long id);
    User save(User user);
    List<User> listAllCustomers();
}
