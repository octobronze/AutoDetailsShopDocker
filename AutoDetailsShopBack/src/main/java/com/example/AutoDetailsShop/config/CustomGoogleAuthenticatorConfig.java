package com.example.AutoDetailsShop.config;

import com.example.AutoDetailsShop.service.CredentialRepositoryImpl;
import com.warrenstrange.googleauth.GoogleAuthenticator;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CustomGoogleAuthenticatorConfig {
    private final CredentialRepositoryImpl credentialRepository;

    public CustomGoogleAuthenticatorConfig(@Qualifier("credentialRepositoryImpl") CredentialRepositoryImpl credentialRepository){
        this.credentialRepository = credentialRepository;
    }

    @Bean
    public GoogleAuthenticator gAuth() {
        GoogleAuthenticator googleAuthenticator = new GoogleAuthenticator();
        googleAuthenticator.setCredentialRepository(credentialRepository);
        return googleAuthenticator;
    }
}
