package com.nikhilanand.bookrent.app.service.impl;

import com.nikhilanand.bookrent.app.dto.RentDTO;
import com.nikhilanand.bookrent.app.exception.book.BookAlreadyReturnedException;
import com.nikhilanand.bookrent.app.exception.book.BookNotAvailableException;
import com.nikhilanand.bookrent.app.exception.book.BookNotFoundException;
import com.nikhilanand.bookrent.app.exception.rent.ActiveRentException;
import com.nikhilanand.bookrent.app.exception.rent.RentNotFoundException;
import com.nikhilanand.bookrent.app.exception.user.UserNotFound;
import com.nikhilanand.bookrent.app.exchanges.response.RentResponse;
import com.nikhilanand.bookrent.app.global.AvailabilityStatus;
import com.nikhilanand.bookrent.app.global.RentStatus;
import com.nikhilanand.bookrent.app.model.BookEntity;
import com.nikhilanand.bookrent.app.model.RentalEntity;
import com.nikhilanand.bookrent.app.model.UserEntity;
import com.nikhilanand.bookrent.app.repositories.BookRepository;
import com.nikhilanand.bookrent.app.repositories.RentalRepository;
import com.nikhilanand.bookrent.app.repositories.UserRepository;
import com.nikhilanand.bookrent.app.service.RentService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Optional;

@Service
public class RentServiceImpl implements RentService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    BookRepository bookRepository;

    @Autowired
    private RentalRepository rentalRepository;
    @Autowired
    private ModelMapper modelMapper;

    // TODO: 04/04/24 remove print and space

    @Override
    public RentResponse addRent(Long bookId, Long userId) {


        Optional<BookEntity> optionalBookEntity = bookRepository.findById(bookId);
        BookEntity book = new BookEntity();
        boolean isAvailable = false;
        UserEntity user = new UserEntity();

        Optional<UserEntity> optionalUserEntity = userRepository.findById(userId);

        if (!optionalUserEntity.isPresent()) {
            try {
                throw new UserNotFound("User not found");
            } catch (UserNotFound e) {
                throw new RuntimeException(e);
            }
        }


        if (!optionalBookEntity.isPresent()) {
            try {
                throw new BookNotFoundException("Book not found");
            } catch (BookNotFoundException e) {
                throw new RuntimeException(e);
            }
        }


        user = optionalUserEntity.get();

        book = optionalBookEntity.get();

        if (!book.getAvailabilityStatus().equals(AvailabilityStatus.AVAILABLE)) {
            try {
                throw new BookNotAvailableException("Book is not available");
            } catch (BookNotAvailableException e) {
                throw new RuntimeException(e);
            }
        }


        int countActiveRentals = userRepository.countActiveRentalsByUserId(userId);

        if (countActiveRentals >= 2) {

            try {
                throw new ActiveRentException("user have already " + countActiveRentals + " active rents");
            } catch (ActiveRentException e) {
                throw new RuntimeException(e);
            }
        }

        System.out.println("user have already " + countActiveRentals + " active rents");


        book.setAvailabilityStatus(AvailabilityStatus.NOT_AVAILABLE);

        RentalEntity rental = RentalEntity.builder()
                .rentalDate(LocalDate.now())
                .user(user)
                .book(book)
                .status(RentStatus.ACTIVE)
                .build();


        bookRepository.save(book);


        rental = rentalRepository.save(rental);

        System.out.println(rental);


        return RentResponse.builder()
                .rent(modelMapper.map(rental, RentDTO.class))
                .build();
    }

    @Override
    public RentResponse returnBook(Long bookId, Long userId) {


        Optional<RentalEntity> optionalRentalEntity = rentalRepository.findByUserIdAndBookId(userId, bookId);
        Optional<BookEntity> optionalBookEntity = bookRepository.findById(bookId);
        BookEntity bookEntity = new BookEntity();


        RentalEntity rentalEntity = new RentalEntity();

        if (!optionalRentalEntity.isPresent()) {
            try {
                throw new RentNotFoundException("Rent not found");
            } catch (RentNotFoundException e) {
                throw new RuntimeException(e);
            }
        }


        if (!optionalBookEntity.isPresent()) {
            try {
                throw new BookNotFoundException("Book not found");
            } catch (BookNotFoundException e) {
                throw new RuntimeException(e);
            }
        }


        rentalEntity = optionalRentalEntity.get();
        bookEntity = optionalBookEntity.get();
        System.out.println("before update rentalEntity " + rentalEntity);
        boolean isReturn = rentalRepository.hasUserReturnedBook(userId, bookId);
        System.out.println("isReturn " + isReturn);

        System.out.println();

        if (isReturn) {
            try {
                throw new BookAlreadyReturnedException("Book is already returned");
            } catch (BookAlreadyReturnedException e) {
                throw new RuntimeException(e);
            }
        } else {
            // If the book is not returned, update rental details
            bookEntity.setAvailabilityStatus(AvailabilityStatus.AVAILABLE);
            bookEntity = bookRepository.save(bookEntity);
            rentalEntity.setReturnDate(LocalDate.now());
            rentalEntity.setBook(bookEntity);
            rentalEntity.setStatus(RentStatus.RETURNED);
            rentalEntity = rentalRepository.save(rentalEntity);
        }


        System.out.println("after update rentalEntity " + rentalEntity);

        return RentResponse.builder()
                .rent(modelMapper.map(rentalEntity, RentDTO.class))
                .build();

    }
}
