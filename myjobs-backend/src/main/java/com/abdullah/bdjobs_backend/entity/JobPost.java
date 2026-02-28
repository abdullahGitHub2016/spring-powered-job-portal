package com.abdullah.bdjobs_backend.entity;

import jakarta.persistence.*;
import lombok.*;

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

    private Double salary;
}