package com.itransition.usermanagementsystem.controller;

import com.itransition.usermanagementsystem.model.User;
import com.itransition.usermanagementsystem.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class MainController {

    @Autowired
    UserRepository userRepository;

    @GetMapping("/login")
    public String login() {
        return "login";
    }


    @GetMapping("/")
    public String home(Model model) {
        List<User> userList = userRepository.findAllUsersById();
        model.addAttribute(userList);
        return "index";
    }




}
