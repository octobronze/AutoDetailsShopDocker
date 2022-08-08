package com.example.AutoDetailsShop.service;

import com.example.AutoDetailsShop.DTO.AuthRequestDTO;
import com.example.AutoDetailsShop.DTO.OtpRequestDTO;
import com.example.AutoDetailsShop.DTO.RegRequestDTO;
import com.example.AutoDetailsShop.domain.Role;
import com.example.AutoDetailsShop.domain.Status;
import com.example.AutoDetailsShop.domain.User;
import com.example.AutoDetailsShop.exceptions.AlreadyExistsException;
import com.example.AutoDetailsShop.exceptions.NotFoundException;
import com.example.AutoDetailsShop.exceptions.PinIsIncorrectException;
import com.example.AutoDetailsShop.exceptions.ValidationException;
import com.example.AutoDetailsShop.security.OtpProvider;
import com.example.AutoDetailsShop.security.RefreshTokenProvider;
import com.example.AutoDetailsShop.security.JwtTokenProvider;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Service;
import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;

@Service("userAuthServiceImpl")
public class UserAuthServiceImpl implements UserAuthService {
    private final UserService userService;
    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider jwtTokenProvider;
    private final RefreshTokenProvider jwtRefreshTokenProvider;
    private final OtpProvider otpProvider;
    private final EmailService emailService;

    UserAuthServiceImpl(@Qualifier("userServiceImpl") UserService userService,
                        AuthenticationManager authenticationManager,
                        JwtTokenProvider jwtTokenProvider,
                        RefreshTokenProvider jwtRefreshTokenProvider, OtpProvider otpProvider,
                        @Qualifier("emailServiceImpl") EmailService emailService){
        this.userService = userService;
        this.authenticationManager = authenticationManager;
        this.jwtTokenProvider = jwtTokenProvider;
        this.jwtRefreshTokenProvider = jwtRefreshTokenProvider;
        this.otpProvider = otpProvider;
        this.emailService = emailService;
    }

    @Override
    public void logout(HttpServletRequest request, HttpServletResponse response) {
        SecurityContextLogoutHandler securityContextLogoutHandler = new SecurityContextLogoutHandler();
        securityContextLogoutHandler.logout(request, response, null);
    }

    @Override
    public Map<Object, Object> authenticate(AuthRequestDTO requestDTO) throws NoSuchPaddingException, IllegalBlockSizeException, NoSuchAlgorithmException, BadPaddingException, InvalidKeyException, ValidationException, MessagingException, UnsupportedEncodingException {
        Map<Object, Object> response = new HashMap<>();
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(requestDTO.getUsername(), requestDTO.getPassword()));
        String pin = otpProvider.createPin();
        String token = otpProvider.buildOtp(pin, requestDTO.getUsername());
        User user = userService.getByUsername(requestDTO.getUsername());
        emailService.send(user, pin);
        response.put("token", token);
        return response;
    }

    @Override
    public User registration(RegRequestDTO requestDTO) throws ValidationException, AlreadyExistsException {
        User user = userService.createUser(requestDTO.getUsername(), requestDTO.getPassword(), requestDTO.getEmail(), requestDTO.getFirstName(), requestDTO.getLastName(), requestDTO.getSex(), Role.USER, Status.Active);
        userService.save(user);
        return user;
    }

    @Override
    public Map<Object, Object> authorization(OtpRequestDTO requestDTO) throws ValidationException, NotFoundException, IllegalBlockSizeException, NoSuchPaddingException, BadPaddingException, NoSuchAlgorithmException, InvalidKeyException, PinIsIncorrectException {
        if(!otpProvider.comparePinToToken(requestDTO.getPin(), requestDTO.getOtpToken())){
            throw new PinIsIncorrectException("Pin is incorrect");
        }
        String username = otpProvider.getUsernameByToken(requestDTO.getOtpToken());
        User user = userService.getByUsername(username);
        String token = jwtTokenProvider.createToken(user.getUsername(), user.getRole().name());
        String refreshToken = jwtRefreshTokenProvider.createToken(user.getUsername());
        Map<Object, Object> response = new HashMap<>();
        response.put("username", user.getUsername());
        response.put("role", user.getRole().name());
        response.put("token", token);
        response.put("refreshToken", refreshToken);
        return response;
    }
}
