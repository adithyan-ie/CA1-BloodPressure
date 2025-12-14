package com.example.bloodPressure;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class BloodPressureE2ETest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    @DisplayName("Run E2E for calculating blood pressure")
    void runE2ETestToCalculateBloodPressure() {
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

    @Test
    @DisplayName("Run E2E to download report")
    void runE2ETestToDownloadReport() {
        String url = "http://localhost:" + port + "/download-report";

        var requestBody = "{\"systolic\":120,\"diastolic\":80}";
        var response = restTemplate.postForEntity(url,
                org.springframework.http.RequestEntity
                        .post(java.net.URI.create(url))
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(requestBody), byte[].class);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertThat(response.getBody())
                .as("This submits post request and returns and HTML page with report  information")
                .isNotNull();
        var headers = response.getHeaders();
        assertEquals(MediaType.APPLICATION_PDF, headers.getContentType());
        String contentDisposition = headers.getFirst(HttpHeaders.CONTENT_DISPOSITION);
        assertNotNull(contentDisposition);
        assertTrue(contentDisposition.contains("filename=\"bp-report.pdf\""));
        assertTrue(contentDisposition.contains("name=\"attachment\""));

        assertNotNull(response.getBody());
        assertTrue(response.getBody().length > 0);
    }
}
