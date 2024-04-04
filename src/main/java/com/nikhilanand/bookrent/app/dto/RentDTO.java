package com.nikhilanand.bookrent.app.dto;

import com.nikhilanand.bookrent.app.global.RentStatus;
import com.nikhilanand.bookrent.app.model.BookEntity;
import com.nikhilanand.bookrent.app.model.UserEntity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RentDTO {

    private Long id;

    private UserEntity user;

    private BookEntity book;

    @Enumerated(EnumType.STRING)
    private RentStatus status;

    private LocalDate rentalDate;

    private LocalDate returnDate;
}

