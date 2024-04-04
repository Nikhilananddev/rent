package com.nikhilanand.bookrent.app.exchanges.response;

import com.nikhilanand.bookrent.app.dto.BookDTO;
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
public class GetAllBookResponse {
    @Builder.Default
    List<BookDTO> books = new ArrayList<>();
}
