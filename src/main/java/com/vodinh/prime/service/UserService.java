package com.vodinh.prime.service;


import com.vodinh.prime.entities.Role;
import com.vodinh.prime.entities.User;
import com.vodinh.prime.exception.AppException;
import com.vodinh.prime.repositories.RoleRepository;
import com.vodinh.prime.repositories.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
public class UserService {

    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    public UserService(PasswordEncoder passwordEncoder, UserRepository userRepository, RoleRepository roleRepository) {
        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
    }

    public User saveUser(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        Role userRole = roleRepository.findByName("ROLE_USER")
                .orElseThrow(() -> new AppException("ROLE_USER was not created yet."));

        user.setRoles(Collections.singleton(userRole));

        return userRepository.save(user);
    }

    public boolean isExistedUser(User user) {
        return userRepository.existsByUsername(user.getUsername()) || userRepository.existsByEmail(user.getEmail());
    }
}
