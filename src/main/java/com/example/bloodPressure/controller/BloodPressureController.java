package com.example.bloodPressure.controller;

import com.example.bloodPressure.model.BloodPressure;
import com.example.bloodPressure.service.BloodPressureService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
//@RequestMapping(value = "/api/v1")
public class BloodPressureController {

    @Autowired
    private BloodPressureService bloodPressureService;

    @GetMapping("/bp")
    public String showForm(Model model) {
        model.addAttribute("bp", new BloodPressure());
        return "bp-form";
    }

    @PostMapping("/bp")
    public String submitForm(@ModelAttribute("bp") BloodPressure bp, Model model) {
        // logic
        bloodPressureService.calculateBloodPressure(bp);
        model.addAttribute("result", "Low Blood Pressure");
        return "bp-form";
    }
}
