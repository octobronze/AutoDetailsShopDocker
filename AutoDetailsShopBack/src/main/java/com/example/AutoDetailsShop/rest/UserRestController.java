package com.example.AutoDetailsShop.rest;

import com.example.AutoDetailsShop.domain.User;
import com.example.AutoDetailsShop.exceptions.AlreadyExistsException;
import com.example.AutoDetailsShop.exceptions.NotFoundException;
import com.example.AutoDetailsShop.exceptions.ValidationException;
import com.example.AutoDetailsShop.service.UserService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;


import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping("/api/users/")
public class UserRestController {
    private final UserService userService;


    public UserRestController(@Qualifier("userServiceImpl") UserService userService) {
        this.userService = userService;
    }

    @RequestMapping(value  = "{id}", method = RequestMethod.GET)
    @PreAuthorize("hasAuthority('developers:read')")
    public ResponseEntity<User> getUser(@PathVariable("id") Long userId) throws ValidationException, NotFoundException {
        HttpHeaders httpHeaders = new HttpHeaders();
        User user = userService.getById(userId);
        if(user == null)
            throw new NotFoundException("User was not found");
        return new ResponseEntity<>(user, httpHeaders, HttpStatus.OK);
    }

    @RequestMapping(value = "", method = RequestMethod.POST)
    @PreAuthorize("hasAuthority('developers:write')")
    public ResponseEntity<User> saveUser(@RequestBody @Valid User user) throws ValidationException, AlreadyExistsException {
        HttpHeaders httpHeaders = new HttpHeaders();
        if(userService.getByUsername(user.getUsername()) != null)
            throw new AlreadyExistsException("User already exists");
        userService.save(user);
        return new ResponseEntity<>(user, httpHeaders, HttpStatus.OK);
    }

    @RequestMapping(value = "", method = RequestMethod.PUT)
    @PreAuthorize("hasAuthority('developers:write')")
    public ResponseEntity<User> updateUser(@RequestBody @Valid User user) throws ValidationException {
        HttpHeaders httpHeaders = new HttpHeaders();
        userService.save(user);
        return new ResponseEntity<>(user, httpHeaders, HttpStatus.OK);
    }

    @RequestMapping(value  = "{id}", method = RequestMethod.DELETE)
    @PreAuthorize("hasAuthority('developers:write')")
    public ResponseEntity<User> deleteOffer(@PathVariable("id") Long userId) throws ValidationException, NotFoundException {
        HttpHeaders httpHeaders = new HttpHeaders();
        userService.delete(userId);
        return new ResponseEntity<>(httpHeaders, HttpStatus.OK);
    }

    @RequestMapping(value = "", method = RequestMethod.GET)
    public ResponseEntity<List<User>> getAllOffers() {
        HttpHeaders httpHeaders = new HttpHeaders();
        List<User> users = userService.getAll();
        return new ResponseEntity<>(users, httpHeaders, HttpStatus.OK);
    }
}
