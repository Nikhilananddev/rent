package com.nikhilanand.bookrent.app.repositories;

import com.nikhilanand.bookrent.app.global.AvailabilityStatus;
import com.nikhilanand.bookrent.app.global.RentStatus;
import com.nikhilanand.bookrent.app.model.BookEntity;
import com.nikhilanand.bookrent.app.model.RentalEntity;
import com.nikhilanand.bookrent.app.model.UserEntity;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.Optional;

@SpringBootTest
class RentalRepositoryTest {


    @Autowired
    UserRepository userRepository;

    @Autowired
    BookRepository bookRepository;

    @Autowired
    private RentalRepository rentalRepository;

    @Test
    void addRent() {


        Optional<UserEntity> user = userRepository.findById(1L);

        System.out.println(user);


        BookEntity book = new BookEntity();
        book.setTitle("Googs Book");
        book.setAuthor("Johdsn Author");
        book.setGenre("Fictiddon");
        book.setAvailabilityStatus(AvailabilityStatus.AVAILABLE); // Assuming it's available initially

        book = bookRepository.save(book);

        RentalEntity rental = new RentalEntity();
        rental.setUser(user.get()); // Set user
        rental.setBook(book); // Set book
        rental.setStatus(RentStatus.ACTIVE);
        rental.setRentalDate(LocalDate.now()); // Set rental date

        rentalRepository.save(rental);

    }

    @Test
    void rentBook() throws Exception {

        Optional<BookEntity> optionalBookEntity = bookRepository.findById(2L);
        BookEntity book = new BookEntity();
        boolean isAvailable = false;
        UserEntity user = new UserEntity();

        Optional<UserEntity> optionalUserEntity = userRepository.findById(1L);

        if (!optionalUserEntity.isPresent())
            throw new Exception("User not found");

        if (!optionalBookEntity.isPresent())
            throw new Exception("Book not found");


        user = optionalUserEntity.get();
//
        book = optionalBookEntity.get();
//
        if (!book.getAvailabilityStatus().equals(AvailabilityStatus.AVAILABLE))
            throw new Exception("Book is not available");


        int countActiveRentals = userRepository.countActiveRentalsByUserId(1L);
        System.out.println("user have already " + countActiveRentals + " active rents");


        book.setAvailabilityStatus(AvailabilityStatus.NOT_AVAILABLE);


        RentalEntity rental = RentalEntity.builder()
                .rentalDate(LocalDate.now())
                .user(user)
                .book(book)
                .status(RentStatus.ACTIVE)
                .build();


        bookRepository.save(book);

        RentalEntity rental1 = rentalRepository.save(rental);

        System.out.println(rental1);

    }

    @Test
    void reaturnBook() throws Exception {


    }


    @Test
    void getAllRent() throws Exception {


        Long userId = 1L;
        Long bookId = 4L;


        Optional<RentalEntity> optionalRentalEntity = rentalRepository.findByUserIdAndBookId(userId, bookId);
        Optional<BookEntity> optionalBookEntity = bookRepository.findById(bookId);
        BookEntity bookEntity = new BookEntity();


        RentalEntity rentalEntity = new RentalEntity();

        if (!optionalRentalEntity.isPresent())
            throw new Exception("Rent not found");


        if (!optionalBookEntity.isPresent())
            throw new Exception("Book not found");


        rentalEntity = optionalRentalEntity.get();
        bookEntity = optionalBookEntity.get();
        System.out.println("before update rentalEntity " + rentalEntity);
        boolean isReturn = rentalRepository.hasUserReturnedBook(userId, bookId);
        System.out.println("isReturn " + isReturn);

        System.out.println();

        if (isReturn) {
            throw new Exception("Book is already returned");
        } else {
            // If the book is not returned, update rental details
            bookEntity.setAvailabilityStatus(AvailabilityStatus.AVAILABLE);
            bookEntity = bookRepository.save(bookEntity);
            System.out.println("Hello");
            rentalEntity.setReturnDate(LocalDate.now());
            rentalEntity.setBook(bookEntity);
            rentalEntity.setStatus(RentStatus.RETURNED);
            rentalEntity = rentalRepository.save(rentalEntity);
        }


        System.out.println("after update rentalEntity " + rentalEntity);

    }
}