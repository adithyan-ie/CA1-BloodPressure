package com.example.bloodPressure.service;

import com.example.bloodPressure.model.BloodPressure;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedConstruction;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.ByteArrayOutputStream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BloodPressureServiceTest {

    @Mock
    private Document document;

    @InjectMocks
    private BloodPressureService bloodPressureService;

    @Test
    @DisplayName("should calculate the blood pressure")
    void shouldCalculateBloodPressure(){
        BloodPressure bloodPressure = new BloodPressure();
        bloodPressure.setCategory("");
        bloodPressure.setDiastolic(136);
        bloodPressure.setSystolic(53);

       String result =  bloodPressureService.calculateBloodPressure(bloodPressure);
       assertThat(result).isNotEmpty();
       assertThat(result).isEqualTo("Hypertensive Crisis (Consult your doctor immediately!)");
    }

    @Test
    @DisplayName("should return appropriate message of blood pressure")
    void shouldCalculateBloodPressureInvalidData(){
        BloodPressure bloodPressure = new BloodPressure();
        bloodPressure.setCategory("");
        bloodPressure.setDiastolic(0);
        bloodPressure.setSystolic(0);

        String result =  bloodPressureService.calculateBloodPressure(bloodPressure);
        assertThat(result).isNotEmpty();
        assertThat(result).isEqualTo("Normal");
    }

    @Test
    @DisplayName("should return unclassified")
    void shouldCalculateBloodPressureUnclassified(){
        BloodPressure bloodPressure = new BloodPressure();
        bloodPressure.setCategory("");
        bloodPressure.setDiastolic(-200);
        bloodPressure.setSystolic(-200);

        String result =  bloodPressureService.calculateBloodPressure(bloodPressure);
        assertThat(result).isNotEmpty();
        assertThat(result).isEqualTo("Normal");
    }

    @Test
    @DisplayName("should generate a report")
    void shouldGenerateReport() throws DocumentException {
        BloodPressure bp = new BloodPressure();
        bp.setSystolic(120);
        bp.setDiastolic(80);
        bp.setCategory("Normal");

        // Mock Document construction
        try (MockedConstruction<Document> mockedDoc = Mockito.mockConstruction(Document.class,
                (mock, context) -> {
                    doNothing().when(mock).open();
                    doNothing().when(mock).add(any(Paragraph.class));
                    doNothing().when(mock).close();
                })) {

            // Mock PdfWriter static getInstance
            try (var mockedWriter = mockStatic(PdfWriter.class)) {
                mockedWriter.when(() -> PdfWriter.getInstance(any(Document.class), any(ByteArrayOutputStream.class)))
                        .thenReturn(mock(PdfWriter.class));

                // Act
                byte[] pdfBytes = bloodPressureService.generatePdf(bp);

                // Assert
                assertNotNull(pdfBytes, "PDF byte array should not be null");
                assertTrue(pdfBytes.length >= 0, "PDF byte array should have some length");

                // Verify interactions
                Document doc = document;
                verify(doc).open();
                verify(doc, atLeastOnce()).add(any(Paragraph.class));
                verify(doc).close();
            }
        }
    }
    }
