package com.example.bloodPressure.controller;

import com.example.bloodPressure.model.BloodPressure;
import com.example.bloodPressure.service.BloodPressureService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Slf4j
@Controller
public class BloodPressureController {

    // In-memory telemetry storage (you could later replace with DB)
    private final List<Integer> systolicHistory = new ArrayList<>(List.of(118, 125, 132));
    private final List<Integer> diastolicHistory = new ArrayList<>(List.of(76, 82, 85));
    private final List<String> timestamps = new ArrayList<>(List.of("08:00", "12:00", "16:00"));



    @Autowired
    private BloodPressureService bloodPressureService;

    @GetMapping("/bp")
    public String showForm(Model model) {
        log.info("loading blood-pressure dashboard!");
        model.addAttribute("bp", new BloodPressure());
        return "bp-form";
    }

    @PostMapping("/bp")
    public String submitForm(@ModelAttribute("bp") BloodPressure bp, Model model) {
        log.info("Calculate blood-pressure request received.");
        // logic
       var bloodPressureResult =  bloodPressureService.calculateBloodPressure(bp);

        systolicHistory.add(bp.getSystolic());
        diastolicHistory.add(bp.getDiastolic());
        timestamps.add(String.format("%tR", new Date()));
        bp.setCategory(bloodPressureResult);
        model.addAttribute("bp", bp);
        model.addAttribute("categoryClass", bloodPressureResult);
        model.addAttribute("timestamps", timestamps);
        model.addAttribute("systolicList", systolicHistory);
        model.addAttribute("diastolicList", diastolicHistory);
        log.info("blood pressure result has been determined, category {}", bloodPressureResult);
        return "bp-telemetry";
    }

    @PostMapping("/download-report")
    public ResponseEntity<byte[]> downloadReport(@ModelAttribute("bp") BloodPressure bp){
        log.info("downloading report...!");
        byte[] pdfBytes = bloodPressureService.generatePdf(bp);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_PDF);
        headers.setContentDispositionFormData("attachment", "bp-report.pdf");
        log.info("report downloaded successfully !");
        return new ResponseEntity<>(pdfBytes, headers, HttpStatus.OK);
    }
}
