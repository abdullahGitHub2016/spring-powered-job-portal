package com.abdullah.bdjobs_backend.entity;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Embeddable
@Data
@NoArgsConstructor
@AllArgsConstructor
public class WorkExperience {
    private String company;
    private String position;
    private String duration;
    private String description;
}