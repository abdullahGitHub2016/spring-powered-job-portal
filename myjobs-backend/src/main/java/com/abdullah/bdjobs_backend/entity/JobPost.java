package com.abdullah.bdjobs_backend.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate; // Import this for the deadline

@Entity
@Table(name = "job_posts")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class JobPost {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private String companyName;
    private String location;

    @Column(length = 1000)
    private String description;

    // CHANGED: Double to String to handle "Negotiable" or "80,000 BDT"
    private String salary;

    // NEW: Added to match the BDJobs data structure
    private String experience;

    // NEW: Added for the application deadline
    private LocalDate deadline;
}