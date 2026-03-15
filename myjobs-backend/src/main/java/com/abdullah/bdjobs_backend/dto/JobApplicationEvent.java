package com.abdullah.bdjobs_backend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class JobApplicationEvent {
    private Long jobId;
    private String jobTitle;
    private String applicantEmail;
    private String applicantName;
    private String companyName;
}