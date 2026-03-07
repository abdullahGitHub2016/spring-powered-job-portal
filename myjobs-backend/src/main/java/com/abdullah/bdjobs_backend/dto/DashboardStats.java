package com.abdullah.bdjobs_backend.dto;

/**
 * DTO for parallelized dashboard statistics.
 */
public record DashboardStats(
        long totalUsers,
        long totalJobs,
        long totalApplications
) {}