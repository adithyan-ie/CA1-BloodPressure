package com.example.bloodPressure.config;

import com.itextpdf.text.Document;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

@Configuration
public class BeanConfig {

    @Bean
    @Scope("prototype")
    public Document getDocument(){
        return new Document();
    }
}
