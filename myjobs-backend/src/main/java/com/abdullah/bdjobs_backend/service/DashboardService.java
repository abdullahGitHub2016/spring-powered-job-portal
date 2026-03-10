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
        // 1. Log the count for Users
        CompletableFuture<Long> users = CompletableFuture.supplyAsync(() -> {
            System.out.println("QUERY 1 (Users) - Thread: " + Thread.currentThread().getName());
            return userRepository.count();
        }, taskExecutor);

        // 2. Log the count for Jobs
        CompletableFuture<Long> jobs = CompletableFuture.supplyAsync(() -> {
            System.out.println("QUERY 2 (Jobs) - Thread: " + Thread.currentThread().getName());
            return jobRepository.count();
        }, taskExecutor);

        // 3. Log the count for Applications
        CompletableFuture<Long> apps = CompletableFuture.supplyAsync(() -> {
            System.out.println("QUERY 3 (Apps) - Thread: " + Thread.currentThread().getName());
            return applicationRepository.count();
        }, taskExecutor);

        return CompletableFuture.allOf(users, jobs, apps)
                .thenApplyAsync(v -> {
                    System.out.println("COMBINING RESULTS - Thread: " + Thread.currentThread().getName());
                    return new DashboardStats(users.join(), jobs.join(), apps.join());
                }, taskExecutor);
    }
}