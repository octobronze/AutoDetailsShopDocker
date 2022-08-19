package com.example.AutoDetailsShop.service;

import com.example.AutoDetailsShop.DTO.AuthRequestDTO;
import com.example.AutoDetailsShop.DTO.OtpRequestDTO;
import com.example.AutoDetailsShop.DTO.RegRequestDTO;
import com.example.AutoDetailsShop.DTO.UserTOTP_DTO;
import com.example.AutoDetailsShop.domain.Role;
import com.example.AutoDetailsShop.domain.Status;
import com.example.AutoDetailsShop.domain.User;
import com.example.AutoDetailsShop.exceptions.AlreadyExistsException;
import com.example.AutoDetailsShop.exceptions.CodeIsIncorrectException;
import com.example.AutoDetailsShop.exceptions.NotFoundException;
import com.example.AutoDetailsShop.exceptions.ValidationException;
import com.example.AutoDetailsShop.security.CredentialTokenProvider;
import com.example.AutoDetailsShop.security.RefreshTokenProvider;
import com.example.AutoDetailsShop.security.JwtTokenProvider;
import com.example.AutoDetailsShop.security.StringEncryptProvider;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.warrenstrange.googleauth.GoogleAuthenticator;
import com.warrenstrange.googleauth.GoogleAuthenticatorConfig;
import com.warrenstrange.googleauth.GoogleAuthenticatorKey;
import com.warrenstrange.googleauth.GoogleAuthenticatorQRGenerator;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Service;
import com.google.zxing.qrcode.QRCodeWriter;
import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service("userAuthServiceImpl")
public class UserAuthServiceImpl implements UserAuthService {
    private final UserService userService;
    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider jwtTokenProvider;
    private final RefreshTokenProvider jwtRefreshTokenProvider;
    private final GoogleAuthenticator googleAuthenticator;
    private final CredentialRepositoryImpl credentialRepository;
    private final CredentialTokenProvider credentialTokenProvider;
    private final StringEncryptProvider stringEncryptProvider;

    UserAuthServiceImpl(@Qualifier("userServiceImpl") UserService userService,
                        AuthenticationManager authenticationManager,
                        JwtTokenProvider jwtTokenProvider,
                        RefreshTokenProvider jwtRefreshTokenProvider,
                        GoogleAuthenticator googleAuthenticator,
                        CredentialRepositoryImpl credentialRepository,
                        CredentialTokenProvider credentialTokenProvider,
                        StringEncryptProvider stringEncryptProvider){
        this.userService = userService;
        this.authenticationManager = authenticationManager;
        this.jwtTokenProvider = jwtTokenProvider;
        this.jwtRefreshTokenProvider = jwtRefreshTokenProvider;
        this.googleAuthenticator = googleAuthenticator;
        this.credentialRepository = credentialRepository;
        this.credentialTokenProvider = credentialTokenProvider;
        this.stringEncryptProvider = stringEncryptProvider;
    }

    @Override
    public void logout(HttpServletRequest request, HttpServletResponse response) {
        SecurityContextLogoutHandler securityContextLogoutHandler = new SecurityContextLogoutHandler();
        securityContextLogoutHandler.logout(request, response, null);
    }

    @Override
    public Map<Object, Object> login(AuthRequestDTO requestDTO) throws NoSuchPaddingException, IllegalBlockSizeException, NoSuchAlgorithmException, BadPaddingException, InvalidKeyException, ValidationException, NotFoundException {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(requestDTO.getUsername(), requestDTO.getPassword()));
        User user = userService.getByUsername(requestDTO.getUsername());
        Map<Object, Object> response;
        if(user.getCred_token() == null){
            response = getCredentials(requestDTO.getUsername(), user.getRole().name());
        }else{
            response = new HashMap<>();
            response.put("otp_token", stringEncryptProvider.encryptString(requestDTO.getUsername()));
        }
        return response;
    }



    @Override
    public User registration(RegRequestDTO requestDTO) throws ValidationException, AlreadyExistsException {
        User user = userService.createUser(requestDTO.getUsername(), requestDTO.getPassword(), requestDTO.getEmail(), requestDTO.getFirstName(), requestDTO.getLastName(), requestDTO.getSex(), Role.USER, Status.Active);
        userService.save(user);
        return user;
    }

    @Override
    public Map<Object, Object> authenticate(OtpRequestDTO requestDTO) throws ValidationException, NotFoundException, CodeIsIncorrectException, IllegalBlockSizeException, NoSuchPaddingException, BadPaddingException, NoSuchAlgorithmException, InvalidKeyException {
        String username = stringEncryptProvider.decryptString(requestDTO.getToken());
        boolean validate = googleAuthenticator.authorizeUser(username, requestDTO.getCode());
        if(!validate){
            throw new CodeIsIncorrectException("Code code is incorrect");
        }
        User user = userService.getByUsername(username);
        return getCredentials(username, user.getRole().name());
    }

    @Override
    public void getQR(HttpServletResponse response, HttpServletRequest request) throws IOException, WriterException, ValidationException {
        User user = jwtTokenProvider.getUserFromRequest(request);
        GoogleAuthenticatorKey key;
        if (user.getCred_token() == null) {
            key = googleAuthenticator.createCredentials(user.getUsername());
            UserTOTP_DTO userTOTP = credentialRepository.getUser(user.getUsername());
            String credToken = credentialTokenProvider.createToken(userTOTP.getUsername(), userTOTP.getSecretKey(), userTOTP.getValidationCode(), userTOTP.getScratchCodes());
            user.setCred_token(credToken);
            userService.update(user);
        }else {
            String credToken = user.getCred_token();
            String secretKey = credentialTokenProvider.getUserSecretKey(credToken);
            int verificationCode = credentialTokenProvider.getVerificationCode(credToken);
            List<Integer> scratchCodes = credentialTokenProvider.getScratchCodes(credToken);
            GoogleAuthenticatorKey.Builder builder = new GoogleAuthenticatorKey.Builder(secretKey);
            builder.setConfig(new GoogleAuthenticatorConfig());
            builder.setScratchCodes(scratchCodes);
            builder.setVerificationCode(verificationCode);
            key = builder.build();
        }

        QRCodeWriter qrCodeWriter = new QRCodeWriter();
        String otpAuthURL = GoogleAuthenticatorQRGenerator.getOtpAuthTotpURL("auto-details-shop", user.getUsername(), key);
        BitMatrix bitMatrix = qrCodeWriter.encode(otpAuthURL, BarcodeFormat.QR_CODE, 200, 200);
        ServletOutputStream outputStream = response.getOutputStream();
        MatrixToImageWriter.writeToStream(bitMatrix, "PNG", outputStream);
        outputStream.close();
    }

    private Map<Object, Object> getCredentials(String username, String roleName) throws ValidationException, NotFoundException {
        Map<Object, Object> credentials = new HashMap<>();
        String token = jwtTokenProvider.createToken(username, roleName);
        String refreshToken = jwtRefreshTokenProvider.createToken(username);
        credentials.put("username", username);
        credentials.put("role", username);
        credentials.put("token", token);
        credentials.put("refreshToken", refreshToken);
        return credentials;
    }
}
