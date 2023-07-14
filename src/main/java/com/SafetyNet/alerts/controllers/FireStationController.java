package com.SafetyNet.alerts.controllers;

import java.util.List;

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
import org.springframework.web.bind.annotation.RestController;

import com.SafetyNet.alerts.models.FireStation;
import com.SafetyNet.alerts.services.FireStationService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/firestations")
@Tag (name = "fireStation" , description = "Fire Station API")
public class FireStationController {
    private FireStationService fireStationService;

    @Autowired
    public FireStationController(FireStationService fireStationService) {
        this.fireStationService = fireStationService;
    }

    @GetMapping
    @Operation(summary = "Get all fire stations")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Success", content = { @Content(schema = @Schema(implementation = FireStation.class), mediaType = "application/json") })
    })
    public ResponseEntity<List<FireStation>> getAllFireStations() {
        List<FireStation> fireStations = fireStationService.getAllFireStations();
        return ResponseEntity.ok(fireStations);
    }

    @GetMapping("/{address}")
    @Operation(summary = "Get fire station by address")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Success", content = { @Content(schema = @Schema(implementation = FireStation.class), mediaType = "application/json") }),
        @ApiResponse(responseCode = "404", description = "Fire station not found")
    })
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
    @ApiResponses({
        @ApiResponse(responseCode = "201", description = "Fire station created")
    })
    public ResponseEntity<String> addFireStation(@RequestBody FireStation fireStation) {
        fireStationService.addFireStation(fireStation);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PutMapping("/{address}")
    @Operation(summary = "Update a fire station")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Fire station updated"),
        @ApiResponse(responseCode = "404", description = "Fire station not found")
    })
    public ResponseEntity<String> updateFireStation(@PathVariable String address, @RequestBody FireStation fireStation) {
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
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Fire station deleted")
    })
    public ResponseEntity<String> deleteFireStation(@PathVariable String address) {
        fireStationService.deleteFireStation(address);
        return ResponseEntity.ok().build();
    }
}
