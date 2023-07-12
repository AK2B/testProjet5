package com.SafetyNet.alerts.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.SafetyNet.alerts.models.Fire;
import com.SafetyNet.alerts.services.AlertsService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@RequestMapping("/fire")
@Api(tags = "Fire API")
public class FireController {
    private AlertsService alertsService;

    @Autowired
    public FireController(AlertsService alertsService) {
        this.alertsService = alertsService;
    }

    @GetMapping
    @ApiOperation("Get fire details by address")
    @ApiResponses({
        @ApiResponse(code = 200, message = "Success", response = Fire.class),
        @ApiResponse(code = 404, message = "Address not found"),
        @ApiResponse(code = 500, message = "Internal server error")
    })
    public Fire getFireDetails(@RequestParam("address") String address) {
        return alertsService.getFireInformation(address);
    }
}
