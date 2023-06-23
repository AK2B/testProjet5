package com.SafetyNet.alerts.DTO;

import java.util.List;

import lombok.Data;

@Data
public class PersonDTO {
    
	private String firstName;
    private String lastName;
    private String phone;
    private int age;
    private List<String> medications;
    private List<String> allergies;
	
    public PersonDTO(String firstName, String lastName, String phone, int age, List<String> medications, List<String> allergies) {
    	this.firstName = firstName;
        this.lastName = lastName;
        this.phone = phone;
        this.age = age;
        this.medications = medications;
        this.allergies = allergies;
	}

}