package com.SafetyNet.alerts.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.SafetyNet.alerts.models.PhoneAlert;
import com.SafetyNet.alerts.services.AlertsService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@RequestMapping("/phoneAlert")
@Api(tags = "Phone Alert API")
public class PhoneAlertController {
    private AlertsService dtoService;

    @Autowired
    public PhoneAlertController(AlertsService dtoService) {
        this.dtoService = dtoService;
    }

    @GetMapping
    @ApiOperation("Get phone alert by fire station number")
    @ApiResponses({
        @ApiResponse(code = 200, message = "Success", response = PhoneAlert.class),
        @ApiResponse(code = 404, message = "Fire station not found"),
        @ApiResponse(code = 500, message = "Internal server error")
    })
    public PhoneAlert getPhoneAlert(@RequestParam("firestation") String firestationNumber) {
        return dtoService.getPhoneAlert(firestationNumber);
    }
}
