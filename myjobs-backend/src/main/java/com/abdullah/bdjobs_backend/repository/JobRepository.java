package com.abdullah.bdjobs_backend.repository;

import com.abdullah.bdjobs_backend.entity.JobPost;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JobRepository extends JpaRepository<JobPost, Long> {
    // JpaRepository provides save(), findAll(), findById(), deleteById() automatically
}