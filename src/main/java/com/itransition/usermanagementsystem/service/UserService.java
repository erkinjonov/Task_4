package com.itransition.usermanagementsystem.service;

import com.itransition.usermanagementsystem.dto.UserRegistrationDto;
import com.itransition.usermanagementsystem.model.User;
import com.itransition.usermanagementsystem.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class UserService implements UserDetailsService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    BCryptPasswordEncoder passwordEncoder;

    public void save(UserRegistrationDto registrationDto) {
        User newUser = new User();
        newUser.setFullName(registrationDto.getFullName());
        newUser.setEmail(registrationDto.getEmail());
        newUser.setPassword(passwordEncoder.encode(registrationDto.getPassword()));
        newUser.setRegistrationTime(Timestamp.valueOf(LocalDateTime.now()));
        newUser.setLastLoginTime(Timestamp.valueOf(LocalDateTime.now()));
        userRepository.save(newUser);
    }


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(username);
        if (user == null) {
            throw new UsernameNotFoundException("Email or Password is incorrect. Try again");
        }
        user.setLastLoginTime(Timestamp.valueOf(LocalDateTime.now()));
        userRepository.save(user);
        return user;
    }


    public void deleteUser(Long id, HttpServletRequest request) {
        Optional<User> optionalUser = userRepository.findById(id);
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String deletingUser = ((UserDetails) principal).getUsername();
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            if (user.getEmail().equals(deletingUser)) {
                HttpSession session = request.getSession(false);
                SecurityContextHolder.clearContext();
                if (session != null) {
                    session.invalidate();
                }
            }
        }
        userRepository.deleteById(id);
    }

    public void blockUser(Long id, HttpServletRequest request) {
        Optional<User> optionalUser = userRepository.findById(id);
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String currentUser = ((UserDetails) principal).getUsername();
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            user.setBlocked(!user.isBlocked());

            if (user.getEmail().equals(currentUser)) {
                HttpSession session = request.getSession(false);
                SecurityContextHolder.clearContext();
                if (session != null) {
                    session.invalidate();
                }
            }
            userRepository.save(user);
        }
    }


    public void deleteAll() {
        userRepository.deleteAll();
    }


    public void blockAll(HttpServletRequest request) {
        List<User> userList = userRepository.findAll();
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String currentUser = ((UserDetails) principal).getUsername();
        for (User user : userList) {
            user.setBlocked(true);
            if (user.getEmail().equals(currentUser)) {
                HttpSession session = request.getSession(false);
                SecurityContextHolder.clearContext();
                if (session != null) {
                    session.invalidate();
                }
            }
            userRepository.save(user);
        }
    }


    public void unblockAll() {
        for (User user : userRepository.findAll()) {
            user.setBlocked(false);
            userRepository.save(user);
        }
    }









}
