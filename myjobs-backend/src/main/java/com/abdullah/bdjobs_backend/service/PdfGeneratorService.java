package com.abdullah.bdjobs_backend.service;

import com.abdullah.bdjobs_backend.entity.ResumeDetail;
import com.abdullah.bdjobs_backend.entity.WorkExperience;
import com.lowagie.text.*;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import org.springframework.stereotype.Service;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

@Service
public class PdfGeneratorService {

    public void export(HttpServletResponse response, ResumeDetail resume) throws IOException {
        Document document = new Document(PageSize.A4);
        PdfWriter.getInstance(document, response.getOutputStream());

        document.open();

        // Styles
        Font fontTitle = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 18);
        Font fontSection = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 12);
        Font fontNormal = FontFactory.getFont(FontFactory.HELVETICA, 10);

        // Header
        Paragraph title = new Paragraph(resume.getFullName().toUpperCase(), fontTitle);
        title.setAlignment(Element.ALIGN_CENTER);
        document.add(title);

        Paragraph sub = new Paragraph(resume.getProfessionalTitle() + " | " + resume.getPhone(), fontNormal);
        sub.setAlignment(Element.ALIGN_CENTER);
        document.add(sub);
        document.add(new Chunk("\n")); // Spacer

        // Summary
        document.add(new Paragraph("PROFESSIONAL SUMMARY", fontSection));
        document.add(new Paragraph(resume.getSummary(), fontNormal));
        document.add(new Chunk("\n"));

        // Experience Table
        document.add(new Paragraph("WORK EXPERIENCE", fontSection));
        PdfPTable table = new PdfPTable(3);
        table.setWidthPercentage(100);
        table.setSpacingBefore(10f);

        for (WorkExperience exp : resume.getExperiences()) {
            table.addCell(new Phrase(exp.getCompany(), fontNormal));
            table.addCell(new Phrase(exp.getPosition(), fontNormal));
            table.addCell(new Phrase(exp.getDuration(), fontNormal));
        }
        document.add(table);

        document.close();
    }
}