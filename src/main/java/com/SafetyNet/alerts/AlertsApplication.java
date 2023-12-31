package com.SafetyNet.alerts;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.error.DefaultErrorAttributes;
import org.springframework.boot.web.servlet.error.ErrorAttributes;
import org.springframework.context.annotation.Bean;


@SpringBootApplication
public class AlertsApplication {
    private static final Logger logger = LogManager.getLogger(AlertsApplication.class);

    public static void main(String[] args) {
        SpringApplication.run(AlertsApplication.class, args);
        
        logger.info("Bonjour.");

    }
    
    
    @Bean
    public ErrorAttributes errorAttributes() {
        return new DefaultErrorAttributes();
    }
	
}