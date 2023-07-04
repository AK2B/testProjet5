package com.SafetyNet.alerts.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.SafetyNet.alerts.models.alerts.Fire;
import com.SafetyNet.alerts.services.AlertsService;

@RestController
public class FireController {
    private AlertsService dtoService;
	
    @Autowired
	public FireController(AlertsService dtoService) {
		this.dtoService = dtoService;
    }
    
    @GetMapping("/fire")
    public Fire getFireDetails(@RequestParam("address") String address) {
        return dtoService.getFireInformation(address);
    }
}
