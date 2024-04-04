package com.nikhilanand.bookrent.app.service.impl;

import com.nikhilanand.bookrent.app.dto.UserDTO;
import com.nikhilanand.bookrent.app.exchanges.request.AddUserRequest;
import com.nikhilanand.bookrent.app.exchanges.response.UserResponse;
import com.nikhilanand.bookrent.app.global.Role;
import com.nikhilanand.bookrent.app.model.UserEntity;
import com.nikhilanand.bookrent.app.repositories.UserRepository;
import com.nikhilanand.bookrent.app.service.UserEntityService;
import org.junit.Before;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest
class UserServiceImplTest {
    @Mock
    UserRepository userRepository;

    @Mock
    private ModelMapper modelMapper;

    @InjectMocks
    UserEntityServiceImpl userEntityService;

    UserEntity userEntity = new UserEntity();

    @Mock
    private ModelMapper mapper;

    @Before
    public void setup1() {
        mapper = new ModelMapper();
    }
    @BeforeEach
    public void setup() {
//        // Initialize Mockito annotated components
//        MockitoAnnotations.initMocks(this);
//
//
//        modelMapper = new ModelMapper();

    }
    @BeforeEach
    void beforeAll() {
        userEntity.setId(1L);
        userEntity.setFirstName("John");
        userEntity.setLastName("Doe");
        userEntity.setEmail("john.doe@example.com");
        userEntity.setRole(Role.USER);
    }

    @Test
    void getUser() throws Exception {

        when(userRepository.findById(1L)).thenReturn(Optional.of(userEntity));
        UserResponse getUserResponse = userEntityService.getUserById(1L);

        System.out.println("userService "+userEntityService.getUserById(1L));

        UserDTO user = getUserResponse.getUserDetails();

        assertEquals("John", user.getFirstName());
        assertEquals("Doe", user.getLastName());
        assertEquals("john.doe@example.com", user.getEmail());
        assertEquals("USER", user.getRole().name());

    }

    @Test
    void updateUser() {

        when(userRepository.findById(1L)).thenReturn(Optional.of(userEntity));
        UserResponse getUserResponse = userEntityService.getUserById(1L);
        UserDTO user = getUserResponse.getUserDetails();

        assertEquals("USER", user.getRole().name());

        //after  update
        userEntity.setRole(Role.ADMIN);
        when(userRepository.save(any())).thenReturn(userEntity);

        AddUserRequest addUserRequest = AddUserRequest.builder()
                .firstName("John")
                .lastName("Doe")
                .email("john.doe@example.com")
                .role(Role.ADMIN)
                .build();

        getUserResponse = userEntityService.updateUser(1L, addUserRequest);
        user = getUserResponse.getUserDetails();
        assertEquals("ADMIN", user.getRole().name());

    }
}