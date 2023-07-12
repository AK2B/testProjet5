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

import com.SafetyNet.alerts.models.MedicalRecord;
import com.SafetyNet.alerts.services.MedicalRecordService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@RequestMapping("/medicalrecord")
@Api(tags = "Medical Record API")
public class MedicalRecordController {
    private MedicalRecordService medicalRecordService;

    @Autowired
    public MedicalRecordController(MedicalRecordService medicalRecordService) {
        this.medicalRecordService = medicalRecordService;
    }

    @GetMapping
    @ApiOperation("Get all medical records")
    @ApiResponses({
        @ApiResponse(code = 200, message = "Success", response = MedicalRecord.class, responseContainer = "List")
    })
    public ResponseEntity<List<MedicalRecord>> getAllMedicalRecords() {
        List<MedicalRecord> medicalRecords = medicalRecordService.getAllMedicalRecords();
        return ResponseEntity.ok(medicalRecords);
    }

    @GetMapping("/{firstName}/{lastName}")
    @ApiOperation("Get medical record by full name")
    @ApiResponses({
        @ApiResponse(code = 200, message = "Success", response = MedicalRecord.class),
        @ApiResponse(code = 404, message = "Medical record not found")
    })
    public ResponseEntity<MedicalRecord> getMedicalRecordByFullName(
            @PathVariable String firstName, @PathVariable String lastName) {
        MedicalRecord medicalRecord = medicalRecordService.getMedicalRecordByFullName(firstName, lastName);
        if (medicalRecord != null) {
            return ResponseEntity.ok(medicalRecord);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    @ApiOperation("Add a new medical record")
    @ApiResponses({
        @ApiResponse(code = 201, message = "Medical record created")
    })
    public ResponseEntity<String> addMedicalRecord(@RequestBody MedicalRecord medicalRecord) {
        medicalRecordService.addMedicalRecord(medicalRecord);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PutMapping("/{firstName}/{lastName}")
    @ApiOperation("Update a medical record")
    @ApiResponses({
        @ApiResponse(code = 200, message = "Medical record updated"),
        @ApiResponse(code = 404, message = "Medical record not found")
    })
    public ResponseEntity<String> updateMedicalRecord(
            @PathVariable String firstName, @PathVariable String lastName, @RequestBody MedicalRecord medicalRecord) {
        MedicalRecord existingMedicalRecord = medicalRecordService.getMedicalRecordByFullName(firstName, lastName);
        if (existingMedicalRecord != null) {
            medicalRecord.setFirstName(firstName);
            medicalRecord.setLastName(lastName);
            medicalRecordService.updateMedicalRecord(medicalRecord);
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{firstName}/{lastName}")
    @ApiOperation("Delete a medical record")
    @ApiResponses({
        @ApiResponse(code = 200, message = "Medical record deleted")
    })
    public ResponseEntity<String> deleteMedicalRecord(
            @PathVariable String firstName, @PathVariable String lastName) {
        medicalRecordService.deleteMedicalRecord(firstName, lastName);
        return ResponseEntity.ok().build();
    }
}
