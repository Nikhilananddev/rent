package com.nikhilanand.bookrent.app.repositories;

import com.nikhilanand.bookrent.app.global.Role;
import com.nikhilanand.bookrent.app.model.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {



    @Modifying
    @Transactional
    @Query("UPDATE UserEntity u SET u.role= ?1 WHERE u.id=?2")
    int updateUserRole(Role role, Long id);


    @Query("SELECT COUNT(r) FROM RentalEntity r WHERE r.user.id = :userId AND r.status = 'ACTIVE'")
    int countActiveRentalsByUserId(@Param("userId") Long userId);

    UserEntity findByEmail(String email);

    boolean existsByEmail(String email);

}
