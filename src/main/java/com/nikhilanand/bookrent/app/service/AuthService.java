package com.nikhilanand.bookrent.app.service;


import com.nikhilanand.bookrent.app.exception.user.UserEmailIdAlreadyExistsException;
import com.nikhilanand.bookrent.app.exchanges.request.AuthRequest;
import com.nikhilanand.bookrent.app.exchanges.request.RegisterRequest;
import com.nikhilanand.bookrent.app.exchanges.response.AuthResponse;
import com.nikhilanand.bookrent.app.global.Role;
import com.nikhilanand.bookrent.app.model.UserEntity;
import com.nikhilanand.bookrent.app.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


@Service
public class AuthService {

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    UserRepository userRepository;

    @Autowired
    AuthenticationManager authenticationManager;

    public AuthResponse register(RegisterRequest request) {
        if (request.getRole() == null) {
            request.setRole(Role.USER);
        }

        boolean isUserEmailIdExist = userRepository.existsByEmail(request.getEmail());

        if (isUserEmailIdExist) {
            try {
                throw new UserEmailIdAlreadyExistsException(request.getEmail() + " email id is already exist");
            } catch (UserEmailIdAlreadyExistsException e) {
                throw new RuntimeException(e);
            }
        }

        UserEntity user = UserEntity.builder()
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(request.getRole())
                .build();

        userRepository.save(user);

        return AuthResponse.builder().build();

    }

    public AuthResponse login(AuthRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(), request.getPassword()));
        return AuthResponse.builder().build();
    }

}
