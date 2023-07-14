package com.SafetyNet.alerts.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.SafetyNet.alerts.models.FireStationCoverage;
import com.SafetyNet.alerts.services.AlertsService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/firestation")
@Tag(name = "fireStationCoverage" , description = "Fire Station Coverage API")
public class FireStationCoverageController {

    private AlertsService alertsService;

    @Autowired
    public FireStationCoverageController(AlertsService alertsService) {
        this.alertsService = alertsService;
    }

    @GetMapping
    @Operation(summary = "Get fire station coverage")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Success",  content = { @Content(schema = @Schema(implementation = FireStationCoverage.class), mediaType = "application/json") }),
        @ApiResponse(responseCode = "404", description = "Fire station not found"),
        @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public List<FireStationCoverage> getFireStationCoverage(@RequestParam("stationNumber") int fireStationNumber) {
        return alertsService.getFireStationCoverage(fireStationNumber);
    }
}
