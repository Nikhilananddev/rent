package com.nikhilanand.bookrent.app.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
public class UserDTO {


    private Long id;
    private String firstName;
    private String lastName;

    private String email;

    @JsonIgnore
    private String password;

    @Enumerated(EnumType.STRING)
    @Builder.Default
    private Role role = Role.USER;


}
