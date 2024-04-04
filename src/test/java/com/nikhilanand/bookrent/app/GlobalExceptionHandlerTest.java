package com.nikhilanand.bookrent.app;


import com.nikhilanand.bookrent.app.exchanges.request.AddBookRequest;
import com.nikhilanand.bookrent.app.exchanges.request.AddUserRequest;
import com.nikhilanand.bookrent.app.global.Role;
import com.nikhilanand.bookrent.app.repositories.BookRepository;
import com.nikhilanand.bookrent.app.repositories.UserRepository;
import com.nikhilanand.bookrent.app.service.impl.BookServiceImpl;
import com.nikhilanand.bookrent.app.service.impl.UserEntityServiceImpl;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest
class GlobalExceptionHandlerTest {

    @Mock
    UserRepository userRepository;
    @Mock
    BookRepository bookRepository;

    @InjectMocks
    UserEntityServiceImpl userService;

    @InjectMocks
    BookServiceImpl bookService;


    @Test
    void handleUserNotFoundException() {

        when(userRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> {
            userService.getUserById(1L);
        });


    }


    @Test
    void handleUserEmailIdAlreadyExistException() {


        AddUserRequest addUserRequest = AddUserRequest.builder()
                .email("userId123@gmail.com")
                .firstName("A")
                .lastName("B")
                .role(Role.USER)
                .build();


        when(userRepository.save(any())).thenThrow(new RuntimeException("userId123@gmail.com userName is already exist"));


        assertThrows(RuntimeException.class, () -> {
            userService.createUser(addUserRequest);
        });


    }

    @Test
    void handleBookNotFoundException() {


        when(bookRepository.findById(any())).thenThrow(new RuntimeException("Book Not Found "));


        assertThrows(RuntimeException.class, () -> {
            bookRepository.findById(1L);
        });


    }

    @Test
    void handleBookAlreadyReturnedException() {


        AddBookRequest addBookRequest= AddBookRequest.builder()
                .title("A")
                .build();
        when(bookRepository.save(any())).thenThrow(new RuntimeException("AlreadyReturned"));


        assertThrows(RuntimeException.class, () -> {
            bookService.addBook(addBookRequest);
        });


    }


}