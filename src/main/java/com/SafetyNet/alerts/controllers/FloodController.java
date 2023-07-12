package com.SafetyNet.alerts.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.SafetyNet.alerts.models.Flood;
import com.SafetyNet.alerts.services.AlertsService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@RequestMapping("/flood/stations")
@Api(tags = "Flood API")
public class FloodController {
	
    private AlertsService alertsService;
	
    @Autowired
    public FloodController(AlertsService alertsService) {
        this.alertsService = alertsService;
    }

    @GetMapping
    @ApiOperation("Get flood stations")
    @ApiResponses({
        @ApiResponse(code = 200, message = "Success", response = Flood.class, responseContainer = "List"),
        @ApiResponse(code = 404, message = "Flood stations not found"),
        @ApiResponse(code = 500, message = "Internal server error")
    })
    public List<Flood> getFloodStations(@RequestParam("stations") List<Integer> stationNumbers) {
        return alertsService.getFloodStations(stationNumbers);
    }
}
