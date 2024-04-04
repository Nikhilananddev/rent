package com.nikhilanand.bookrent.app.service;

import com.nikhilanand.bookrent.app.exchanges.response.RentResponse;

public interface RentService {

    RentResponse addRent(Long bookId, Long userId) ;

    RentResponse returnBook(Long bookId, Long userId) ;

}
