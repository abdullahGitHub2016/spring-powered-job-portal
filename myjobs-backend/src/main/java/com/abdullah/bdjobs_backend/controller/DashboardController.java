package com.abdullah.bdjobs_backend.controller;

import com.abdullah.bdjobs_backend.service.DashboardService;
import com.abdullah.bdjobs_backend.dto.DashboardStats;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping("/api/admin/dashboard")
public class DashboardController {

    private final DashboardService dashboardService;

    public DashboardController(DashboardService dashboardService) {
        this.dashboardService = dashboardService;
    }

    @GetMapping("/stats")
    public CompletableFuture<ResponseEntity<DashboardStats>> getDashboardStats() {
        // 1. Capture the context from the main thread (exec-10)
        var context = SecurityContextHolder.getContext();

        return dashboardService.getStats()
                .thenApply(stats -> {
                    // 2. Manually restore the context before returning the response
                    SecurityContextHolder.setContext(context);
                    return ResponseEntity.ok(stats);
                });
    }
}