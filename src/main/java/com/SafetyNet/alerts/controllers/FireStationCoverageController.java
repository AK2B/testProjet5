package com.SafetyNet.alerts.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.SafetyNet.alerts.DTO.FireStationCoverageDTO;
import com.SafetyNet.alerts.services.DTOService;

@RestController
@RequestMapping("/firestation")
public class FireStationCoverageController {
	
    private DTOService dtoService;

    @Autowired
    public FireStationCoverageController(DTOService dtoService) {
        this.dtoService = dtoService;
    }

    @GetMapping
    public List<FireStationCoverageDTO> getFireStationCoverage(@RequestParam("stationNumber") int fireStationNumber) {
        return dtoService.getFireStationCoverage(fireStationNumber);
    }
}
