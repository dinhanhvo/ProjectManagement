package com.vodinh.prime.controller;

import com.vodinh.prime.entities.User;
import com.vodinh.prime.requests.CreateCustomerRequest;
import com.vodinh.prime.responses.ApiResponse;
import com.vodinh.prime.service.UserService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.websocket.server.PathParam;
import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@Tag(name = "Customer Controller", description = "APIs for managing customers")
public class CustomerController {

    private final UserService userService;

    public CustomerController(UserService userService) {
        this.userService = userService;
    }

    // CUSTOMER
    @PostMapping("/customer")
    public ResponseEntity<?> createCustomer(@Valid @RequestBody CreateCustomerRequest createCustomerRequest) {

        // Creating user's account
        User user = new User();
        BeanUtils.copyProperties(createCustomerRequest, user);

        if (userService.isExistedUser(user)) {
            return new ResponseEntity<>(new ApiResponse(false, "Info was already created!"),
                    HttpStatus.BAD_REQUEST);
        }

        User result = userService.createUser(user);

        return  ResponseEntity.ok(result);
    }

    @PutMapping("/customer")
    public ResponseEntity<?> updateCustomer(@Valid @RequestBody CreateCustomerRequest createCustomerRequest) {

        // Creating user's account
        User user = new User();
        boolean updatePass = false;
        if (createCustomerRequest.getPassword().isBlank()) {
            createCustomerRequest.setPassword(null);
        } else {
            updatePass = true;
        }
        BeanUtils.copyProperties(createCustomerRequest, user);

        if (userService.isExistedUser(user)) {
            return new ResponseEntity<>(new ApiResponse(false, "Info was already created!"),
                    HttpStatus.BAD_REQUEST);
        }

        User result = userService.updateUser(user, updatePass);

        return  ResponseEntity.ok(result);
    }


    @GetMapping("/customers")
    public ResponseEntity<List<User>> getCustomer() {
        List<User> projects = userService.getAllActiveCustomer();
        return new ResponseEntity<>(projects, HttpStatus.OK);
    }

    @GetMapping("/customer/{id}")
    public ResponseEntity<User> getCustomerById(@PathVariable("id") Long id) {
        User user = userService.getUserById(id);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @GetMapping("/customer/{phone}")
    public ResponseEntity<List<User>> getCustomerByPhone(@PathVariable String phone) {
        List<User> projects = userService.getCustomerByPhone(phone);
        return new ResponseEntity<>(projects, HttpStatus.OK);
    }

    @DeleteMapping("/customer/deactivate/{id}")
    public ResponseEntity<Boolean> softDeleteCustomer(@PathVariable Long id) {
        boolean deleted = userService.softDeleteUser(id);
        return deleted ? new ResponseEntity<>(true, HttpStatus.OK) : new ResponseEntity<>(false, HttpStatus.NOT_FOUND);
    }

}
