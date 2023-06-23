package com.SafetyNet.alerts.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.SafetyNet.alerts.DTO.FloodDTO;
import com.SafetyNet.alerts.services.DTOService;

@RestController
@RequestMapping("/flood/stations")
public class FloodController {
	
	private DTOService dtoService;
	
	@Autowired
    public FloodController(DTOService dtoService) {
		this.dtoService = dtoService;
    }

    @GetMapping
    public List<FloodDTO> getFloodStations(@RequestParam("stations") List<Integer> stationNumbers) {
        return dtoService.getFloodStations(stationNumbers);
    }
}
