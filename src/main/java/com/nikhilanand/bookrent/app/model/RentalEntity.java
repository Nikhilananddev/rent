package com.nikhilanand.bookrent.app.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.nikhilanand.bookrent.app.global.RentStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "Rental")
public class RentalEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @ManyToOne(fetch = FetchType.EAGER)
    @JoinTable(
            name = "Rental_User",
            joinColumns = @JoinColumn(name = "rental_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id")
    )
    @JsonBackReference
    private UserEntity user;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinTable(
            name = "Rental_Book",
            joinColumns = @JoinColumn(name = "rental_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "book_id", referencedColumnName = "id")
    )
    @JsonBackReference
    private BookEntity book;

    @Enumerated(EnumType.STRING)
    private RentStatus status;

    private LocalDate rentalDate;

    private LocalDate returnDate;
}
