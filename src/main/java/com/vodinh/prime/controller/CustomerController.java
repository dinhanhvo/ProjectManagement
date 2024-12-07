package com.vodinh.prime.controller;

import com.vodinh.prime.entities.User;
import com.vodinh.prime.enums.RoleEnum;
import com.vodinh.prime.requests.CreateCustomerRequest;
import com.vodinh.prime.responses.ApiResponse;
import com.vodinh.prime.service.UserService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
        user.setUsername(user.getPhone());

        if (userService.isExistedUser(user)) {
            return new ResponseEntity<>(new ApiResponse(false, "Info was already created!"),
                    HttpStatus.BAD_REQUEST);
        }

        User result = userService.createUser(user, String.valueOf(RoleEnum.ROLE_CUSTOMER));

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
            return new ResponseEntity<>(new ApiResponse(false, "Info was already existed!"),
                    HttpStatus.BAD_REQUEST);
        }

        User result = userService.updateUser(user, updatePass);

        return  ResponseEntity.ok(result);
    }


    @GetMapping("/customers")
    public ResponseEntity<Page<User>> getCustomer(
            Pageable pageable,
            HttpServletResponse response
    ) {

        Page<User> customers = userService.getAllCustomer(pageable);
        response.setHeader("X-Total-Count", String.valueOf(customers.getTotalElements()));
        return new ResponseEntity<>(customers, HttpStatus.OK);
    }

    @GetMapping("/customer/{id}")
    public ResponseEntity<User> getCustomerById(@PathVariable("id") Long id) {
        User user = userService.getUserById(id);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

//    @GetMapping("/customer/{phone}")
//    public ResponseEntity<List<User>> getCustomerByPhone(@PathVariable String phone) {
//        List<User> projects = userService.getCustomerByPhone(phone);
//        return new ResponseEntity<>(projects, HttpStatus.OK);
//    }

    @DeleteMapping("/customer/deactivate/{id}")
    public ResponseEntity<Boolean> softDeleteCustomer(@PathVariable Long id) {
        boolean deleted = userService.softDeleteUser(id);
        return deleted ? new ResponseEntity<>(true, HttpStatus.OK) : new ResponseEntity<>(false, HttpStatus.NOT_FOUND);
    }

}
