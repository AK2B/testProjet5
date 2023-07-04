package com.SafetyNet.alerts.models.alerts;

import java.util.List;

import lombok.Data;

@Data
public class Flood {
	
    private String address;
    private List<PersonFlood> persons;

    public Flood(String address, List<PersonFlood> persons) {
        this.address = address;
        this.persons = persons;
    }

}

