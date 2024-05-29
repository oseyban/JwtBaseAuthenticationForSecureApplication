package com.bank.JwtForBank.service;

import com.bank.JwtForBank.security.CustomUserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public interface CustomUserService {
    CustomUserDetails loadUserByUsername(String username) throws UsernameNotFoundException;
}
