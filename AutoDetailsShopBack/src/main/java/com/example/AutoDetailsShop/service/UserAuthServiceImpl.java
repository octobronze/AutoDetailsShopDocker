package com.example.AutoDetailsShop.service;

import com.example.AutoDetailsShop.DTO.AuthRequestDTO;
import com.example.AutoDetailsShop.DTO.RegRequestDTO;
import com.example.AutoDetailsShop.domain.Role;
import com.example.AutoDetailsShop.domain.Status;
import com.example.AutoDetailsShop.domain.User;
import com.example.AutoDetailsShop.exceptions.AlreadyExistsException;
import com.example.AutoDetailsShop.exceptions.NotFoundException;
import com.example.AutoDetailsShop.exceptions.ValidationException;
import com.example.AutoDetailsShop.security.RefreshTokenProvider;
import com.example.AutoDetailsShop.security.JwtTokenProvider;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

@Service("userAuthServiceImpl")
public class UserAuthServiceImpl implements UserAuthService {

    private final UserService userService;
    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider jwtTokenProvider;
    private final RefreshTokenProvider jwtRefreshTokenProvider;

    UserAuthServiceImpl(@Qualifier("userServiceImpl") UserService userService,
                        AuthenticationManager authenticationManager,
                        JwtTokenProvider jwtTokenProvider,
                        RefreshTokenProvider jwtRefreshTokenProvider){
        this.userService = userService;
        this.authenticationManager = authenticationManager;
        this.jwtTokenProvider = jwtTokenProvider;
        this.jwtRefreshTokenProvider = jwtRefreshTokenProvider;
    }

    @Override
    public void logout(HttpServletRequest request, HttpServletResponse response) {
        SecurityContextLogoutHandler securityContextLogoutHandler = new SecurityContextLogoutHandler();
        securityContextLogoutHandler.logout(request, response, null);
    }

    @Override
    public Map<Object, Object> authenticate(AuthRequestDTO requestDTO) throws ValidationException {
        Map<Object, Object> response = new HashMap<>();
        try{
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(requestDTO.getUsername(), requestDTO.getPassword()));
            User user = userService.getByUsername(requestDTO.getUsername());
            String token = jwtTokenProvider.createToken(requestDTO.getUsername(), user.getRole().name());
            String refreshToken = jwtRefreshTokenProvider.createToken(requestDTO.getUsername());
            response.put("username", user.getUsername());
            response.put("role", user.getRole().name());
            response.put("token", token);
            response.put("refreshToken", refreshToken);
            return response;
        }catch (AuthenticationException | NotFoundException a){
            response.put("Invalid username/password", a.getMessage());
        }
        return response;
    }

    @Override
    public User registration(RegRequestDTO requestDTO) throws ValidationException, AlreadyExistsException {
        User user = userService.createUser(requestDTO.getUsername(), requestDTO.getPassword(), requestDTO.getEmail(), requestDTO.getFirstName(), requestDTO.getLastName(), requestDTO.getSex(), Role.USER, Status.Active);
        userService.save(user);
        return user;
    }
}
