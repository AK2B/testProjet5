package com.SafetyNet.alerts.models;

import java.util.List;

import lombok.Data;

@Data
public class FireStationCoverage {
    
	private String address;
    private int numAdults;
    private int numChildren;
    private List<PersonCoverage> persons;
    
}


