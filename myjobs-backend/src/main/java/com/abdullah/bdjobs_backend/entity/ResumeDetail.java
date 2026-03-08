package com.abdullah.bdjobs_backend.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "resume_details")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ResumeDetail {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_id", unique = true, nullable = false)
    private Long userId;

    private String fullName;
    private String professionalTitle;
    private String phone;

    @Column(columnDefinition = "TEXT")
    private String summary;

    // Inside ResumeDetail.java

    @ElementCollection(fetch = FetchType.EAGER) // Added EAGER
    @CollectionTable(name = "resume_skills", joinColumns = @JoinColumn(name = "resume_id"))
    @Column(name = "skill_name")
    private List<String> skills = new ArrayList<>();

    @ElementCollection(fetch = FetchType.EAGER) // Added EAGER
    @CollectionTable(name = "resume_experience", joinColumns = @JoinColumn(name = "resume_id"))
    private List<WorkExperience> experiences = new ArrayList<>();
}