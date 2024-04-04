package com.nikhilanand.bookrent.app.exchanges.response;

import com.nikhilanand.bookrent.app.dto.BookDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BookResponse {
    private BookDTO book;
}
