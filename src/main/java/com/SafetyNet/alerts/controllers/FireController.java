package com.SafetyNet.alerts.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.SafetyNet.alerts.models.Fire;
import com.SafetyNet.alerts.services.AlertsService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/fire")
@Tag (name = "fire" , description = "Fire API")
public class FireController {
    private AlertsService alertsService;

    @Autowired
    public FireController(AlertsService alertsService) {
        this.alertsService = alertsService;
    }

    @GetMapping
    @Operation(summary = "Get fire details by address")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Success", content = { @Content(schema = @Schema(implementation = Fire.class), mediaType = "application/json") }),
        @ApiResponse(responseCode = "404", description = "Address not found"),
        @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public Fire getFireDetails(@RequestParam("address") String address) {
        return alertsService.getFireInformation(address);
    }
}
