package com.abdullah.bdjobs_backend.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "job_applications")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class JobApplication {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long jobId;
    private String jobTitle;
    private String applicantName;
    private String applicantEmail;
    private String applicantPhone;

    @Column(length = 2000)
    private String coverLetter;

    private LocalDateTime appliedAt = LocalDateTime.now();
}