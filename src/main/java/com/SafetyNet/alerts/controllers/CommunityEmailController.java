package com.SafetyNet.alerts.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.SafetyNet.alerts.models.CommunityEmail;
import com.SafetyNet.alerts.services.AlertsService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@RequestMapping("/communityEmail")
@Api(tags = "Community Email API")
public class CommunityEmailController {

    private AlertsService alertsService;

    @Autowired
    public CommunityEmailController(AlertsService alertsService) {
        this.alertsService = alertsService;
    }

    @GetMapping
    @ApiOperation("Get community emails by city")
    @ApiResponses({
        @ApiResponse(code = 200, message = "Success", response = CommunityEmail.class),
        @ApiResponse(code = 404, message = "City not found"),
        @ApiResponse(code = 500, message = "Internal server error")
    })
    public CommunityEmail getCommunityEmails(@RequestParam("city") String city) {
        return alertsService.getCommunityEmails(city);
    }
}
