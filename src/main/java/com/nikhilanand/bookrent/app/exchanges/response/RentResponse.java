package com.nikhilanand.bookrent.app.exchanges.response;

import com.nikhilanand.bookrent.app.dto.RentDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RentResponse {
    private RentDTO rent;
}
