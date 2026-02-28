package com.abdullah.bdjobs_backend.controller;

import com.abdullah.bdjobs_backend.entity.JobApplication;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/applications")
@CrossOrigin(origins = "*")
public class ApplicationController {

    @PersistenceContext
    private EntityManager entityManager;

    @PostMapping
    @Transactional
    public JobApplication apply(@RequestBody JobApplication application) {
        entityManager.persist(application);
        return application;
    }
}