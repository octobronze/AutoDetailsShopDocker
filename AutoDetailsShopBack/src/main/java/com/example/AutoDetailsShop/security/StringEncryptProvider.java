package com.example.AutoDetailsShop.security;

import org.apache.commons.codec.binary.Base64;
import org.springframework.stereotype.Component;

import javax.crypto.*;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

@Component
public class StringEncryptProvider {
    private final SecretKey secretKey;

    StringEncryptProvider() throws NoSuchAlgorithmException {
        KeyGenerator keyGen = KeyGenerator.getInstance("DESede");
        SecureRandom random = new SecureRandom();
        keyGen.init(random);
        secretKey = keyGen.generateKey();
    }

    public String encryptString(String string) throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException {
        Cipher cipher = Cipher.getInstance("DESede");
        cipher.init(Cipher.ENCRYPT_MODE, secretKey);
        byte[] plainText = string.getBytes(StandardCharsets.UTF_8);
        byte[] encryptedText = cipher.doFinal(plainText);
        String encryptedString = new String(Base64.encodeBase64(encryptedText));
        return encryptedString;
    }

    public String decryptString(String encryptedString) throws IllegalBlockSizeException, BadPaddingException, InvalidKeyException, NoSuchPaddingException, NoSuchAlgorithmException {
        Cipher cipher = Cipher.getInstance("DESede");
        cipher.init(Cipher.DECRYPT_MODE, secretKey);
        byte[] encryptedArray = Base64.decodeBase64(encryptedString);
        byte[] plainText = cipher.doFinal(encryptedArray);
        String decryptedString = new String(plainText);
        return decryptedString;
    }
}
