package com.nikhilanand.bookrent.app.service.impl;

import com.nikhilanand.bookrent.app.exchanges.request.AddBookRequest;
import com.nikhilanand.bookrent.app.model.BookEntity;
import com.nikhilanand.bookrent.app.repositories.BookRepository;
import com.nikhilanand.bookrent.app.service.BookService;
import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.modelmapper.ModelMapper;
import org.springframework.boot.test.context.SpringBootTest;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


@SpringBootTest
class BookServiceImplTest {

    @Mock
    private BookRepository bookRepository;

    @InjectMocks
    private BookServiceImpl bookService;
    @Mock
    private ModelMapper mapper;
    @Before
    public void setup() {
        mapper = new ModelMapper();
    }
    @Test
    void addBookTest() {


        AddBookRequest addBookRequest = AddBookRequest.builder()
                .id(1L)
                .title("1984")
                .author("George Orwell")
                .genre("Dystopian")
                .build();

        bookService.addBook(addBookRequest);

    }
    @Test
    void updateBook() {
    }
}