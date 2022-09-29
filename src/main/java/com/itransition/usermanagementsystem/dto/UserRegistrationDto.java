package com.itransition.usermanagementsystem.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class UserRegistrationDto {

    private String fullName;
    private String email;
    private String password;
    private String confirmPassword;

}
