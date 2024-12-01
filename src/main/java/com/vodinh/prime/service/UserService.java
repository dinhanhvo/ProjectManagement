package com.vodinh.prime.service;


import com.vodinh.prime.entities.Role;
import com.vodinh.prime.entities.User;
import com.vodinh.prime.enums.RoleEnum;
import com.vodinh.prime.exception.AppException;
import com.vodinh.prime.exception.ResourceNotFoundException;
import com.vodinh.prime.repositories.RoleRepository;
import com.vodinh.prime.repositories.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;

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

    public User createUser(User user) {
        return  createUser(user, String.valueOf(RoleEnum.ROLE_USER));
    }
    @Transactional(readOnly = false)
    public User createUser(User user, String role) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        Role userRole = roleRepository.findByName(role)
                .orElseThrow(() -> new AppException("ROLE was not created yet."));

        user.setRoles(List.of(userRole));

        return userRepository.save(user);
    }

    @Transactional(readOnly = false)
    public User updateUser(User user, boolean updatePass) {
        if (updatePass) {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
        }

        return userRepository.save(user);
    }

    public List<User> getAllUsers() {
        return userRepository.findByDeletedFalse();
    }

    public User getUserById(Long id) {
        return userRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException(" User not found {}", "id", id)
        );
    }

//    public List<User> getCustomerByPhone(String phone) {
//        return userRepository.findByPhoneOrCompanyPhoneIs(phone);
//    }

    @Transactional
    public boolean deleteUser(Long id) {
        if (userRepository.existsById(id)) {
            userRepository.deleteById(id);
            return true;
        }
        return false;
    }

    @Transactional
    public boolean softDeleteUser(Long id) {
        if (userRepository.existsById(id)) {
            User u = userRepository.findById(id).get();
            u.setDeleted(true);
            userRepository.save(u);
            return true;
        }
        return false;
    }

    public boolean isExistedUser(User user) {
        return userRepository.existsByUsername(user.getUsername()) || userRepository.existsByEmail(user.getEmail());
    }

    public List<User> getAllActiveUsers() {
        return userRepository.findByDeletedFalse();
    }

    public List<User> getAllActiveCustomer() {
        return userRepository.findByDeletedFalseAndCompanyNameIsNotNull();
    }

    public List<User> getCustomerByPhone() {
        return userRepository.findByDeletedFalseAndCompanyNameIsNotNull();
    }
}
