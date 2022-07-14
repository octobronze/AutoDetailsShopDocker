package com.example.AutoDetailsShop.service;

import com.example.AutoDetailsShop.domain.Role;
import com.example.AutoDetailsShop.domain.Status;
import com.example.AutoDetailsShop.domain.User;
import com.example.AutoDetailsShop.exceptions.AlreadyExistsException;
import com.example.AutoDetailsShop.exceptions.NotFoundException;
import com.example.AutoDetailsShop.exceptions.ValidationException;
import com.example.AutoDetailsShop.repos.UserRepo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service("userServiceImpl")
public class UserServiceImpl implements UserService {
    private final UserRepo userRepo;


    public UserServiceImpl(UserRepo userRepo) {
        this.userRepo = userRepo;
    }

    public User getById(Long id) throws ValidationException {
        if(id == null)
            throw new ValidationException("Id is null");
        return userRepo.findById(id).orElse(null);
    }

    public void save(User user) throws ValidationException, AlreadyExistsException {
        if(userRepo.findByUsername(user.getUsername()) != null)
            throw new AlreadyExistsException("User already exists");
        if(user == null)
            throw new ValidationException("User is null");
        user.setRole(Role.USER);
        user.setStatus(Status.Suspended);
        userRepo.save(user);
    }

    public void update(User user) throws ValidationException {
        if(user == null)
            throw new ValidationException("User is null");
        user.setRole(Role.USER);
        user.setStatus(Status.Suspended);
        userRepo.save(user);
    }

    public void delete(Long id) throws ValidationException, NotFoundException {
        if(id == null)
            throw new ValidationException("Id is null");
        User user = userRepo.findById(id).orElseThrow(() -> new NotFoundException("User was not found"));
        userRepo.delete(user);
    }

    public List<User> getAll() {
        return userRepo.findAll();
    }

    public User getByUsername(String username) throws ValidationException {
        if(username == null)
            throw new ValidationException("Username is null");
        return userRepo.findByUsername(username).orElse(null);
    }
}

