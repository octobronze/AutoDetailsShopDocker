package com.example.AutoDetailsShop.service;

import com.example.AutoDetailsShop.domain.User;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import javax.annotation.PostConstruct;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

@Service("emailServiceImpl")
public class EmailServiceImpl implements EmailService{

    @Value("${spring.mail.host}")
    private String host;

    @Value("${spring.mail.port}")
    private int port;

    @Value("${spring.mail.username}")
    private String username;

    @Value("${spring.mail.password}")
    private String password;

    @Value("${spring.mail.properties.mail.smtp.auth}")
    private boolean smtpAuth;

    @Value("${spring.mail.properties.mail.smtp.starttls.enable}")
    private boolean smtpStarttlsEnable;

    private final JavaMailSenderImpl mailSender = new JavaMailSenderImpl();

    @PostConstruct
    public void setProperties(){
    }

    @Override
    public void send(User user, String pin) throws MessagingException {
        mailSender.setHost(host);
        mailSender.setPort(port);
        mailSender.setUsername(username);
        mailSender.setPassword(password);
        Properties properties = new Properties();
        properties.put("mail.smtp.auth", smtpAuth);
        properties.put("mail.smtp.starttls.enable", smtpStarttlsEnable);
        mailSender.setJavaMailProperties(properties);
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);
        helper.setFrom("teesssstttttttt@gmail.com");
        helper.setTo(user.getEmail());
        String subject = "Here's your One Time Password (OTP) - Expire in 5 minutes";
        String content = "Hello, " + user.getFirstName() + "!\n"
                + "For security reason, you're required to use the following "
                + "One Time Password to login: "
                + "" + pin + "\n"
                + "Note: this OTP is set to expire in 5 minutes.";
        helper.setSubject(subject);
        helper.setText(content);
        mailSender.send(message);
    }
}
