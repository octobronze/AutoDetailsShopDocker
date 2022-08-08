package com.example.AutoDetailsShop.service;

import com.example.AutoDetailsShop.domain.User;
import javax.mail.MessagingException;
import java.io.UnsupportedEncodingException;

public interface EmailService {

    void send(User user, String pin) throws MessagingException, UnsupportedEncodingException;
}
