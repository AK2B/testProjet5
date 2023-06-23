package com.SafetyNet.alerts.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.SafetyNet.alerts.DTO.PersonInfoDTO;
import com.SafetyNet.alerts.services.DTOService;

@RestController
@RequestMapping("/personInfo")
public class PersonInfoController {
    
	private DTOService dtoService;

    @Autowired
    public PersonInfoController(DTOService dtoService) {
        this.dtoService = dtoService;
    }

    @GetMapping
    public List<PersonInfoDTO> getPersonInfo(@RequestParam("firstName") String firstName, @RequestParam("lastName") String lastName) {
    	return dtoService.getPersonInfoDTO(firstName, lastName);
    }
    
}
