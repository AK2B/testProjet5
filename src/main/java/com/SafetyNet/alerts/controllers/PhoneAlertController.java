package com.SafetyNet.alerts.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.SafetyNet.alerts.models.alerts.PhoneAlert;
import com.SafetyNet.alerts.services.AlertsService;

@RestController
@RequestMapping("/phoneAlert")
public class PhoneAlertController {
    private AlertsService dtoService;

    @Autowired
	public PhoneAlertController(AlertsService dtoService) {
		this.dtoService = dtoService;
	}

    @GetMapping
    public PhoneAlert getPhoneAlert(@RequestParam("firestation") String firestationNumber) {
        return dtoService.getPhoneAlert(firestationNumber);
    }

	
}
