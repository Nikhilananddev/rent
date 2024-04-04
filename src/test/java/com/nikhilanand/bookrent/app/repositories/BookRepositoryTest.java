package com.nikhilanand.bookrent.app.repositories;

import com.nikhilanand.bookrent.app.global.AvailabilityStatus;
import com.nikhilanand.bookrent.app.model.BookEntity;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.util.AssertionErrors.assertFalse;


@SpringBootTest
class BookRepositoryTest {

    @Autowired
    BookRepository bookRepository;


    @Test
    void addBook() {


        BookEntity bookEntity = BookEntity.builder()
                .title("Think")
                .author("Robert")
                .availabilityStatus(AvailabilityStatus.AVAILABLE)
                .genre("A")
                .build();


        BookEntity book = bookRepository.save(bookEntity);
        assertEquals("Think", book.getTitle());
        assertEquals(bookEntity, book, "save book");
        assertEquals("Robert", book.getAuthor());
        assertEquals("A", book.getGenre());


    }

    @Test
    void getBook() {

        Optional<BookEntity> optionalBookEntity = bookRepository.findById(1L);
        BookEntity book = new BookEntity();
        if (optionalBookEntity.isPresent())
            book = optionalBookEntity.get();

        assertEquals(1, book.getId());
        assertEquals("Think", book.getTitle());
        assertEquals("Robert", book.getAuthor());
    }

    @Test
    void updateBookDeatils() {

        Optional<BookEntity> optionalBookEntity = bookRepository.findById(1L);
        BookEntity bookEntity = new BookEntity();
        if (optionalBookEntity.isPresent()) {
            bookEntity = optionalBookEntity.get();
            bookEntity.setGenre("B");
            bookEntity.setAuthor("SAM");

        }

        BookEntity book = bookRepository.save(bookEntity);

        assertEquals("B", book.getGenre());
        assertEquals("SAM", book.getAuthor());

    }

    @Test
    void removeBook() {

        if (bookRepository.existsById(1L)) {
            bookRepository.deleteById(1L);
        }

        assertFalse("User deleted ", bookRepository.existsById(1L));
    }

    @Test
    void checkBookStatus() {

        Optional<BookEntity> optionalBookEntity = bookRepository.findById(2L);
        BookEntity book = new BookEntity();
        if (optionalBookEntity.isPresent()) {
            book = optionalBookEntity.get();
        }
        assertEquals(AvailabilityStatus.AVAILABLE, book.getAvailabilityStatus());

    }


    @Test
    void getAllBooks() {

        bookRepository.findByAvailabilityStatus(AvailabilityStatus.AVAILABLE).forEach(System.out::println);
    }

}