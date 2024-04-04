package com.nikhilanand.bookrent.app.exception.book;

public class BookAlreadyReturnedException extends Exception {
    public BookAlreadyReturnedException(String message) {
        super(message);
    }
}
