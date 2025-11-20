package com.example.bloodPressure;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.MediaType;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class BloodPressureE2ETest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    @DisplayName("Run E2E for calculating blood pressure")
    public void testPostBloodPressure() {
        String url = "http://localhost:" + port + "/bp";

        var requestBody = "{\"systolic\":120,\"diastolic\":80}";
        var response = restTemplate.postForEntity(url,
                org.springframework.http.RequestEntity
                        .post(java.net.URI.create(url))
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(requestBody), String.class);

        assertThat(response.getStatusCode().is2xxSuccessful()).isTrue();
        assertThat(response.getBody())
                .as("This submits post request and returns and HTML page with report  information")
                .contains("Blood Pressure Telemetry");
    }
}
