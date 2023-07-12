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

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@RequestMapping("/firestations")
@Api(tags = "Fire Station API")
public class FireStationController {
    private FireStationService fireStationService;

    @Autowired
    public FireStationController(FireStationService fireStationService) {
        this.fireStationService = fireStationService;
    }

    @GetMapping
    @ApiOperation("Get all fire stations")
    @ApiResponses({
        @ApiResponse(code = 200, message = "Success", response = FireStation.class, responseContainer = "List")
    })
    public ResponseEntity<List<FireStation>> getAllFireStations() {
        List<FireStation> fireStations = fireStationService.getAllFireStations();
        return ResponseEntity.ok(fireStations);
    }

    @GetMapping("/{address}")
    @ApiOperation("Get fire station by address")
    @ApiResponses({
        @ApiResponse(code = 200, message = "Success", response = FireStation.class),
        @ApiResponse(code = 404, message = "Fire station not found")
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
    @ApiOperation("Add a new fire station")
    @ApiResponses({
        @ApiResponse(code = 201, message = "Fire station created")
    })
    public ResponseEntity<String> addFireStation(@RequestBody FireStation fireStation) {
        fireStationService.addFireStation(fireStation);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PutMapping("/{address}")
    @ApiOperation("Update a fire station")
    @ApiResponses({
        @ApiResponse(code = 200, message = "Fire station updated"),
        @ApiResponse(code = 404, message = "Fire station not found")
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
    @ApiOperation("Delete a fire station")
    @ApiResponses({
        @ApiResponse(code = 200, message = "Fire station deleted")
    })
    public ResponseEntity<String> deleteFireStation(@PathVariable String address) {
        fireStationService.deleteFireStation(address);
        return ResponseEntity.ok().build();
    }
}
