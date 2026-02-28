package com.abdullah.bdjobs_backend.controller;

import com.abdullah.bdjobs_backend.entity.JobApplication;
import com.abdullah.bdjobs_backend.repository.ApplicationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/applications")
@CrossOrigin(origins = "*")
public class ApplicationController {

    @Autowired
    private ApplicationRepository applicationRepository;

    // 1. POST: Submit an application (Used by Job Seekers)
    // URL: http://localhost:8080/api/applications
    @PostMapping
    public JobApplication apply(@RequestBody JobApplication application) {
        System.out.println("Received new application for job: " + application.getJobTitle());
        return applicationRepository.save(application);
    }

    // 2. GET: View all applications (Used by Employers/Admin)
    // URL: http://localhost:8080/api/applications
    @GetMapping
    public List<JobApplication> getAllApplications() {
        System.out.println("Fetching all applications...");
        return applicationRepository.findAll();
    }
}