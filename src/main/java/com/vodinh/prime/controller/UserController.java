package com.vodinh.prime.controller;

import com.vodinh.prime.entities.User;
import com.vodinh.prime.requests.SignUpRequest;
import com.vodinh.prime.responses.ApiResponse;
import com.vodinh.prime.service.UserService;
import com.vodinh.prime.util.SecurityUtils;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@Tag(name = "User Controller", description = "APIs for managing users")
public class UserController {

    private final UserService userService;


    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/user/signup")
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

    @GetMapping("/users")
    public ResponseEntity<List<User>> getAllUsers() {
        List<User> projects = userService.getAllUsers();
        return new ResponseEntity<>(projects, HttpStatus.OK);
    }

    // delete user by id
    @DeleteMapping("/user/{id}")
    public ResponseEntity<Boolean> deleteUser(@PathVariable Long id) {
        boolean deleted = userService.deleteUser(id);
        return deleted ? new ResponseEntity<>(true, HttpStatus.OK) : new ResponseEntity<>(false, HttpStatus.NOT_FOUND);
    }

    @GetMapping("/current-user")
    public String getCurrentUser() {
        return SecurityUtils.getCurrentUser();
    }
}
