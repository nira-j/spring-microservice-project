package com.ecommerce.user.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.ecommerce.user.dto.LoginRequest;
import com.ecommerce.user.dto.TokenResponse;
import com.ecommerce.user.security.JwtService;


@RestController
@RequestMapping("/auth")
public class AuthController {

    private final JwtService jwtService;

    public AuthController(JwtService jwtService) {
        this.jwtService = jwtService;
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request) {

        if (!request.getUsername().equals("niraj") || 
            !request.getPassword().equals("12345")) {
            return ResponseEntity.status(401).body("Invalid Credentials");
        }

        String token = jwtService.generateToken(request.getUsername());

        return ResponseEntity.ok(
                new TokenResponse(request.getUsername(), "USER", token)
        );
    }
}
