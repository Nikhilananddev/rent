package com.nikhilanand.bookrent.app.exchanges.request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.nikhilanand.bookrent.app.global.AvailabilityStatus;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import jakarta.validation.constraints.NotBlank;
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
public class AddBookRequest {

    @JsonIgnoreProperties
    private Long id;
    @NotBlank(message = "Title is required")
    private String title;

    @NotBlank(message = "Author is required")
    private String author;

    @NotBlank(message = "Genre is required")
    private String genre;
    @Enumerated(EnumType.STRING)
    private AvailabilityStatus availabilityStatus;



}
