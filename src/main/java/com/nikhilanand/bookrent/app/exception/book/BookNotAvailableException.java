package com.nikhilanand.bookrent.app.exception.book;

public class BookNotAvailableException extends Exception {
    public BookNotAvailableException(String message) {
        super(message);
    }
}
