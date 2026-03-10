package com.abdullah.bdjobs_backend.service;

import com.abdullah.bdjobs_backend.repository.UserRepository;
import com.abdullah.bdjobs_backend.repository.JobRepository;
import com.abdullah.bdjobs_backend.repository.ApplicationRepository;
import com.abdullah.bdjobs_backend.dto.DashboardStats;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;

@Service
public class DashboardService {

    private final UserRepository userRepository;
    private final JobRepository jobRepository;
    private final ApplicationRepository applicationRepository;
    private final Executor taskExecutor;

    public DashboardService(UserRepository userRepository,
                            JobRepository jobRepository,
                            ApplicationRepository applicationRepository,
                            @Qualifier("taskExecutor") Executor taskExecutor) {
        this.userRepository = userRepository;
        this.jobRepository = jobRepository;
        this.applicationRepository = applicationRepository;
        this.taskExecutor = taskExecutor;
    }

    public CompletableFuture<DashboardStats> getStats() {
        // Use taskExecutor for every count query
        CompletableFuture<Long> users = CompletableFuture.supplyAsync(userRepository::count, taskExecutor);
        CompletableFuture<Long> jobs = CompletableFuture.supplyAsync(jobRepository::count, taskExecutor);
        CompletableFuture<Long> apps = CompletableFuture.supplyAsync(applicationRepository::count, taskExecutor);

        return CompletableFuture.allOf(users, jobs, apps)
                .thenApplyAsync(v -> {
                    // This block now safely retains ROLE_EMPLOYER
                    return new DashboardStats(users.join(), jobs.join(), apps.join());
                }, taskExecutor); // MUST use taskExecutor here too
    }
}