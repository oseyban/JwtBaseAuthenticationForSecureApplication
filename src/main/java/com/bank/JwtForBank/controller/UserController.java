package com.bank.JwtForBank.controller;

import com.bank.JwtForBank.dto.AuthenticationRequest;
import com.bank.JwtForBank.dto.UserDTO;
import com.bank.JwtForBank.security.AuthenticationResponse;
import com.bank.JwtForBank.security.UtilsForJwt;
import com.bank.JwtForBank.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UtilsForJwt utilsForJwt;

    public UserController(UserService userService, AuthenticationManager authenticationManager, UtilsForJwt utilsForJwt) {
        this.userService = userService;
        this.authenticationManager = authenticationManager;
        this.utilsForJwt = utilsForJwt;
    }

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@Valid @RequestBody UserDTO userDTO) {
        try {
            userService.registerUser(userDTO);
            return ResponseEntity.ok("User registered successfully");
        } catch (Exception e) {
            return ResponseEntity.status(400).body("Error registering user: " + e.getMessage());
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@Valid @RequestBody AuthenticationRequest authenticationRequest) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(authenticationRequest.getUsername(), authenticationRequest.getPassword())
            );

            final UserDetails userDetails = userService.loadUserByUsername(authenticationRequest.getUsername());

            final String token = utilsForJwt.generateToken(userDetails);

            return ResponseEntity.ok(new AuthenticationResponse(token));
        } catch (Exception e) {
            return ResponseEntity.status(401).body("Invalid username or password");
        }
    }

}
