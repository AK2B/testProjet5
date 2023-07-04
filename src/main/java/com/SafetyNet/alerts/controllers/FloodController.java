package com.SafetyNet.alerts.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.SafetyNet.alerts.models.alerts.Flood;
import com.SafetyNet.alerts.services.AlertsService;

@RestController
@RequestMapping("/flood/stations")
public class FloodController {
	
	private AlertsService dtoService;
	
	@Autowired
    public FloodController(AlertsService dtoService) {
		this.dtoService = dtoService;
    }

    @GetMapping
    public List<Flood> getFloodStations(@RequestParam("stations") List<Integer> stationNumbers) {
        return dtoService.getFloodStations(stationNumbers);
    }
}
