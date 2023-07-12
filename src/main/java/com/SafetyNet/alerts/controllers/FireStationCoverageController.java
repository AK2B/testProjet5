package com.SafetyNet.alerts.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.SafetyNet.alerts.models.FireStationCoverage;
import com.SafetyNet.alerts.services.AlertsService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@RequestMapping("/firestation")
@Api(tags = "Fire Station Coverage API")
public class FireStationCoverageController {

    private AlertsService alertsService;

    @Autowired
    public FireStationCoverageController(AlertsService alertsService) {
        this.alertsService = alertsService;
    }

    @GetMapping
    @ApiOperation("Get fire station coverage")
    @ApiResponses({
        @ApiResponse(code = 200, message = "Success", response = FireStationCoverage.class, responseContainer = "List"),
        @ApiResponse(code = 404, message = "Fire station not found"),
        @ApiResponse(code = 500, message = "Internal server error")
    })
    public List<FireStationCoverage> getFireStationCoverage(@RequestParam("stationNumber") int fireStationNumber) {
        return alertsService.getFireStationCoverage(fireStationNumber);
    }
}
