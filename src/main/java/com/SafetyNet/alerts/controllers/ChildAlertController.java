package com.SafetyNet.alerts.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.SafetyNet.alerts.models.ChildAlert;
import com.SafetyNet.alerts.services.AlertsService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@RequestMapping("/childAlert")
@Api(tags = "Child Alert API")
public class ChildAlertController {
    private AlertsService alertsService;

    @Autowired
    public ChildAlertController(AlertsService alertsService) {
        this.alertsService = alertsService;
    }

    @GetMapping
    @ApiOperation("Get child alert by address")
    @ApiResponses({
        @ApiResponse(code = 200, message = "Success", response = ChildAlert.class),
        @ApiResponse(code = 404, message = "Address not found"),
        @ApiResponse(code = 500, message = "Internal server error")
    })
    public ChildAlert getChildAlert(@RequestParam("address") String address) throws Exception {
        return alertsService.getChildAlert(address);
    }
}
