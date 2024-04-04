package com.nikhilanand.bookrent.app.controller;


import com.nikhilanand.bookrent.app.exchanges.request.AuthRequest;
import com.nikhilanand.bookrent.app.exchanges.request.RegisterRequest;
import com.nikhilanand.bookrent.app.exchanges.response.AuthResponse;
import com.nikhilanand.bookrent.app.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@Controller
public class AuthController {

    @Autowired
    AuthService authService;


    @PostMapping("/register")
    public ResponseEntity<AuthResponse> register(
            @RequestBody RegisterRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(authService.register(request));
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(
            @RequestBody AuthRequest request) {
        return ResponseEntity.ok(authService.login(request));
    }


}
