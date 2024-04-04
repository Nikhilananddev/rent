package com.nikhilanand.bookrent.app.exchanges.response;

import com.nikhilanand.bookrent.app.dto.UserDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserResponse {

    private UserDTO userDetails;

}
