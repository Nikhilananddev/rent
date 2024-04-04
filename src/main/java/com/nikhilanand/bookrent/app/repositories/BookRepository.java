package com.nikhilanand.bookrent.app.repositories;

import com.nikhilanand.bookrent.app.global.AvailabilityStatus;
import com.nikhilanand.bookrent.app.model.BookEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookRepository extends JpaRepository<BookEntity, Long> {

    List<BookEntity> findByAvailabilityStatus(AvailabilityStatus availabilityStatus);
}
