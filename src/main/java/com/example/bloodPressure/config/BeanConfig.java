package com.example.bloodPressure.config;

import com.itextpdf.text.Document;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BeanConfig {

    @Bean
    public Document getDocument(){
        return new Document();
    }
}
