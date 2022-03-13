package com.example.AutoDetailsShop.service;

import com.example.AutoDetailsShop.domain.User;
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

    public User getById(Long id){
        return userRepo.findById(id).orElse(null);
    }

    public boolean save(User user){
        if(userRepo.findByUsername(user.getUsername()).isPresent()) {
            return false;
        }
        userRepo.save(user);
        return true;
    }

    public void delete(User user){
        userRepo.delete(user);
    }

    public List<User> getAll(){
        return userRepo.findAll();
    }
}

