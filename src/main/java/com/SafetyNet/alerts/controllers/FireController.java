package com.SafetyNet.alerts.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.SafetyNet.alerts.DTO.FireDTO;
import com.SafetyNet.alerts.services.DTOService;

@RestController
public class FireController {
    private DTOService dtoService;
	
    @Autowired
	public FireController(DTOService dtoService) {
		this.dtoService = dtoService;
    }
    
    @GetMapping("/fire")
    public FireDTO getFireDetails(@RequestParam("address") String address) {
        return dtoService.getFireInformation(address);
    }
}
