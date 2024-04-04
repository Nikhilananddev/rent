package com.nikhilanand.bookrent.app;

import com.nikhilanand.bookrent.app.global.Role;
import com.nikhilanand.bookrent.app.model.BookEntity;
import com.nikhilanand.bookrent.app.model.RentalEntity;
import com.nikhilanand.bookrent.app.model.UserEntity;
import com.nikhilanand.bookrent.app.repositories.BookRepository;
import com.nikhilanand.bookrent.app.repositories.RentalRepository;
import com.nikhilanand.bookrent.app.repositories.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.time.LocalDate;

@SpringBootApplication
public  class AppApplication  {

	@Autowired
	UserRepository userRepository;

	@Autowired
	RentalRepository rentalRepository;

	@Autowired
	BookRepository bookRepository;


	public static void main(String[] args) {
		SpringApplication.run(AppApplication.class, args);
	}

}
