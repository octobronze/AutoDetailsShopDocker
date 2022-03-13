package com.example.AutoDetailsShop.rest;

import com.example.AutoDetailsShop.domain.AuthRequestDTO;
import com.example.AutoDetailsShop.domain.RegRequestDTO;
import com.example.AutoDetailsShop.domain.Role;
import com.example.AutoDetailsShop.domain.User;
import com.example.AutoDetailsShop.repos.UserRepo;
import com.example.AutoDetailsShop.security.JwtTokenProvider;
import com.example.AutoDetailsShop.service.UserService;
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
import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

@RestController
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
            return ResponseEntity.ok(response);
        }catch (AuthenticationException e){
            return new ResponseEntity<>("Invalid username/password", HttpStatus.FORBIDDEN);
        }
    }

    @PostMapping("/logout")
    public void logout(HttpServletRequest request, HttpServletResponse response) {
        SecurityContextLogoutHandler securityContextLogoutHandler = new SecurityContextLogoutHandler();
        securityContextLogoutHandler.logout(request, response, null);
    }

    @PostMapping("/registration")
    public ResponseEntity<?> register(@RequestBody @Valid RegRequestDTO requestDTO){
        HttpHeaders httpHeaders = new HttpHeaders();

        if(userRepo.findByUsername(requestDTO.getUsername()).isPresent()){
            return new ResponseEntity<>("User already exists", HttpStatus.FOUND);
        }

        User user = new User();
        user.setUsername(requestDTO.getUsername());
        user.setPassword(BCrypt.hashpw(requestDTO.getPassword(), BCrypt.gensalt(12)));
        user.setRole(Role.USER);
        user.setFirstName(requestDTO.getFirstName());
        user.setLastName(requestDTO.getLastName());
        userRepo.save(user);
        return new ResponseEntity<>(user, httpHeaders, HttpStatus.OK);
    }
}
