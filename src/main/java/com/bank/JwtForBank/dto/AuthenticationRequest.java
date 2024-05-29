package com.bank.JwtForBank.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class AuthenticationRequest {

    @NotBlank
    private String username;
    @NotBlank
    private String password;


    public String getUsername() {
        return username;
    }

    public Object getPassword() {
        return password;
    }
}
