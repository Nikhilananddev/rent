package com.nikhilanand.bookrent.app.dto;

import com.nikhilanand.bookrent.app.global.AvailabilityStatus;
import com.nikhilanand.bookrent.app.model.RentalEntity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BookDTO {

    private Long id;
    private String title;
    private String author;
    private String genre;

    @Enumerated(EnumType.STRING)
    private AvailabilityStatus availabilityStatus;
    @Builder.Default
    private Set<RentalEntity> rentalEntitySet = new HashSet<>();
}
