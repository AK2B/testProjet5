package com.SafetyNet.alerts.controllers;

import java.io.IOException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.SafetyNet.alerts.model.Data;
import com.SafetyNet.alerts.services.DataService;

@RestController
@RequestMapping("/")
public class DataController {
	public Logger logger = LogManager.getLogger(DataController.class);
	
    private final DataService dataService;

    public DataController(DataService dataService) {
        this.dataService = dataService;
    }

    @GetMapping
    public Data getData() throws IOException {
    	logger.info("Requête pour obtenir les données.");
        return dataService.getData();
    }

}




