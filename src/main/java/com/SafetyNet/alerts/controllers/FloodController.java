package com.SafetyNet.alerts.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.SafetyNet.alerts.models.Flood;
import com.SafetyNet.alerts.services.AlertsService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/flood/stations")
@Tag (name = "flood" , description ="Flood API")
public class FloodController {
	
    private AlertsService alertsService;
	
    @Autowired
    public FloodController(AlertsService alertsService) {
        this.alertsService = alertsService;
    }

    @GetMapping
    @Operation(summary = "Get flood stations")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Success", content = { @Content(schema = @Schema(implementation = Flood.class), mediaType = "application/json") }),
        @ApiResponse(responseCode = "404", description = "Flood stations not found"),
        @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public List<Flood> getFloodStations(@RequestParam("stations") List<Integer> stationNumbers) {
        return alertsService.getFloodStations(stationNumbers);
    }
}
