package com.example.AutoDetailsShop.service;

import com.example.AutoDetailsShop.DTO.UserTOTP_DTO;
import com.warrenstrange.googleauth.ICredentialRepository;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service("credentialRepositoryImpl")
public class CredentialRepositoryImpl implements ICredentialRepository {

    private final Map<String, UserTOTP_DTO> usersKeys = new HashMap<>();

    @Override
    public String getSecretKey(String userName) {
        return usersKeys.get(userName).getSecretKey();
    }

    @Override
    public void saveUserCredentials(String username,
                                    String secretKey,
                                    int verificationCode,
                                    List<Integer> scratchCodes) {
        usersKeys.put(username, new UserTOTP_DTO(username, secretKey, verificationCode, scratchCodes));
    }

    public UserTOTP_DTO getUser(String username) {
        return usersKeys.get(username);
    }
}
