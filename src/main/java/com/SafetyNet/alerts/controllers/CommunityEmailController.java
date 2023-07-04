package com.SafetyNet.alerts.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.SafetyNet.alerts.models.alerts.CommunityEmail;
import com.SafetyNet.alerts.services.AlertsService;

@RestController
@RequestMapping("/communityEmail")
public class CommunityEmailController {
    
	private AlertsService dtoService;
	
	@Autowired
	public CommunityEmailController(AlertsService dtoService) {
		this.dtoService = dtoService;
    }

    @GetMapping
    public CommunityEmail getCommunityEmails(@RequestParam("city") String city) {
        return dtoService.getCommunityEmails(city);
    }
}
