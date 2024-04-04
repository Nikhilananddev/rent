package com.nikhilanand.bookrent.app.service;

import com.nikhilanand.bookrent.app.exchanges.request.AddBookRequest;
import com.nikhilanand.bookrent.app.exchanges.response.BookResponse;
import com.nikhilanand.bookrent.app.exchanges.response.GetAllBookResponse;

public interface BookService {

    GetAllBookResponse getAllBooks();

    GetAllBookResponse getAllAvailableBooks();

    BookResponse addBook(AddBookRequest addBookRequest);

    BookResponse updateBook(Long id, AddBookRequest addBookRequest);

    void deleteBook(Long id);
}
