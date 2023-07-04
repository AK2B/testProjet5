package com.SafetyNet.alerts.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.SafetyNet.alerts.models.alerts.ChildAlert;
import com.SafetyNet.alerts.services.AlertsService;

@RestController
@RequestMapping("/childAlert")
public class ChildAlertController {
    private AlertsService dtoService;

    @Autowired
    public ChildAlertController(AlertsService dtoService) {
        this.dtoService = dtoService;
    }

    @GetMapping
    public ChildAlert getChildAlert(@RequestParam("address") String address) throws Exception {
        return dtoService.getChildAlert(address);
        
    }
    
}
