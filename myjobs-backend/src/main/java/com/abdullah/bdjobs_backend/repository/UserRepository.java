package com.abdullah.bdjobs_backend.repository;

import com.abdullah.bdjobs_backend.entity.User; // Updated to match your entity package
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
}