package com.example.AutoDetailsShop.rest;

import com.example.AutoDetailsShop.DTO.AuthRequestDTO;
import com.example.AutoDetailsShop.DTO.OtpRequestDTO;
import com.example.AutoDetailsShop.DTO.RegRequestDTO;
import com.example.AutoDetailsShop.domain.*;
import com.example.AutoDetailsShop.exceptions.AlreadyExistsException;
import com.example.AutoDetailsShop.exceptions.NotFoundException;
import com.example.AutoDetailsShop.exceptions.PinIsIncorrectException;
import com.example.AutoDetailsShop.exceptions.ValidationException;
import com.example.AutoDetailsShop.service.UserAuthService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class AuthRestController {
    private final UserAuthService userAuthService;

    public AuthRestController(@Qualifier("userAuthServiceImpl") UserAuthService userAuthService) {
        this.userAuthService = userAuthService;
    }

    @PostMapping("/login")
    public ResponseEntity<?> authenticate(@RequestBody AuthRequestDTO requestDTO) throws ValidationException, AlreadyExistsException, NoSuchPaddingException, IllegalBlockSizeException, NoSuchAlgorithmException, BadPaddingException, InvalidKeyException, MessagingException, UnsupportedEncodingException {
        HttpHeaders httpHeaders = new HttpHeaders();
        Map<Object, Object> response = userAuthService.authenticate(requestDTO);
        return new ResponseEntity<>(response, httpHeaders, HttpStatus.OK);
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logout(HttpServletRequest request, HttpServletResponse response) {
        HttpHeaders httpHeaders = new HttpHeaders();
        userAuthService.logout(request, response);
        return new ResponseEntity<>(httpHeaders, HttpStatus.OK);
    }

    @PostMapping("/registration")
    public ResponseEntity<?> register(@RequestBody @Valid RegRequestDTO requestDTO) throws AlreadyExistsException, ValidationException {
        HttpHeaders httpHeaders = new HttpHeaders();
        User user = userAuthService.registration(requestDTO);
        return new ResponseEntity<>(user, httpHeaders, HttpStatus.OK);
    }

    @PostMapping("/check_pin")
    public ResponseEntity<?> authorization(@RequestBody OtpRequestDTO requestDTO) throws ValidationException, NotFoundException, IllegalBlockSizeException, NoSuchPaddingException, BadPaddingException, NoSuchAlgorithmException, PinIsIncorrectException, InvalidKeyException {
        HttpHeaders httpHeaders = new HttpHeaders();
        Map<Object, Object> response = userAuthService.authorization(requestDTO);
        return new ResponseEntity<>(response, httpHeaders, HttpStatus.OK);
    }
}
