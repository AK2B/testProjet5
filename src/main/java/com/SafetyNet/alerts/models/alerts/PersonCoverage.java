package com.SafetyNet.alerts.models.alerts;

import lombok.Data;

@Data
public class PersonCoverage {
	
    private String firstName;
    private String lastName;
    private String address;
    private String phone;

    public PersonCoverage(String firstName, String lastName, String address, String phone) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
        this.phone = phone;
    }

    
}
