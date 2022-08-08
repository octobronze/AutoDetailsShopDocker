package com.example.AutoDetailsShop.security;

import com.google.common.cache.CacheBuilder;
import org.apache.commons.codec.binary.Base64;
import org.springframework.stereotype.Component;
import javax.crypto.*;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Random;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.TimeUnit;

@Component
public class OtpProvider {
    private final ConcurrentMap<Object, Object> otpsStorage;
    private final SecretKey secretKey;

    OtpProvider() throws NoSuchAlgorithmException {
        KeyGenerator keyGen = KeyGenerator.getInstance("DESede");
        SecureRandom random = new SecureRandom();
        keyGen.init(random);
        secretKey = keyGen.generateKey();
        otpsStorage = CacheBuilder.newBuilder()
                .expireAfterWrite(5, TimeUnit.MINUTES)
                .build().asMap();
    }

    public String createPin(){
        int random = new Random().nextInt(10000_0000);
        return String.format("%08d", random);
    }

    public String buildOtp(String pin, String username) throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException {
        Cipher cipher = Cipher.getInstance("DESede");
        cipher.init(Cipher.ENCRYPT_MODE, secretKey);
        byte[] plainText = pin.getBytes(StandardCharsets.UTF_8);
        byte[] encryptedText = cipher.doFinal(plainText);
        String encryptedString = new String(Base64.encodeBase64(encryptedText));
        otpsStorage.put(encryptedString, username);
        return encryptedString;
    }

    public boolean comparePinToToken(String pin, String token) throws IllegalBlockSizeException, BadPaddingException, InvalidKeyException, NoSuchPaddingException, NoSuchAlgorithmException {
        Cipher cipher = Cipher.getInstance("DESede");
        cipher.init(Cipher.DECRYPT_MODE, secretKey);
        byte[] encryptedString = Base64.decodeBase64(token);
        byte[] plainText = cipher.doFinal(encryptedString);
        String decryptedString = new String(plainText);
        return decryptedString.compareTo(pin) == 0;
    }

    public String getUsernameByToken(String token){
        return (String) otpsStorage.get(token);
    }
}
