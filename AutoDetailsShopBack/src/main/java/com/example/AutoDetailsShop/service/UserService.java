package com.example.AutoDetailsShop.service;

import com.example.AutoDetailsShop.domain.User;

import java.util.List;

public interface UserService {
    User getById(Long id);

    boolean save(User user);

    void delete(User user);

    List<User> getAll();
}
