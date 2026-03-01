package com.abdullah.bdjobs_backend.repository;

import com.abdullah.bdjobs_backend.entity.JobPost;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JobRepository extends JpaRepository<JobPost, Long> {
    // This interface now has save(), findAll(), findById(), and delete() automatically!
}