package com.example.bloodPressure.service;

import com.example.bloodPressure.model.BloodPressure;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
class BloodPressureServiceTest {

    @InjectMocks
    private BloodPressureService bloodPressureService;

    @Test
    @DisplayName("should calculate the blood pressure")
    public void shouldCalculateBloodPressure(){
        BloodPressure bloodPressure = new BloodPressure();
        bloodPressure.setCategory("");
        bloodPressure.setDiastolic(136);
        bloodPressure.setSystolic(53);

       String result =  bloodPressureService.calculateBloodPressure(bloodPressure);
       assertThat(result).isNotEmpty();
       assertThat(result).isEqualTo("Hypertensive Crisis (Consult your doctor immediately!)");
    }

}