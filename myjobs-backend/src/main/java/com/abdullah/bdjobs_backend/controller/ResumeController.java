package com.abdullah.bdjobs_backend.controller;

import com.abdullah.bdjobs_backend.dto.ErrorResponse;
import com.abdullah.bdjobs_backend.entity.ResumeDetail;
import com.abdullah.bdjobs_backend.exception.UserNotFoundException;
import com.abdullah.bdjobs_backend.repository.ResumeRepository;
import com.abdullah.bdjobs_backend.repository.UserRepository;
import com.abdullah.bdjobs_backend.service.PdfGeneratorService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.security.Principal;

@RestController
@RequestMapping("/api/resumes")
public class ResumeController {

    @Autowired
    private ResumeRepository resumeRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired // 👈 This "connects" the service to the controller
    private PdfGeneratorService pdfGeneratorService;


    @PostMapping
    public ResponseEntity<?> saveOrUpdateResume(@RequestBody ResumeDetail resumeData, Principal principal) {
        // 1. Security Check
        if (principal == null) {
            return ResponseEntity.status(401).body(new ErrorResponse("User not authenticated", 401));
        }

        String email = principal.getName();
        System.out.println(">>> Attempting to save resume for: " + email);

        try {
            return userRepository.findByEmail(email).map(user -> {

                // 2. Find existing resume or create new one
                ResumeDetail resume = resumeRepository.findByUserId(user.getId())
                        .orElse(new ResumeDetail());

                // 3. Mapping data from Vue Request
                resume.setUserId(user.getId());
                resume.setFullName(resumeData.getFullName());
                resume.setProfessionalTitle(resumeData.getProfessionalTitle());
                resume.setPhone(resumeData.getPhone());
                resume.setSummary(resumeData.getSummary());

                // Handle Collections (Skills & Experience)
                resume.setSkills(resumeData.getSkills());
                resume.setExperiences(resumeData.getExperiences());

                System.out.println(">>> Data mapped. Sending to Database...");

                // 4. The Database Save
                ResumeDetail saved = resumeRepository.save(resume);

                System.out.println(">>> Success! Resume ID: " + saved.getId());
                return ResponseEntity.ok(saved);

            }).orElseThrow(() -> new UserNotFoundException("User not found: " + email));

        } catch (org.springframework.dao.DataAccessException e) {
            // THIS CATCHES DATABASE SPECIFIC ERRORS (Missing tables, constraint violations)
            System.err.println("--- DATABASE ERROR DETECTED ---");
            System.err.println("Message: " + e.getMostSpecificCause().getMessage());
            e.printStackTrace(); // Detailed log in IntelliJ

            return ResponseEntity.status(500).body(new ErrorResponse(
                    "Database Error: " + e.getMostSpecificCause().getMessage(), 500));

        } catch (Exception e) {
            // CATCHES EVERYTHING ELSE (Null pointers, logic errors)
            System.err.println("--- GENERAL SERVER ERROR ---");
            e.printStackTrace();

            return ResponseEntity.status(500).body(new ErrorResponse(
                    "Server Error: " + e.getMessage(), 500));
        }
    }

    @GetMapping("/download")
    @Transactional(readOnly = true) // Keeps the database session open for the PDF generator
    public void downloadPdf(HttpServletResponse response, Principal principal) throws IOException {
        try {
            String email = principal.getName();
            ResumeDetail resume = userRepository.findByEmail(email)
                    .flatMap(user -> resumeRepository.findByUserId(user.getId()))
                    .orElseThrow(() -> new RuntimeException("Resume not found"));

            response.setContentType("application/pdf");
            response.setHeader("Content-Disposition", "attachment; filename=resume.pdf");

            pdfGeneratorService.export(response, resume);
        } catch (Exception e) {
            // Handle the "HttpMessageNotWritableException" from your logs
            response.reset();
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.setContentType("application/json");
            response.getWriter().write("{\"error\": \"" + e.getMessage() + "\"}");
        }
    }
}