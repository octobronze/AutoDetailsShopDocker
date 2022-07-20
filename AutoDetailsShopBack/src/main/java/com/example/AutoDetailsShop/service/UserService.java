package com.example.AutoDetailsShop.service;

import com.example.AutoDetailsShop.domain.Role;
import com.example.AutoDetailsShop.domain.Status;
import com.example.AutoDetailsShop.domain.User;
import com.example.AutoDetailsShop.exceptions.AlreadyExistsException;
import com.example.AutoDetailsShop.exceptions.NotFoundException;
import com.example.AutoDetailsShop.exceptions.ValidationException;

import java.util.List;

public interface UserService {
    User getById(Long id) throws ValidationException, NotFoundException;

    User getByUsername(String username) throws ValidationException;

    void save(User user) throws ValidationException, AlreadyExistsException;

    void delete(Long id) throws ValidationException, NotFoundException;

    void update(User user) throws ValidationException;

    User createUser(String username, String password, String email, String firstName, String lastName, String sex, Role role, Status status);

    List<User> getAll();
}
