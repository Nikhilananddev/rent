package com.nikhilanand.bookrent.app.exchanges.request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.nikhilanand.bookrent.app.global.AvailabilityStatus;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
public class AddBookRequest {

    @JsonIgnoreProperties
    private Long id;

    private String title;
    private String author;
    private String genre;

    @Enumerated(EnumType.STRING)
    private AvailabilityStatus availabilityStatus;


//    private Set<RentalEntity> rentalEntitySet=new HashSet<>();


}
