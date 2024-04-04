package com.nikhilanand.bookrent.app.exception.user;

public class UserEmailIdAlreadyExistsException extends Exception {

    public UserEmailIdAlreadyExistsException(String message) {
        super(message);
    }
}
