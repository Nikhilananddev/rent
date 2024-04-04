package com.nikhilanand.bookrent.app.service.impl;

import com.nikhilanand.bookrent.app.dto.UserDTO;
import com.nikhilanand.bookrent.app.exception.rent.ActiveRentException;
import com.nikhilanand.bookrent.app.exception.user.UserEmailIdAlreadyExistsException;
import com.nikhilanand.bookrent.app.exception.user.UserNotFound;
import com.nikhilanand.bookrent.app.exchanges.request.AddUserRequest;
import com.nikhilanand.bookrent.app.exchanges.response.GetAllUserResponse;
import com.nikhilanand.bookrent.app.exchanges.response.UserResponse;
import com.nikhilanand.bookrent.app.model.RentalEntity;
import com.nikhilanand.bookrent.app.model.UserEntity;
import com.nikhilanand.bookrent.app.repositories.RentalRepository;
import com.nikhilanand.bookrent.app.repositories.UserRepository;
import com.nikhilanand.bookrent.app.service.UserEntityService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class UserEntityServiceImpl implements UserEntityService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    RentalRepository rentalRepository;
    @Autowired
    PasswordEncoder passwordEncoder;
    @Autowired
    private ModelMapper modelMapper;

    @Override
    public UserResponse createUser(AddUserRequest addUserRequest) {
        System.out.println("addUserRequest " + addUserRequest.toString());
        String encodedPassword = passwordEncoder.encode(addUserRequest.getPassword());

        boolean isUserEmailIdExist = userRepository.existsByEmail(addUserRequest.getEmail());

        if (isUserEmailIdExist) {
            try {
                throw new UserEmailIdAlreadyExistsException(addUserRequest.getEmail() + " email id is already exist");
            } catch (UserEmailIdAlreadyExistsException e) {
                throw new RuntimeException(e);
            }
        }

        UserEntity userEntity = UserEntity.builder()
                .firstName(addUserRequest.getFirstName())
                .lastName(addUserRequest.getLastName())
                .email(addUserRequest.getEmail())
                .password(encodedPassword)
                .role(addUserRequest.getRole())
                .build();

        UserEntity savedUser = userRepository.save(userEntity);

        UserResponse userResponse = UserResponse.builder()
                .userDetails(modelMapper.map(savedUser, UserDTO.class))
                .build();


        return userResponse;

    }

    @Override
    public UserResponse updateUser(Long userId, AddUserRequest addUserRequest) {
        System.out.println(addUserRequest.toString());
        System.out.println("encodedPassword " + addUserRequest.getPassword());
        String encodedPassword = passwordEncoder.encode(addUserRequest.getPassword());

        Optional<UserEntity> user = userRepository.findById(userId);

        if (!user.isPresent()) {
            try {
                throw new UserNotFound("User not found");
            } catch (UserNotFound e) {
                throw new RuntimeException(e);
            }
        }


        boolean isUserEmailIdExist = userRepository.existsByEmail(addUserRequest.getEmail());

        if (isUserEmailIdExist) {
            try {
                throw new UserEmailIdAlreadyExistsException(addUserRequest.getEmail() + " email id is already exist");
            } catch (UserEmailIdAlreadyExistsException e) {
                throw new RuntimeException(e);
            }
        }


        UserEntity userEntity = user.get();
        userEntity.setFirstName(addUserRequest.getFirstName());
        userEntity.setLastName(addUserRequest.getLastName());
        userEntity.setEmail(addUserRequest.getEmail());
        System.out.println("encodedPassword " + encodedPassword);
        userEntity.setPassword(encodedPassword);
        userEntity.setRole(addUserRequest.getRole());

        UserEntity savedUser = userRepository.save(userEntity);



        UserResponse userResponse = UserResponse.builder()
                .userDetails(modelMapper.map(savedUser, UserDTO.class))
                .build();


        return userResponse;

    }

    @Override
    public UserResponse getUserById(Long userId) {

        Optional<UserEntity> user = userRepository.findById(userId);

        System.out.println("UserTest "+ user);
        if (!user.isPresent()) {
            try {
                throw new UserNotFound("User not found");
            } catch (UserNotFound e) {
                throw new RuntimeException(e);
            }

        }

        System.out.println("UserTest2 "+ user);

        System.out.println("UserTest3 "+ UserResponse.builder()
                .userDetails(modelMapper.map(user, UserDTO.class))
                .build());

        return UserResponse.builder()
                .userDetails(modelMapper.map(user, UserDTO.class))
                .build();
    }


    @Override
    public GetAllUserResponse getAllUsers() {

        List<UserEntity> userEntities = userRepository.findAll();

        List<UserDTO> userDTOList = new ArrayList<>();
        for (UserEntity user : userEntities) {
            UserDTO userDTO = modelMapper.map(user, UserDTO.class);
            userDTOList.add(userDTO);
        }
        return GetAllUserResponse.builder()
                .users(userDTOList)
                .build();
    }

    @Override
    public void deleteUser(Long userId){


        Optional<UserEntity> userOptional = userRepository.findById(userId);
        if (userOptional.isPresent()) {
            UserEntity user = userOptional.get();
            Set<RentalEntity> rentals = user.getRentalEntities();

            // TODO: 04/04/24 complete it  
            if (!rentals.isEmpty()) {

                try {
                    throw new ActiveRentException("Cannot delete user with active rentals");
                } catch (ActiveRentException e) {
                    throw new RuntimeException(e);
                }
            }
            userRepository.delete(user);
        } else {
            try {
                throw new UserNotFound("User not found");
            } catch (UserNotFound e) {
                throw new RuntimeException(e);
            }
        }


    }
}
