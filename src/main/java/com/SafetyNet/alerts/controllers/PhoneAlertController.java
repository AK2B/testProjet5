package com.SafetyNet.alerts.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.SafetyNet.alerts.models.PhoneAlert;
import com.SafetyNet.alerts.services.AlertsService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/phoneAlert")
@Tag(name = "phoneAlert" , description = "Phone Alert API")
public class PhoneAlertController {
    private AlertsService dtoService;

    @Autowired
    public PhoneAlertController(AlertsService dtoService) {
        this.dtoService = dtoService;
    }

    @GetMapping
    @Operation(summary = "Get phone alert by fire station number")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Success", content = { @Content(schema = @Schema(implementation = PhoneAlert.class), mediaType = "application/json") }),
        @ApiResponse(responseCode = "404", description = "Fire station not found"),
        @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public PhoneAlert getPhoneAlert(@RequestParam("firestation") String firestationNumber) {
        return dtoService.getPhoneAlert(firestationNumber);
    }
}
