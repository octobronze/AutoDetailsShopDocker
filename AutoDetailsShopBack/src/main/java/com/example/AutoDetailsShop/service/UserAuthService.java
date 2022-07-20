package com.example.AutoDetailsShop.service;

import com.example.AutoDetailsShop.DTO.AuthRequestDTO;
import com.example.AutoDetailsShop.DTO.RegRequestDTO;
import com.example.AutoDetailsShop.domain.User;
import com.example.AutoDetailsShop.exceptions.AlreadyExistsException;
import com.example.AutoDetailsShop.exceptions.ValidationException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

public interface UserAuthService {

    void logout(HttpServletRequest request, HttpServletResponse response);

    Map<Object, Object> authenticate(AuthRequestDTO requestDTO) throws ValidationException, AlreadyExistsException;

    User registration(RegRequestDTO requestDTO) throws ValidationException, AlreadyExistsException;
}
