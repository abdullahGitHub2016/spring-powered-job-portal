package com.abdullah.bdjobs_backend.repository;

import com.abdullah.bdjobs_backend.entity.ResumeDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface ResumeRepository extends JpaRepository<ResumeDetail, Long> {
    // This MUST return Optional so we can use .map() or .orElse()
    Optional<ResumeDetail> findByUserId(Long userId);
}