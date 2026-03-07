package com.abdullah.bdjobs_backend.service;

import com.abdullah.bdjobs_backend.repository.UserRepository;
import com.abdullah.bdjobs_backend.repository.JobRepository;
import com.abdullah.bdjobs_backend.repository.ApplicationRepository;
import com.abdullah.bdjobs_backend.dto.DashboardStats;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import java.util.concurrent.CompletableFuture;

@Service
public class DashboardService {

    private final UserRepository userRepository;
    private final JobRepository jobRepository;
    private final ApplicationRepository applicationRepository;

    public DashboardService(UserRepository userRepository,
                            JobRepository jobRepository,
                            ApplicationRepository applicationRepository) {
        this.userRepository = userRepository;
        this.jobRepository = jobRepository;
        this.applicationRepository = applicationRepository;
    }

    public CompletableFuture<DashboardStats> getStats() {
        // Parallel execution logic
        CompletableFuture<Long> users = CompletableFuture.supplyAsync(userRepository::count);
        CompletableFuture<Long> jobs = CompletableFuture.supplyAsync(jobRepository::count);
        CompletableFuture<Long> apps = CompletableFuture.supplyAsync(applicationRepository::count);
        return CompletableFuture.allOf(users, jobs, apps)
                .thenApply(v -> new DashboardStats(users.join(), jobs.join(), apps.join()));
    }

    @Async("dashboardExecutor")
    public void generatePdfReport(Long adminId) {
        // This runs in a separate background thread
        try {
            // Your PDF generation logic goes here (e.g., iText or OpenPDF)
            Thread.sleep(3000);
            System.out.println("Background processing complete for Admin: " + adminId);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}