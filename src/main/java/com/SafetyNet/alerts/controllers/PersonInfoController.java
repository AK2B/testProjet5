package com.SafetyNet.alerts.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.SafetyNet.alerts.models.PersonInfo;
import com.SafetyNet.alerts.services.AlertsService;

@RestController
@RequestMapping("/personInfo")
public class PersonInfoController {
    
	private AlertsService dtoService;

    @Autowired
    public PersonInfoController(AlertsService dtoService) {
        this.dtoService = dtoService;
    }

    @GetMapping
    public List<PersonInfo> getPersonInfo(@RequestParam("firstName") String firstName, @RequestParam("lastName") String lastName) {
    	return dtoService.getPersonInfo(firstName, lastName);
    }
    
}
