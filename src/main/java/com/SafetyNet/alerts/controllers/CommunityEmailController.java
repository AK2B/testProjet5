package com.SafetyNet.alerts.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.SafetyNet.alerts.models.CommunityEmail;
import com.SafetyNet.alerts.services.AlertsService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/communityEmail")
@Tag (name = "communityEmail" , description = "Community Email API")
public class CommunityEmailController {

    private AlertsService alertsService;

    @Autowired
    public CommunityEmailController(AlertsService alertsService) {
        this.alertsService = alertsService;
    }

    @GetMapping
    @Operation(summary ="Get community emails by city")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Success", content = { @Content(schema = @Schema(implementation = CommunityEmail.class), mediaType = "application/json") }),
        @ApiResponse(responseCode = "404", description = "City not found"),
        @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public CommunityEmail getCommunityEmails(@RequestParam("city") String city) {
        return alertsService.getCommunityEmails(city);
    }
}
