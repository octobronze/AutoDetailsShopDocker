package com.example.AutoDetailsShop.security;

import io.jsonwebtoken.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import java.util.Base64;
import java.util.Date;
import java.util.List;

@Component
public class CredentialTokenProvider {
    @Value("${cred.secret}")
    private String secretKey;

    @PostConstruct
    private void init(){
        secretKey = Base64.getEncoder().encodeToString(secretKey.getBytes());
    }

    public String createToken(String username, String userSecretKey, int verificationCode, List<Integer> scratchCodes){
        Claims claims = Jwts.claims().setSubject(username);
        claims.put("secretKey", userSecretKey);
        claims.put("verificationCode", verificationCode);
        claims.put("scratchCodes", scratchCodes);

        return Jwts.builder()
                .setClaims(claims)
                .signWith(SignatureAlgorithm.HS256, secretKey)
                .compact();
    }

    public String getUsername(String token){
        return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody().getSubject();
    }

    public String getUserSecretKey(String token){
        return (String) Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody().get("secretKey");
    }

    public int getVerificationCode(String token){
        return (int) Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody().get("verificationCode");
    }

    public List<Integer> getScratchCodes(String token){
        return (List<Integer>) Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody().get("scratchCodes");
    }
}
