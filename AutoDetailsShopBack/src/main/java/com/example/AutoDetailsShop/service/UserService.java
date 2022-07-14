package com.example.AutoDetailsShop.service;

import com.example.AutoDetailsShop.domain.User;
import com.example.AutoDetailsShop.exceptions.AlreadyExistsException;
import com.example.AutoDetailsShop.exceptions.NotFoundException;
import com.example.AutoDetailsShop.exceptions.ValidationException;

import java.util.List;

public interface UserService {
    User getById(Long id) throws ValidationException;

    User getByUsername(String username) throws ValidationException;

    void save(User user) throws ValidationException, AlreadyExistsException;

    void delete(Long id) throws ValidationException, NotFoundException;

    void update(User user) throws ValidationException;

    List<User> getAll();
}
