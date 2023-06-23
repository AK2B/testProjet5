package com.SafetyNet.alerts.controllers;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.SafetyNet.alerts.repository.DataRepository;

@RestController
@RequestMapping("/")
public class DataController {
	public Logger logger = LogManager.getLogger(DataController.class);
	
    public DataController(DataRepository dataRepository) {
    }

    @GetMapping
    public String getData(){
    	logger.info("Faite une requête pour obtenir les données.");
        return "Bonjour, faite une requête pour obtenir les données dont vous avez besoin !!!";
    }

}




