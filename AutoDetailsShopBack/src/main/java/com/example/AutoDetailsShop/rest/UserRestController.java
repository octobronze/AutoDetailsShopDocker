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
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/domain/users/")
public class UserRestController {
    private final UserService userService;

    public UserRestController(@Qualifier("userServiceImpl") UserService userService) {
        this.userService = userService;
    }

    @RequestMapping(value  = "{id}", method = RequestMethod.GET)
    @PreAuthorize("hasAuthority('developers:write')")
    public ResponseEntity<User> getUser(@PathVariable("id") Long userId) throws ValidationException, NotFoundException {
        HttpHeaders httpHeaders = new HttpHeaders();
        User user = userService.getById(userId);
        return new ResponseEntity<>(user, httpHeaders, HttpStatus.OK);
    }

    @RequestMapping(value = "", method = RequestMethod.POST)
    @PreAuthorize("hasAuthority('developers:write')")
    public ResponseEntity<User> saveUser(@RequestBody @Valid User user) throws ValidationException, AlreadyExistsException {
        HttpHeaders httpHeaders = new HttpHeaders();
        userService.save(user);
        return new ResponseEntity<>(user, httpHeaders, HttpStatus.OK);
    }

    @RequestMapping(value = "", method = RequestMethod.PUT)
    @PreAuthorize("hasAuthority('developers:write')")
    public ResponseEntity<User> updateUser(@RequestBody @Valid User user) throws ValidationException {
        HttpHeaders httpHeaders = new HttpHeaders();
        userService.update(user);
        return new ResponseEntity<>(user, httpHeaders, HttpStatus.OK);
    }

    @RequestMapping(value  = "{id}", method = RequestMethod.DELETE)
    @PreAuthorize("hasAuthority('developers:write')")
    public ResponseEntity<User> deleteUser(@PathVariable("id") Long userId) throws ValidationException, NotFoundException {
        HttpHeaders httpHeaders = new HttpHeaders();
        userService.delete(userId);
        return new ResponseEntity<>(httpHeaders, HttpStatus.OK);
    }

    @RequestMapping(value = "", method = RequestMethod.GET)
    @PreAuthorize("hasAuthority('developers:write')")
    public ResponseEntity<List<User>> getAllUsers() {
        HttpHeaders httpHeaders = new HttpHeaders();
        List<User> users = userService.getAll();
        return new ResponseEntity<>(users, httpHeaders, HttpStatus.OK);
    }
}
