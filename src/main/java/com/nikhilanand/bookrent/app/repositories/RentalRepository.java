package com.nikhilanand.bookrent.app.repositories;

import com.nikhilanand.bookrent.app.model.RentalEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RentalRepository extends JpaRepository<RentalEntity, Long> {

//    @Query("SELECT COUNT(r) FROM RentalEntity r " +
//            "WHERE r.user.id = :userId AND r.rentStatus = 'ACTIVE'")
//    int countActiveRentalsByUserId(@Param("userId") Long userId);

    boolean existsByBookIdAndUserIdAndReturnDateIsNull(Long bookId, Long userId);


    @Query("SELECT COUNT(r) > 0 FROM RentalEntity r WHERE r.user.id = :userId AND r.book.id = :bookId AND r.returnDate IS NOT NULL")
    boolean hasUserReturnedBook(@Param("userId") Long userId, @Param("bookId") Long bookId);

    @Query("SELECT r FROM RentalEntity r WHERE r.user.id = :userId")
    List<RentalEntity> findAllByUserId(@Param("userId") Long userId);


    @Query("SELECT r.book  FROM RentalEntity r WHERE r.user.id = :userId AND r.book.id = :bookId")
    List<Object[]> findAllByUserIdAndBookId(@Param("userId") Long userId, @Param("bookId") Long bookId);

    Optional<RentalEntity> findByUserIdAndBookId(Long userId, Long bookId);
}
