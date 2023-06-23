package com.SafetyNet.alerts.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.SafetyNet.alerts.DTO.CommunityEmailDTO;
import com.SafetyNet.alerts.services.DTOService;

@RestController
@RequestMapping("/communityEmail")
public class CommunityEmailController {
    
	private DTOService dtoService;
	
	@Autowired
	public CommunityEmailController(DTOService dtoService) {
		this.dtoService = dtoService;
    }

    @GetMapping
    public CommunityEmailDTO getCommunityEmails(@RequestParam("city") String city) {
        return dtoService.getCommunityEmails(city);
    }
}
