package com.example.bloodPressure.service;

import com.example.bloodPressure.model.BloodPressure;
import org.springframework.stereotype.Service;

@Service
public class BloodPressureService {

    public BloodPressure calculateBloodPressure(BloodPressure bloodPressure){
        // logic to calculate Bp
        return new BloodPressure();
    }
}
