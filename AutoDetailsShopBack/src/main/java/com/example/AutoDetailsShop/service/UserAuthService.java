package com.example.AutoDetailsShop.service;

import com.example.AutoDetailsShop.DTO.AuthRequestDTO;
import com.example.AutoDetailsShop.DTO.OtpRequestDTO;
import com.example.AutoDetailsShop.DTO.RegRequestDTO;
import com.example.AutoDetailsShop.domain.User;
import com.example.AutoDetailsShop.exceptions.AlreadyExistsException;
import com.example.AutoDetailsShop.exceptions.CodeIsIncorrectException;
import com.example.AutoDetailsShop.exceptions.NotFoundException;
import com.example.AutoDetailsShop.exceptions.ValidationException;
import com.itextpdf.text.pdf.qrcode.WriterException;
import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Map;

public interface UserAuthService {

    void logout(HttpServletRequest request, HttpServletResponse response);

    Map<Object, Object> login(AuthRequestDTO requestDTO) throws ValidationException, AlreadyExistsException, NoSuchPaddingException, IllegalBlockSizeException, NoSuchAlgorithmException, BadPaddingException, InvalidKeyException, MessagingException, IOException, WriterException, com.google.zxing.WriterException, NotFoundException;

    User registration(RegRequestDTO requestDTO) throws ValidationException, AlreadyExistsException;

    Map<Object, Object> authenticate(OtpRequestDTO requestDTO) throws ValidationException, NotFoundException, IllegalBlockSizeException, NoSuchPaddingException, BadPaddingException, NoSuchAlgorithmException, InvalidKeyException, CodeIsIncorrectException;

    void getQR(HttpServletResponse response, HttpServletRequest request) throws IOException, com.google.zxing.WriterException, ValidationException;
}
