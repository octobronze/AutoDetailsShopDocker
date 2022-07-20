package com.example.AutoDetailsShop.security;

import com.example.AutoDetailsShop.DTO.RefreshTokenRequestDTO;
import com.example.AutoDetailsShop.domain.RefreshToken;
import com.example.AutoDetailsShop.domain.User;
import com.example.AutoDetailsShop.exceptions.ExpirationException;
import com.example.AutoDetailsShop.exceptions.NotFoundException;
import com.example.AutoDetailsShop.exceptions.ValidationException;
import com.example.AutoDetailsShop.service.RefreshTokenService;
import com.example.AutoDetailsShop.service.UserService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Component
public class RefreshTokenProvider {

    private final JwtTokenProvider jwtTokenProvider;
    private final RefreshTokenService refreshTokenService;
    private final UserService userService;

    @Value("${jwt.refreshToken.expiration}")
    private Long validityInMilliseconds;

    RefreshTokenProvider(JwtTokenProvider jwtTokenProvider, @Qualifier("refreshTokenServiceImpl") RefreshTokenService refreshTokenService,@Qualifier("userServiceImpl") UserService userService){
        this.jwtTokenProvider = jwtTokenProvider;
        this.refreshTokenService = refreshTokenService;
        this.userService = userService;
    }

    public String createToken(String username) throws ValidationException, NotFoundException {
        User user = userService.getByUsername(username);
        String token = UUID.randomUUID().toString();
        Date expirationDate = new Date(new Date().getTime() + validityInMilliseconds);
        RefreshToken refreshToken = refreshTokenService.buildToken(token, expirationDate, user);
        refreshTokenService.save(refreshToken);
        return refreshToken.getToken();
    }

    public Map<Object, Object> returnNewJwtToken(RefreshTokenRequestDTO requestDTO) throws ExpirationException, NotFoundException, ValidationException {
        RefreshToken refreshToken = refreshTokenService.getByToken(requestDTO.getRefreshToken());
        if(!refreshTokenService.isValid(refreshToken)){
            throw new ExpirationException("Refresh token is inspired");
        }
        User user = userService.getByUsername(refreshToken.getUser().getUsername());
        String jwtToken = jwtTokenProvider.createToken(user.getUsername(), user.getRole().name());
        Map<Object, Object> response = new HashMap<>();
        response.put("token", jwtToken);
        return response;
    }

}
