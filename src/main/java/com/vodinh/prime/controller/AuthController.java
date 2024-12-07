package com.vodinh.prime.controller;

import com.vodinh.prime.requests.LoginRequest;
import com.vodinh.prime.responses.JwtAuthenticationResponse;
import com.vodinh.prime.security.CustomUserDetailsService;
import com.vodinh.prime.security.JwtTokenProvider;
import com.vodinh.prime.service.UserService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import org.springframework.security.core.GrantedAuthority;


import java.util.List;
import java.util.stream.Collectors;

@RestController
@Tag(name = "Auth Controller", description = "APIs for author/authen users")
@RequestMapping("/api/auth")
public class AuthController {

    private static final Logger log = LoggerFactory.getLogger(AuthController.class);
    private final AuthenticationManager authenticationManager;

    private final CustomUserDetailsService customUserDetailsService;

    private final JwtTokenProvider tokenProvider;

    public AuthController(AuthenticationManager authenticationManager, CustomUserDetailsService customUserDetailsService, JwtTokenProvider tokenProvider) {
        this.authenticationManager = authenticationManager;
        this.customUserDetailsService = customUserDetailsService;
        this.tokenProvider = tokenProvider;
    }

    @PostMapping("/signin")
    public ResponseEntity<JwtAuthenticationResponse> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getUsername(),
                        loginRequest.getPassword()

                )
        );

        UserDetails userDetails = customUserDetailsService.loadUserByUsername(loginRequest.getUsername());

        SecurityContextHolder.getContext().setAuthentication(authentication);

        List<String> roles = userDetails.getAuthorities()
                .stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList());

        String jwt = tokenProvider.generateToken(authentication, roles);
        String jwtDecode = tokenProvider.decodeJWT(jwt);
        log.debug("Jwt content: {}", jwtDecode);
        return ResponseEntity.ok(new JwtAuthenticationResponse(jwt));
    }

}
