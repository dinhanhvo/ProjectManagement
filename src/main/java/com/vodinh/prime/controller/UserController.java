package com.vodinh.prime.controller;

import com.vodinh.prime.entities.User;
import com.vodinh.prime.repositories.RoleRepository;
import com.vodinh.prime.requests.SignUpRequest;
import com.vodinh.prime.responses.ApiResponse;
import com.vodinh.prime.service.UserService;
import com.vodinh.prime.util.SecurityUtils;
import jakarta.validation.Valid;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user")
public class UserController {

    private final UserService userService;

    @Autowired
    RoleRepository roleRepository;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@Valid @RequestBody SignUpRequest signUpRequest) {

        // Creating user's account
        User user = new User();
        BeanUtils.copyProperties(signUpRequest, user);

        if (userService.isExistedUser(user)) {
            return new ResponseEntity<>(new ApiResponse(false, "Username/Email is already taken!"),
                    HttpStatus.BAD_REQUEST);
        }

        User result = userService.saveUser(user);

        return  ResponseEntity.ok(result);
    }

    @GetMapping("/current-user")
    public String getCurrentUser() {
        return SecurityUtils.getCurrentUser();
    }
}
