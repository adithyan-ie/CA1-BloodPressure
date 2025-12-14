package com.example.bloodPressure.steps;

import com.example.bloodPressure.controller.BloodPressureController;
import com.example.bloodPressure.model.BloodPressure;
import com.example.bloodPressure.service.BloodPressureService;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.ConcurrentModel;
import org.springframework.ui.Model;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import static org.junit.jupiter.api.Assertions.*;

public class BloodPressureSteps {

    private BloodPressure bp;
    private Model model;
    private String viewName;

    private TestRestTemplate restTemplate = new TestRestTemplate();
    private ResponseEntity<String> response;

    @LocalServerPort
    private int port;

    @Mock
    private BloodPressureService bloodPressureService;

    // ---- Class Under Test ----
    @InjectMocks
    private BloodPressureController controller;

    public BloodPressureSteps() {
        MockitoAnnotations.openMocks(this);  // Initialize @Mock & @InjectMocks
    }

    @Given("a user provides a systolic value of {int} and a diastolic value of {int}")
    public void userProvidesBloodPressure(int systolic, int diastolic) {
        bp = new BloodPressure();
        bp.setSystolic(systolic);
        bp.setDiastolic(diastolic);

        model = new ConcurrentModel();
    }

    @When("the user submits the blood pressure form")
    public void userSubmitsForm() {

        MultiValueMap<String, String> form = new LinkedMultiValueMap<>();
        form.add("systolic", String.valueOf(bp.getSystolic()));
        form.add("diastolic", String.valueOf(bp.getDiastolic()));

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(form, headers);
        String url = "http://localhost:" + port + "/bp";
        // POST to /bp
        response = restTemplate.postForEntity(url, request, String.class);
        viewName = controller.submitForm(bp, model);
    }

    @Then("the system should calculate the blood pressure category")
    public void systemCalculatesCategory() {
        assertNotNull(response.getBody());
    }

    @Then("the category should be {string}")
    public void categoryShouldBe(String expected) {

    assertEquals("High Blood Pressure (Hypertension Stage 1)", expected);
        assertTrue(response.getBody().contains(expected),"Response body does not contain category" + expected);
    }

    @Then("the system should store the systolic history")
    public void systolicHistoryStored() {
        assertNotNull(model.getAttribute("systolicList"));
    }

    @Then("the system should store the diastolic history")
    public void diastolicHistoryStored() {
        assertNotNull(model.getAttribute("diastolicList"));
    }

    @Then("the system should return the {string} view")
    public void returnView(String expectedView) {
    assertEquals("bp-telemetry", expectedView);
        assertEquals(expectedView, viewName);
    }

    @Then("the stage2 category should be {string}")
    public void stage2CategoryShouldBe(String expected) {

    assertEquals("High Blood Pressure (Hypertension Stage 2)", expected);
        assertTrue(response.getBody().contains(expected),"Response body does not contain category" + expected);
    }
}
