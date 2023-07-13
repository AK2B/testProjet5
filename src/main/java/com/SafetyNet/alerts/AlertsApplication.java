package com.SafetyNet.alerts;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.web.servlet.error.ErrorMvcAutoConfiguration;
import org.springframework.boot.web.servlet.error.DefaultErrorAttributes;
import org.springframework.boot.web.servlet.error.ErrorAttributes;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;


@SpringBootApplication
@ComponentScan
@EnableAutoConfiguration(exclude = {ErrorMvcAutoConfiguration.class})
public class AlertsApplication {
    private static final Logger logger = LogManager.getLogger(AlertsApplication.class);

    public static void main(String[] args) {
        SpringApplication.run(AlertsApplication.class, args);
        
        logger.info("Bonjour.");

    }
    /*
    @Bean
    public Docket productApi() {
       return new Docket(DocumentationType.SWAGGER_2).select()
          .apis(RequestHandlerSelectors.basePackage("com.SafetyNet.alerts")).build();
    }*/
    
    @Bean
    public ErrorAttributes errorAttributes() {
        return new DefaultErrorAttributes();
    }
	
}