package com.example.AutoDetailsShop.rest;

import com.example.AutoDetailsShop.DTO.RefreshTokenRequestDTO;
import com.example.AutoDetailsShop.exceptions.ExpirationException;
import com.example.AutoDetailsShop.exceptions.NotFoundException;
import com.example.AutoDetailsShop.exceptions.ValidationException;
import com.example.AutoDetailsShop.security.RefreshTokenProvider;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import java.util.Map;

@RestController
@RequestMapping("/api/domain/refresh_token/")
public class RefreshTokenRestController {
    private final RefreshTokenProvider refreshTokenProvider;

    RefreshTokenRestController(RefreshTokenProvider refreshTokenProvider){
        this.refreshTokenProvider = refreshTokenProvider;
    }

    @RequestMapping(value = "", method = RequestMethod.POST)
    public ResponseEntity<?> refreshToken(@RequestBody @Valid RefreshTokenRequestDTO requestDTO) throws ValidationException, ExpirationException, NotFoundException {
        HttpHeaders httpHeaders = new HttpHeaders();
        Map<Object, Object> response = refreshTokenProvider.returnNewJwtToken(requestDTO);
        return new ResponseEntity<>(response, httpHeaders, HttpStatus.OK);
    }
}
