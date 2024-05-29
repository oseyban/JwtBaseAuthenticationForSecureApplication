package com.bank.JwtForBank.service;

import com.bank.JwtForBank.domain.Roles;
import com.bank.JwtForBank.domain.Users;
import com.bank.JwtForBank.dto.UserDTO;
import com.bank.JwtForBank.exception.ResourceConflictException;
import com.bank.JwtForBank.exception.ResourceNotFoundException;
import com.bank.JwtForBank.repository.RoleRepository;
import com.bank.JwtForBank.repository.UserRepository;
import com.bank.JwtForBank.security.CustomUserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService implements CustomUserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public void registerUser(UserDTO userDTO) {
        if (userRepository.findByUsername(userDTO.getUsername()) != null) {
            throw new ResourceConflictException("Username is already taken");
        }

        Users user = new Users();
        user.setFirstName(userDTO.getFirstName());
        user.setLastName(userDTO.getLastName());
        user.setUsername(userDTO.getUsername());
        user.setPassword(passwordEncoder.encode(userDTO.getPassword()));

        Roles userRole = roleRepository.findByRoleName("ROLE_USER");
        if (userRole == null) {
            throw new ResourceNotFoundException("Default user role not found");
        }
        user.setRole(userRole);

        userRepository.save(user);
    }

    @Override
    public CustomUserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Users user = userRepository.findByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException("User not found with username: " + username);
        }
        return buildCustomUserDetails(user);
    }

    private CustomUserDetails buildCustomUserDetails(Users user) {
        return new CustomUserDetails(
                user.getId(),
                user.getUsername(),
                user.getPassword(),
                user.getRole().getAuthorities()
        );
    }
}












