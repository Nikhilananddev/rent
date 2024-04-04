package com.nikhilanand.bookrent.app.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Home {


    @GetMapping("/")
    public String welcome() {
        return "WELCOME TO HOME FROM AUTHENTICATED ENDPOINT!";
    }

    @GetMapping("/admins")
    @PreAuthorize("hasAuthority('ADMIN')")
    public String welcomeAdmin() {
        return "WELCOME TO ADMIN PAGE FROM ADMIN'S ENDPOINT!";
    }
}
