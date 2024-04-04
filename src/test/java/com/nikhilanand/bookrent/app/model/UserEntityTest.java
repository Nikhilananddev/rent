package com.nikhilanand.bookrent.app.model;

import com.nikhilanand.bookrent.app.global.Role;
import com.nikhilanand.bookrent.app.repositories.RentalRepository;
import com.nikhilanand.bookrent.app.repositories.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureDataJpa;
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureTestEntityManager;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;
import java.util.UUID;

import static org.springframework.test.util.AssertionErrors.assertEquals;
import static org.springframework.test.util.AssertionErrors.assertFalse;


@AutoConfigureDataJpa
@AutoConfigureTestDatabase()
@AutoConfigureTestEntityManager
@SpringBootTest
class UserEntityTest {

    @Autowired
    UserRepository userRepository;

    @Autowired
    RentalRepository rentalRepository;

    @Autowired
    PasswordEncoder passwordEncoder;


    @Test
    void addUser() {

        BookEntity book = BookEntity.builder()
                .title("THINK")
                .build();
        UserEntity user = UserEntity.builder()
                .firstName("Sam")
                .lastName("jon")
                .email(UUID.randomUUID() + "@gmail.com")
                .role(Role.USER)
                .password(passwordEncoder.encode("password"))
                .build();
        UserEntity user1 = userRepository.save(user);


        assertEquals("PASS", user, user1);
    }


    @Test
    void getUserById() {
        Optional<UserEntity> user = userRepository.findById(2L);

        assertEquals("object id is equal " + user.get(), 1L, user.get().getId());
    }


    @Test
    void updateUserDetails() {
        Optional<UserEntity> user = userRepository.findById(1L);
        UserEntity userEntity = new UserEntity();
        if (user.isPresent()) {
            userEntity = user.get();
            userEntity.setFirstName("SAM");
            userEntity.setLastName("ALT");
            userEntity.setEmail("ONE@gmail.com");
            userEntity.setRole(Role.ADMIN);
        }

        UserEntity updatesUser = userRepository.save(userEntity);

        System.out.println(updatesUser);
        assertEquals("updates email address", "ONE@gmail.com", updatesUser.getEmail());
        assertEquals("updates name", "SAM", updatesUser.getFirstName());
    }


    @Test
    void removeUser() {
        if (userRepository.existsById(1L)) {
            userRepository.deleteById(1L);
        }

        assertFalse("User deleted ", userRepository.existsById(1L));
    }


}