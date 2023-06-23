package com.SafetyNet.alerts.DTO;

import java.util.List;

import lombok.Data;

@Data
public class FireStationCoverageDTO {
    
	private String address;
    private int numAdults;
    private int numChildren;
    private List<PersonCoverageDTO> persons;
    
}


