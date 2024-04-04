package com.nikhilanand.bookrent.app.controller;

import com.nikhilanand.bookrent.app.exchanges.request.AddBookRequest;
import com.nikhilanand.bookrent.app.exchanges.request.AddRentRequest;
import com.nikhilanand.bookrent.app.exchanges.response.BookResponse;
import com.nikhilanand.bookrent.app.exchanges.response.GetAllBookResponse;
import com.nikhilanand.bookrent.app.exchanges.response.RentResponse;
import com.nikhilanand.bookrent.app.service.BookService;
import com.nikhilanand.bookrent.app.service.RentService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/books")
public class BookController {

    @Autowired
    private BookService bookService;

    @Autowired
    RentService rentService;
    @GetMapping("/getAll")
    public ResponseEntity<GetAllBookResponse> getAllBooks() {
        GetAllBookResponse books = bookService.getAllBooks();
        if (books != null) {
            return ResponseEntity.ok().body(books);
        }

        return ResponseEntity.badRequest().body(null);
    }

    @GetMapping("/available")
    public ResponseEntity<GetAllBookResponse> getAllAvailableBooks() {
        GetAllBookResponse books = bookService.getAllAvailableBooks();
        if (books != null)
            return ResponseEntity.ok().body(books);

        return ResponseEntity.badRequest().body(null);
    }

    @PostMapping("/add")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<BookResponse> addBook(@Valid @RequestBody AddBookRequest addBookRequest) {
        BookResponse newBook = bookService.addBook(addBookRequest);
        if (newBook != null)
            return ResponseEntity.status(HttpStatus.CREATED).body(newBook);

        return ResponseEntity.badRequest().body(null);
    }

    @PutMapping("/update/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<BookResponse> updateBook(@PathVariable Long id,@Valid @RequestBody AddBookRequest addBookRequest) {
        BookResponse updatedBook = bookService.updateBook(id, addBookRequest);

        if (updatedBook != null)
            return ResponseEntity.ok().body(updatedBook);

        return ResponseEntity.badRequest().body(null);
    }

    @DeleteMapping("/remove/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<?> deleteBook(@PathVariable Long id) {
            bookService.deleteBook(id);
        return ResponseEntity.ok().body("Book have been deleted");
    }

    @PostMapping("/{bookId}/rent")
    ResponseEntity<RentResponse> rentBook(@PathVariable("bookId") Long bookId,@Valid @RequestBody AddRentRequest addRentRequest) {

        RentResponse rentalEntity = rentService.addRent(bookId, addRentRequest.getUserId());
        if (rentalEntity != null)
            return ResponseEntity.status(HttpStatus.CREATED).body(rentalEntity);



        return ResponseEntity.badRequest().body(null);
    }

    @PostMapping("/{bookId}/return")
    ResponseEntity<RentResponse> returnBook(@PathVariable("bookId") Long bookId,@Valid @RequestBody AddRentRequest addRentRequest) {


        RentResponse rentalEntity = rentService.returnBook(bookId, addRentRequest.getUserId());
        if (rentalEntity != null)
            return ResponseEntity.status(HttpStatus.CREATED).body(rentalEntity);


        return ResponseEntity.badRequest().body(null);
    }


}
