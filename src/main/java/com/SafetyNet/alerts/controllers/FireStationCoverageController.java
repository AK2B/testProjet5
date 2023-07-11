package com.SafetyNet.alerts.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.SafetyNet.alerts.models.FireStationCoverage;
import com.SafetyNet.alerts.services.AlertsService;

@RestController
@RequestMapping("/firestation")
public class FireStationCoverageController {
	
    private AlertsService dtoService;

    @Autowired
    public FireStationCoverageController(AlertsService dtoService) {
        this.dtoService = dtoService;
    }

    @GetMapping
    public List<FireStationCoverage> getFireStationCoverage(@RequestParam("stationNumber") int fireStationNumber) {
        return dtoService.getFireStationCoverage(fireStationNumber);
    }
}
