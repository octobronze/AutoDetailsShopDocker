package com.example.AutoDetailsShop.service;

import com.example.AutoDetailsShop.DTO.AuthRequestDTO;
import com.example.AutoDetailsShop.DTO.OtpRequestDTO;
import com.example.AutoDetailsShop.DTO.RegRequestDTO;
import com.example.AutoDetailsShop.domain.User;
import com.example.AutoDetailsShop.exceptions.AlreadyExistsException;
import com.example.AutoDetailsShop.exceptions.NotFoundException;
import com.example.AutoDetailsShop.exceptions.PinIsIncorrectException;
import com.example.AutoDetailsShop.exceptions.ValidationException;
import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Map;

public interface UserAuthService {

    void logout(HttpServletRequest request, HttpServletResponse response);

    Map<Object, Object> authenticate(AuthRequestDTO requestDTO) throws ValidationException, AlreadyExistsException, NoSuchPaddingException, IllegalBlockSizeException, NoSuchAlgorithmException, BadPaddingException, InvalidKeyException, MessagingException, UnsupportedEncodingException;

    User registration(RegRequestDTO requestDTO) throws ValidationException, AlreadyExistsException;

    Map<Object, Object> authorization(OtpRequestDTO requestDTO) throws ValidationException, NotFoundException, IllegalBlockSizeException, NoSuchPaddingException, BadPaddingException, NoSuchAlgorithmException, InvalidKeyException, PinIsIncorrectException;
}
