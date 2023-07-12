package com.SafetyNet.alerts.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.SafetyNet.alerts.models.PersonInfo;
import com.SafetyNet.alerts.services.AlertsService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@RequestMapping("/personInfo")
@Api(tags = "Person Info API")
public class PersonInfoController {

    private AlertsService alertsService;

    @Autowired
    public PersonInfoController(AlertsService alertsService) {
        this.alertsService = alertsService;
    }

    @GetMapping
    @ApiOperation("Get person info")
    @ApiResponses({
        @ApiResponse(code = 200, message = "Success", response = PersonInfo.class, responseContainer = "List"),
        @ApiResponse(code = 500, message = "Internal server error")
    })
    public List<PersonInfo> getPersonInfo(@RequestParam("firstName") String firstName, @RequestParam("lastName") String lastName) {
        return alertsService.getPersonInfo(firstName, lastName);
    }
}
