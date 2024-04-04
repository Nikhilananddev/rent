package com.nikhilanand.bookrent.app.exchanges.request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.nikhilanand.bookrent.app.global.Role;
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
public class AddUserRequest {

    @JsonIgnoreProperties
    private Long id;

    private String firstName;
    private String lastName;

    private String email;

    private String password;

    @Enumerated(EnumType.STRING)
    @Builder.Default
    private Role role = Role.USER;
}
