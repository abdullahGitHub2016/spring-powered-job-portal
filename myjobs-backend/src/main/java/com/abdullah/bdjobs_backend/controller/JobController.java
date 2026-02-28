package com.abdullah.bdjobs_backend.controller;

import com.abdullah.bdjobs_backend.entity.JobPost;
import com.abdullah.bdjobs_backend.repository.JobRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/jobs")
@CrossOrigin(origins = "*")
public class JobController {

    @Autowired
    private JobRepository jobRepository;

    // 1. GET ALL
    @GetMapping
    public List<JobPost> getAllJobs() {
        return jobRepository.findAll();
    }

    // 2. GET BY ID
    @GetMapping("/{id}")
    public ResponseEntity<JobPost> getJobById(@PathVariable Long id) {
        return jobRepository.findById(id)
                .map(job -> ResponseEntity.ok().body(job))
                .orElse(ResponseEntity.notFound().build());
    }

    // 3. CREATE
    @PostMapping
    public JobPost createJob(@RequestBody JobPost job) {
        return jobRepository.save(job);
    }

    // 4. UPDATE
    @PutMapping("/{id}")
    public ResponseEntity<JobPost> updateJob(@PathVariable Long id, @RequestBody JobPost jobDetails) {
        return jobRepository.findById(id)
                .map(existingJob -> {
                    existingJob.setTitle(jobDetails.getTitle());
                    existingJob.setCompanyName(jobDetails.getCompanyName());
                    existingJob.setLocation(jobDetails.getLocation());
                    existingJob.setDescription(jobDetails.getDescription());
                    existingJob.setSalary(jobDetails.getSalary());

                    JobPost updatedJob = jobRepository.save(existingJob);
                    return ResponseEntity.ok(updatedJob);
                })
                .orElse(ResponseEntity.notFound().build());
    }

    // 5. DELETE
    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteJob(@PathVariable Long id) {
        return jobRepository.findById(id)
                .map(job -> {
                    jobRepository.delete(job);
                    return ResponseEntity.noContent().build();
                })
                .orElse(ResponseEntity.notFound().build());
    }
}