package com.nikhilanand.bookrent.app.service;

import com.nikhilanand.bookrent.app.exchanges.request.AddUserRequest;
import com.nikhilanand.bookrent.app.exchanges.response.GetAllUserResponse;
import com.nikhilanand.bookrent.app.exchanges.response.UserResponse;

public interface UserEntityService {

    UserResponse createUser(AddUserRequest addUserRequest);

    UserResponse updateUser(Long userId, AddUserRequest addUserRequest);

    UserResponse getUserById(Long userId);

    GetAllUserResponse getAllUsers();

    void deleteUser(Long userId);
}
