package com.example.AutoDetailsShop.rest;

import com.example.AutoDetailsShop.domain.*;
import com.example.AutoDetailsShop.exceptions.AlreadyExistsException;
import com.example.AutoDetailsShop.repos.UserRepo;
import com.example.AutoDetailsShop.security.JwtTokenProvider;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSessionBindingEvent;
import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

@RestController
@Slf4j
@RequestMapping("/api/auth")
public class AuthRestController {

    private final AuthenticationManager authenticationManager;
    private final UserRepo userRepo;
    private final JwtTokenProvider jwtTokenProvider;

    public AuthRestController(AuthenticationManager authenticationManager, UserRepo userRepo, JwtTokenProvider jwtTokenProvider) {
        this.authenticationManager = authenticationManager;
        this.userRepo = userRepo;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @PostMapping("/login")
    public ResponseEntity<?> authenticate(@RequestBody AuthRequestDTO requestDTO) {
        try{
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(requestDTO.getUsername(), requestDTO.getPassword()));
            User user = userRepo.findByUsername(requestDTO.getUsername()).orElseThrow(() -> new UsernameNotFoundException("Username doesn't exist"));
            String token = jwtTokenProvider.createToken(requestDTO.getUsername(), user.getRole().name());
            Map<Object, Object> response = new HashMap<>();
            response.put("username", user.getUsername());
            response.put("role", user.getRole().name());
            response.put("token", token);
            user.setStatus(Status.Active);
            userRepo.save(user);
            return ResponseEntity.ok(response);
        }catch (AuthenticationException e){
            return new ResponseEntity<>("Invalid username/password", HttpStatus.FORBIDDEN);
        }
    }

    @PostMapping("/logout")
    public void logout(HttpServletRequest request, HttpServletResponse response) {
        String username = request.getRemoteUser();
        User user = userRepo.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("Username doesn't exist"));
        user.setStatus(Status.Suspended);
        userRepo.save(user);
        SecurityContextLogoutHandler securityContextLogoutHandler = new SecurityContextLogoutHandler();
        securityContextLogoutHandler.logout(request, response, null);
    }

    @PostMapping("/registration")
    public ResponseEntity<?> register(@RequestBody @Valid RegRequestDTO requestDTO) throws AlreadyExistsException {
        HttpHeaders httpHeaders = new HttpHeaders();
        if(userRepo.findByUsername(requestDTO.getUsername()).isPresent())
            throw new AlreadyExistsException("User already exists");
        User user = new User();
        user.setUsername(requestDTO.getUsername());
        user.setPassword(BCrypt.hashpw(requestDTO.getPassword(), BCrypt.gensalt(12)));
        user.setRole(Role.USER);
        user.setFirstName(requestDTO.getFirstName());
        user.setLastName(requestDTO.getLastName());
        user.setSex(requestDTO.getSex());
        user.setStatus(Status.Suspended);
        user.setEmail(requestDTO.getEmail());
        userRepo.save(user);
        return new ResponseEntity<>(user, httpHeaders, HttpStatus.OK);
    }
}
