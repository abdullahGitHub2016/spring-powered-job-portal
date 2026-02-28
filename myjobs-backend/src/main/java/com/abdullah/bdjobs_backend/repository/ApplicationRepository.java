package com.abdullah.bdjobs_backend.repository;

import com.abdullah.bdjobs_backend.entity.JobApplication;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ApplicationRepository extends JpaRepository<JobApplication, Long> {
}