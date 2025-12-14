package com.example.bloodPressure.service;

import com.example.bloodPressure.model.BloodPressure;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.Font;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;

@Slf4j
@Service
public class BloodPressureService {

    @Autowired
    private Document document;

    public String calculateBloodPressure(BloodPressure bloodPressure){
        log.info("Calculating blood pressure for inputs: systolic {} and diastolic {}", bloodPressure.getSystolic(),bloodPressure.getDiastolic());
        // logic to calculate Bp
        var systolic = bloodPressure.getSystolic();
        var diastolic = bloodPressure.getDiastolic();

        if (systolic < 120 && diastolic < 80) {
            return "Normal";
        } else if (systolic < 130 && diastolic < 80) {
            return "Elevated";
        } else if ((systolic <= 139 && diastolic <= 89)) {
            return "High Blood Pressure (Hypertension Stage 1)";
        } else if ((systolic >= 140 && systolic <= 180) || (diastolic >= 90 && diastolic <= 120)) {
            return "High Blood Pressure (Hypertension Stage 2)";
        } else {
            return "Hypertensive Crisis (Consult your doctor immediately!)";
        }
    }

    public byte[] generatePdf(BloodPressure bp) {
        log.info("Generating report in PDF format...");
        ByteArrayOutputStream out = new ByteArrayOutputStream();

        try {
            PdfWriter.getInstance(document, out);

            document.open();

            Font titleFont = new Font(Font.FontFamily.HELVETICA, 20, Font.BOLD);
            Font labelFont = new Font(Font.FontFamily.HELVETICA, 14, Font.BOLD);
            Font normalFont = new Font(Font.FontFamily.HELVETICA, 14);

            // Title
            document.add(new Paragraph("Blood Pressure Report", titleFont));
            document.add(new Paragraph("Generated on: " + new java.util.Date(), normalFont));
            document.add(Chunk.NEWLINE);

            // Section Header
            document.add(new Paragraph("Latest Reading", labelFont));
            document.add(Chunk.NEWLINE);

            // BP values
            document.add(new Paragraph("Systolic:  " + bp.getSystolic(), normalFont));
            document.add(new Paragraph("Diastolic: " + bp.getDiastolic(), normalFont));
            document.add(new Paragraph("Category:  " + bp.getCategory(), normalFont));

            document.add(Chunk.NEWLINE);
            document.add(new Paragraph("Category Definitions:", labelFont));

            document.add(new Paragraph("• Normal: <120 / <80", normalFont));
            document.add(new Paragraph("• Elevated: 120–129 / <80", normalFont));
            document.add(new Paragraph("• Hypertension Stage 1: 130–139 / 80–89", normalFont));
            document.add(new Paragraph("• Hypertension Stage 2: ≥140 / ≥90", normalFont));
            document.add(new Paragraph("• Crisis: >180 / >120", normalFont));

            document.close();
            log.info("PDF generated successfully !");
        } catch (Exception e) {
            e.printStackTrace();
        }

        return out.toByteArray();
    }

    public void codeSmellAndRefactored(){
            String username = "admin";
        try{
            String password = System.getenv("password");
            if (password !=null){
                log.info("password accepted !");
            }
            //log.info("password: {}",password); // sensitive information like password should not be logged
        }catch (Exception e){
            log.error("refactored the code smell for user : {}",username);
            throw e;
        }


    }
}
