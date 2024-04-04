package com.nikhilanand.bookrent.app.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.nikhilanand.bookrent.app.global.AvailabilityStatus;
import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(exclude = "rentalEntitySet")
@Builder
@Entity
@Table(name = "Books")
public class BookEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private String author;
    private String genre;

    @Enumerated(EnumType.STRING)
    private AvailabilityStatus availabilityStatus;

    @OneToMany(mappedBy = "book", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @Builder.Default
    @ToString.Exclude
    @JsonManagedReference
    private Set<RentalEntity> rentalEntitySet = new HashSet<>();
}
