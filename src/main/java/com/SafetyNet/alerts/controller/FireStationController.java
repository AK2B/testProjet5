package com.SafetyNet.alerts.controller;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.SafetyNet.alerts.model.FireStation;
import com.SafetyNet.alerts.model.FireStationCoverage;
import com.SafetyNet.alerts.service.AlertsService;
import com.SafetyNet.alerts.service.FireStationService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/firestation")
@Tag(name = "fireStation", description = "Fire Station API")
public class FireStationController {

	private static final Logger logger = LogManager.getLogger(FireStationController.class);

	private FireStationService fireStationService;
	private AlertsService alertsService;

	@Autowired
	public FireStationController(FireStationService fireStationService, AlertsService alertsService) {
		this.fireStationService = fireStationService;
		this.alertsService = alertsService;
	}

	@GetMapping
	@Operation(summary = "Get fire station coverage")
	@ApiResponses({ @ApiResponse(responseCode = "200", description = "Success", content = {
			@Content(schema = @Schema(implementation = FireStationCoverage.class), mediaType = "application/json") }),
			@ApiResponse(responseCode = "400", description = "Bad Request"),
			@ApiResponse(responseCode = "404", description = "Fire station not found"),
			@ApiResponse(responseCode = "500", description = "Internal server error") })
	public ResponseEntity<List<FireStationCoverage>> getFireStationCoverage(
			@RequestParam("stationNumber") String fireStationNumberStr) {
		try {
			if (fireStationNumberStr == null || fireStationNumberStr.isEmpty()) {
				logger.error("La station est nul ou vide.");
				return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
			}

			int fireStationNumber;
			try {
				fireStationNumber = Integer.parseInt(fireStationNumberStr);
			} catch (NumberFormatException e) {
				logger.error("La station doit être un integer.");
				return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
			}

			List<FireStationCoverage> coverage = alertsService.getFireStationCoverage(fireStationNumber);

			if (coverage == null) {
				logger.error("La station n'existe pas.");
				return new ResponseEntity<>(HttpStatus.NOT_FOUND);
			}
			logger.info("La méthode getFireStationCoverage a été exécutée avec succès.");
			return new ResponseEntity<>(coverage, HttpStatus.OK);
		} catch (Exception e) {
			logger.info("Une autre exception est levée.");
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping("/{address}")
	@Operation(summary = "Get fire station by address")
	@ApiResponses({
			@ApiResponse(responseCode = "200", description = "Success", content = {
					@Content(schema = @Schema(implementation = FireStation.class), mediaType = "application/json") }),
			@ApiResponse(responseCode = "404", description = "Fire station not found") })
	public ResponseEntity<FireStation> getFireStationByAddress(@PathVariable String address) {
		FireStation fireStation = fireStationService.getFireStationByAddress(address);
		if (fireStation != null) {
			return ResponseEntity.ok(fireStation);
		} else {
			return ResponseEntity.notFound().build();
		}
	}

	@PostMapping
	@Operation(summary = "Add a new fire station")
	@ApiResponses({ @ApiResponse(responseCode = "201", description = "Fire station created") })
	public ResponseEntity<String> addFireStation(@RequestBody FireStation fireStation) {
		fireStationService.addFireStation(fireStation);
		return ResponseEntity.status(HttpStatus.CREATED).build();
	}

	@PutMapping("/{address}")
	@Operation(summary = "Update a fire station")
	@ApiResponses({ @ApiResponse(responseCode = "200", description = "Fire station updated"),
			@ApiResponse(responseCode = "404", description = "Fire station not found") })
	public ResponseEntity<String> updateFireStation(@PathVariable String address,
			@RequestBody FireStation fireStation) {
		FireStation existingFireStation = fireStationService.getFireStationByAddress(address);
		if (existingFireStation != null) {
			fireStation.setAddress(address);
			fireStationService.updateFireStation(fireStation);
			return ResponseEntity.ok().build();
		} else {
			return ResponseEntity.notFound().build();
		}
	}

	@DeleteMapping("/{address}")
	@Operation(summary = "Delete a fire station")
	@ApiResponses({ @ApiResponse(responseCode = "200", description = "Fire station deleted") })
	public ResponseEntity<String> deleteFireStation(@PathVariable String address) {
		fireStationService.deleteFireStation(address);
		return ResponseEntity.ok().build();
	}
}
