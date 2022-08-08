package com.example.AutoDetailsShop.service;

import com.example.AutoDetailsShop.domain.RefreshToken;
import com.example.AutoDetailsShop.domain.User;
import com.example.AutoDetailsShop.exceptions.NotFoundException;
import com.example.AutoDetailsShop.exceptions.ValidationException;
import java.util.Date;

public interface RefreshTokenService {

    RefreshToken getByToken(String token) throws NotFoundException;

    void delete(Long id) throws NotFoundException, ValidationException;

    void save(RefreshToken refreshToken) throws ValidationException, NotFoundException;

    RefreshToken getByUsername(String username) throws ValidationException, NotFoundException;

    boolean isValid(RefreshToken refreshToken );

    RefreshToken buildToken(String token, Date expirationTime, User user);
}
