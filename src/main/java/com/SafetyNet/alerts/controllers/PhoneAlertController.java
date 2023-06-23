package com.SafetyNet.alerts.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.SafetyNet.alerts.DTO.PhoneAlertDTO;
import com.SafetyNet.alerts.services.DTOService;

@RestController
@RequestMapping("/phoneAlert")
public class PhoneAlertController {
    private DTOService dtoService;

    @Autowired
	public PhoneAlertController(DTOService dtoService) {
		this.dtoService = dtoService;
	}

    @GetMapping
    public PhoneAlertDTO getPhoneAlert(@RequestParam("firestation") int firestationNumber) {
        return dtoService.getPhoneAlert(firestationNumber);
    }

	
}
