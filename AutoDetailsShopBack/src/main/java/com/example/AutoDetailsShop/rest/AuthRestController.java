package com.example.AutoDetailsShop.rest;

import com.example.AutoDetailsShop.DTO.AuthRequestDTO;
import com.example.AutoDetailsShop.DTO.RegRequestDTO;
import com.example.AutoDetailsShop.domain.*;
import com.example.AutoDetailsShop.exceptions.AlreadyExistsException;
import com.example.AutoDetailsShop.exceptions.ValidationException;
import com.example.AutoDetailsShop.service.UserAuthService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.Map;

@RestController
@Slf4j
@RequestMapping("/api/auth")
public class AuthRestController {

    private final UserAuthService userAuthService;

    public AuthRestController(@Qualifier("userAuthServiceImpl") UserAuthService userAuthService) {
        this.userAuthService = userAuthService;
    }

    @PostMapping("/login")
    public ResponseEntity<?> authenticate(@RequestBody AuthRequestDTO requestDTO) throws ValidationException, AlreadyExistsException {
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
}
