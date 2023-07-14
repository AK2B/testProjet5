package com.SafetyNet.alerts.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.SafetyNet.alerts.models.ChildAlert;
import com.SafetyNet.alerts.services.AlertsService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/childAlert")
@Tag (name= "childAlert" , description= "Child Alert API")
public class ChildAlertController {
    private AlertsService alertsService;

    @Autowired
    public ChildAlertController(AlertsService alertsService) {
        this.alertsService = alertsService;
    }

    @GetMapping
    @Operation(summary="Get child alert by address")
    @ApiResponses({
    	@ApiResponse(responseCode = "200", description = "Success", content = { @Content(schema = @Schema(implementation = ChildAlert.class), mediaType = "application/json") }),
    	@ApiResponse(responseCode = "404", description = "Address not found"),
        @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ChildAlert getChildAlert(@RequestParam("address") String address) throws Exception {
        return alertsService.getChildAlert(address);
    }
}
