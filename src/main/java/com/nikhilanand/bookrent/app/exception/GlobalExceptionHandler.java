package com.nikhilanand.bookrent.app.exception;

import com.nikhilanand.bookrent.app.exception.book.BookAlreadyReturnedException;
import com.nikhilanand.bookrent.app.exception.book.BookNotAvailableException;
import com.nikhilanand.bookrent.app.exception.book.BookNotFoundException;
import com.nikhilanand.bookrent.app.exception.rent.ActiveRentException;
import com.nikhilanand.bookrent.app.exception.rent.RentNotFoundException;
import com.nikhilanand.bookrent.app.exception.user.UserEmailIdAlreadyExistsException;
import com.nikhilanand.bookrent.app.exception.user.UserNotFound;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String, String> handleInvalidArgument(MethodArgumentNotValidException exception) {
        Map<String, String> errorMap = new HashMap<>();
        exception.getBindingResult().getFieldErrors().forEach(error ->
        {
            errorMap.put(error.getField(), error.getDefaultMessage());
        });
        return errorMap;
    }


    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(BookNotFoundException.class)
    public Map<String, String> handleBookNotFoundException(BookNotFoundException exception) {
        Map<String, String> errorMap = new HashMap<>();
        errorMap.put("message", exception.getMessage());
        return errorMap;
    }

    @ResponseStatus(HttpStatus.CONFLICT)
    @ExceptionHandler(UserEmailIdAlreadyExistsException.class)
    public Map<String, String> handleUserIdAlreadyExistException(UserEmailIdAlreadyExistsException exception) {
        Map<String, String> errorMap = new HashMap<>();
        errorMap.put("message", exception.getMessage());
        return errorMap;
    }

    @ExceptionHandler(AccessDeniedException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public Map<String, String> handleAccessDeniedException(AccessDeniedException exception) {
        Map<String, String> errorMap = new HashMap<>();
        errorMap.put("message", "Only admin can access: " + exception.getMessage());
        return errorMap;
    }

    @ExceptionHandler(ActiveRentException.class)
    @ResponseStatus(HttpStatus.NOT_ACCEPTABLE)
    public Map<String, String> handleActiveRentException(ActiveRentException exception) {
        Map<String, String> errorMap = new HashMap<>();
        errorMap.put("message", exception.getMessage());
        return errorMap;
    }

    @ExceptionHandler(UserNotFound.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public Map<String, String> handleUserNotFoundException(UserNotFound exception) {
        Map<String, String> errorMap = new HashMap<>();
        errorMap.put("message", exception.getMessage());
        return errorMap;
    }

    @ExceptionHandler(BookAlreadyReturnedException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Map<String, String> BookAlreadyReturnedExceptionException(BookAlreadyReturnedException exception) {
        Map<String, String> errorMap = new HashMap<>();
        errorMap.put("message", exception.getMessage());
        return errorMap;
    }

    @ExceptionHandler(BookNotAvailableException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public Map<String, String> handleBookNotAvailableException(BookNotAvailableException exception) {
        Map<String, String> errorMap = new HashMap<>();
        errorMap.put("message", exception.getMessage());
        return errorMap;
    }

    @ExceptionHandler(RentNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public Map<String, String> handleRentNotFoundException(RentNotFoundException exception) {
        Map<String, String> errorMap = new HashMap<>();
        errorMap.put("message", exception.getMessage());
        return errorMap;
    }

    @ExceptionHandler(UsernameNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public Map<String, String> handleUsernameNotFoundException(UsernameNotFoundException exception) {
        Map<String, String> errorMap = new HashMap<>();
        errorMap.put("message", exception.getMessage());
        return errorMap;
    }


}