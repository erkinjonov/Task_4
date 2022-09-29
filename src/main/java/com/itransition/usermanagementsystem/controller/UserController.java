package com.itransition.usermanagementsystem.controller;

import com.itransition.usermanagementsystem.dto.UserRegistrationDto;
import com.itransition.usermanagementsystem.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@Controller
public class UserController {

    @Autowired
    UserService userService;


    @GetMapping("/registration")
    public String registrationForm(Model model) {
        model.addAttribute("user", new UserRegistrationDto());
        return "registration";
    }


    @PostMapping("/registration")
    public String registerUser(@ModelAttribute("user")UserRegistrationDto registrationDto) {
        userService.save(registrationDto);
        return "redirect:/registration?success";
    }


    @GetMapping("/delete/{id}")
    public String deleteUser(@PathVariable Long id, HttpServletRequest request) {
        userService.deleteUser(id, request);
        return "redirect:/";
    }


    @GetMapping("/block/{id}")
    public String blockUser(@PathVariable Long id, HttpServletRequest request) {
        userService.blockUser(id, request);
        return "redirect:/";
    }


    @GetMapping("/deleteAll")
    public String deleteAll() {
        userService.deleteAll();
        return "redirect:/registration";
    }


    @GetMapping("/blockAll")
    public String blockAll(HttpServletRequest request) {
        userService.blockAll(request);
        return "redirect:/registration";
    }


    @GetMapping("/unblockAll")
    public String unblockAll() {
        userService.unblockAll();
        return "redirect:/";
    }






}
