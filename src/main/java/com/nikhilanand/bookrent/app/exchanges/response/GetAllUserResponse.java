package com.nikhilanand.bookrent.app.exchanges.response;

import com.nikhilanand.bookrent.app.dto.UserDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class GetAllUserResponse {

    @Builder.Default
    List<UserDTO> users = new ArrayList<>();
}
