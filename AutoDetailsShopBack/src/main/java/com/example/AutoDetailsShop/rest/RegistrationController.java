//package com.example.AutoDetailsShop.rest;
//
//import com.example.AutoDetailsShop.domain.Role;
//import com.example.AutoDetailsShop.domain.User;
//import com.example.AutoDetailsShop.repos.UserRepo;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PostMapping;
//
//import java.util.stream.Collectors;
//import java.util.stream.Stream;
//
//@Controller
//public class RegistrationController {
//
//    private final UserRepo userRepo;
//
//    public RegistrationController(UserRepo userRepo) {
//        this.userRepo = userRepo;
//    }
//
//
//    @GetMapping("/registration")
//    public String getRegistration(){
//        return "registration";
//    }
//
//    @PostMapping("/registration")
//    public String addUser(User user, Model model){
//        User userFromDB = userRepo.findByUsername(user.getUsername());
//        if(userFromDB != null){
//            model.addAttribute("message", "User already exists");
//            return "registration";
//        }
//        user.setRoles(Stream.of(Role.USER).collect(Collectors.toSet()));
//        userRepo.save(user);
//        return "redirect:/login";
//    }
//
//
//}
