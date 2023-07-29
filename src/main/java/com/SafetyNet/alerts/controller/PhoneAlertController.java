package com.SafetyNet.alerts.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.SafetyNet.alerts.model.PhoneAlert;
import com.SafetyNet.alerts.service.AlertsService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/phoneAlert")
@Tag(name = "phoneAlert", description = "Phone Alert API")
public class PhoneAlertController {

	private static final Logger logger = LogManager.getLogger(PhoneAlertController.class);

	private AlertsService alertsService;

	@Autowired
	public PhoneAlertController(AlertsService alertsService) {
		this.alertsService = alertsService;
	}

	@GetMapping
	@Operation(summary = "Get phone alert by fire station number")
	@ApiResponses({
			@ApiResponse(responseCode = "200", description = "Success", content = {
					@Content(schema = @Schema(implementation = PhoneAlert.class), mediaType = "application/json") }),
			@ApiResponse(responseCode = "404", description = "Fire station not found"),
			@ApiResponse(responseCode = "500", description = "Internal server error") })
	public ResponseEntity<PhoneAlert> getPhoneAlert(@RequestParam("firestation") String firestationNumberStr) {
		try {
			if (firestationNumberStr == null || firestationNumberStr.isEmpty()) {
				logger.error("The firestation number is null or empty.");
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
			}
			int firestationNumber;
			try {
				firestationNumber = Integer.parseInt(firestationNumberStr);
			} catch (NumberFormatException e) {
				logger.error("The firestation number must be an integer.");
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
			}
			PhoneAlert phoneAlert = alertsService.getPhoneAlert(firestationNumber);

			if (phoneAlert == null) {
				logger.error("Fire station not found for number: {}", firestationNumber);
				return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
			}
			logger.info("La méthode getPhoneAlert a été exécutée avec succès.");
			return ResponseEntity.ok(phoneAlert);
		} catch (Exception e) {
			logger.error("An error occurred while retrieving phone alert for firestation number: {}",
					firestationNumberStr, e);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
	}
}
