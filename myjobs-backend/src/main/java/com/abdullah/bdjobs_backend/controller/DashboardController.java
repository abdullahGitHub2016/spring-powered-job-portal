package com.abdullah.bdjobs_backend.controller;

import com.abdullah.bdjobs_backend.service.DashboardService;
import com.abdullah.bdjobs_backend.dto.DashboardStats;
import org.springframework.http.ResponseEntity;
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
        return dashboardService.getStats()
                .thenApply(ResponseEntity::ok);
    }

    @PostMapping("/report/{id}")
    public ResponseEntity<String> startReport(@PathVariable Long id) {
        dashboardService.generatePdfReport(id);
        return ResponseEntity.accepted().body("PDF generation thread started...");
    }
}