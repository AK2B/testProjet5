package com.SafetyNet.alerts.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.SafetyNet.alerts.DTO.ChildAlertDTO;
import com.SafetyNet.alerts.services.DTOService;

@RestController
@RequestMapping("/childAlert")
public class ChildAlertController {
    private DTOService dtoService;

    @Autowired
    public ChildAlertController(DTOService dtoService) {
        this.dtoService = dtoService;
    }

    @GetMapping
    public ChildAlertDTO getChildAlert(@RequestParam("address") String address) throws Exception {
        return dtoService.getChildAlert(address);
        
    }
    
}
