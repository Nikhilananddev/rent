package com.nikhilanand.bookrent.app.service.impl;

import com.nikhilanand.bookrent.app.dto.BookDTO;
import com.nikhilanand.bookrent.app.exception.book.BookNotFoundException;
import com.nikhilanand.bookrent.app.exchanges.request.AddBookRequest;
import com.nikhilanand.bookrent.app.exchanges.response.BookResponse;
import com.nikhilanand.bookrent.app.exchanges.response.GetAllBookResponse;
import com.nikhilanand.bookrent.app.global.AvailabilityStatus;
import com.nikhilanand.bookrent.app.model.BookEntity;
import com.nikhilanand.bookrent.app.repositories.BookRepository;
import com.nikhilanand.bookrent.app.service.BookService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class BookServiceImpl implements BookService {


    @Autowired
    BookRepository bookRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public GetAllBookResponse getAllBooks() {
        List<BookEntity> books = bookRepository.findAll();


        List<BookDTO> bookDTOList = new ArrayList<>();
        for (BookEntity book : books) {
            BookDTO bookDTO = modelMapper.map(book, BookDTO.class);
            bookDTOList.add(bookDTO);
        }

        GetAllBookResponse getAllBookResponse = GetAllBookResponse.builder()
                .books(bookDTOList)
                .build();

        return getAllBookResponse;

    }

    @Override
    public GetAllBookResponse getAllAvailableBooks() {

        List<BookEntity> books = bookRepository.findByAvailabilityStatus(AvailabilityStatus.AVAILABLE);


        List<BookDTO> bookDTOList = new ArrayList<>();
        for (BookEntity book : books) {
            BookDTO bookDTO = modelMapper.map(book, BookDTO.class);
            bookDTOList.add(bookDTO);
        }

        GetAllBookResponse getAllBookResponse = GetAllBookResponse.builder()
                .books(bookDTOList)
                .build();

        return getAllBookResponse;
    }


    @Override
    public BookResponse addBook(AddBookRequest addBookRequest) {

        BookEntity bookEntity = BookEntity.builder()
                .title(addBookRequest.getTitle())
                .author(addBookRequest.getAuthor())
                .availabilityStatus(addBookRequest.getAvailabilityStatus())
                .genre(addBookRequest.getGenre())
                .build();

        bookEntity = bookRepository.save(bookEntity);

        BookResponse bookResponse = BookResponse.builder()
                .book(modelMapper.map(bookEntity, BookDTO.class))
                .build();
        return bookResponse;
    }

    @Override
    public BookResponse updateBook(Long id, AddBookRequest addBookRequest) {

        Optional<BookEntity> optionalBookEntity = bookRepository.findById(id);
        BookEntity bookEntity = new BookEntity();
        if (optionalBookEntity.isPresent()) {
            bookEntity = optionalBookEntity.get();
            bookEntity.setTitle(addBookRequest.getTitle());
            bookEntity.setAuthor(addBookRequest.getAuthor());
            bookEntity.setAvailabilityStatus(addBookRequest.getAvailabilityStatus());
            bookEntity.setGenre(addBookRequest.getGenre());
            bookEntity = bookRepository.save(bookEntity);

        } else {
            try {
                throw new BookNotFoundException("Book not found");
            } catch (BookNotFoundException e) {
                throw new RuntimeException(e);
            }
        }


        BookResponse bookResponse = BookResponse.builder()
                .book(modelMapper.map(bookEntity, BookDTO.class))
                .build();
        return bookResponse;
    }

    @Override
    public void deleteBook(Long id) {

        Optional<BookEntity> optionalBookEntity = bookRepository.findById(id);

        if (!optionalBookEntity.isPresent()) {
            try {
                throw new BookNotFoundException("Book not found");
            } catch (BookNotFoundException e) {
                throw new RuntimeException(e);
            }
        }

        bookRepository.deleteById(id);

    }
}
