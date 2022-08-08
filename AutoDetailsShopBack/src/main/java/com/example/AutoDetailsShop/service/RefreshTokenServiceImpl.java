package com.example.AutoDetailsShop.service;

import com.example.AutoDetailsShop.domain.RefreshToken;
import com.example.AutoDetailsShop.domain.User;
import com.example.AutoDetailsShop.exceptions.NotFoundException;
import com.example.AutoDetailsShop.exceptions.ValidationException;
import com.example.AutoDetailsShop.repos.RefreshTokenRepo;
import org.springframework.stereotype.Service;
import java.util.Date;

@Service("refreshTokenServiceImpl")
public class RefreshTokenServiceImpl implements RefreshTokenService{
    RefreshTokenRepo refreshTokenRepo;

    RefreshTokenServiceImpl(RefreshTokenRepo refreshTokenRepo){
        this.refreshTokenRepo = refreshTokenRepo;
    }

    @Override
    public RefreshToken getByToken(String token) throws NotFoundException {
        return refreshTokenRepo.findRefreshTokenByToken(token).orElseThrow(() -> new NotFoundException("Refresh token was not found"));
    }

    @Override
    public void delete(Long id) throws NotFoundException, ValidationException {
        if(id == null)
            throw new ValidationException("Id is null");
        RefreshToken refreshToken = refreshTokenRepo.findById(id).orElseThrow(() -> new NotFoundException("Refresh token was not found"));
        refreshTokenRepo.delete(refreshToken);
    }



    @Override
    public void save(RefreshToken refreshToken) throws ValidationException, NotFoundException {
        RefreshToken refreshTokenCheck = getByUsername(refreshToken.getUser().getUsername());
        if(refreshTokenCheck != null){
            delete(refreshTokenCheck.getId());
        }
        refreshTokenRepo.save(refreshToken);
    }

    @Override
    public RefreshToken getByUsername(String username) throws ValidationException {
        if(username == null)
            throw new ValidationException("Username is null");
        return refreshTokenRepo.findRefreshTokenByUser_Username(username).orElse(null);
    }

    @Override
    public boolean isValid(RefreshToken refreshToken) {
        Date currentDate = new Date();
        Date expirationDate = refreshToken.getExpirationTime();
        return expirationDate.after(currentDate);
    }

    @Override
    public RefreshToken buildToken(String token, Date expirationTime, User user) {
        RefreshToken refreshToken = new RefreshToken();
        refreshToken.setToken(token);
        refreshToken.setExpirationTime(expirationTime);
        refreshToken.setUser(user);
        return refreshToken;
    }
}
