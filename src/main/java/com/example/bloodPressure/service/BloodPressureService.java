package com.example.bloodPressure.service;

import com.example.bloodPressure.model.BloodPressure;
import org.springframework.stereotype.Service;

@Service
public class BloodPressureService {

    public String calculateBloodPressure(BloodPressure bloodPressure){
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
        } else if (systolic > 180 || diastolic > 120) {
            return "Hypertensive Crisis (Consult your doctor immediately!)";
        } else {
            return "Unclassified (check your inputs)";
        }
    }
}
