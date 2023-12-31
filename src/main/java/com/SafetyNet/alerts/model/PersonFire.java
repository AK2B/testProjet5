package com.SafetyNet.alerts.model;

import java.util.List;

import lombok.Data;

/**
 * Classe représentant une liste de personnes composition de fire alerte.
 */
@Data
public class PersonFire {
    private String firstName;
    private String lastName;
    private String phone;
    private int age;
    private List<String> medications;
    private List<String> allergies;
	
    public PersonFire(String firstName, String lastName, String phone, int age, List<String> medications,
			List<String> allergies) {
		super();
		this.firstName = firstName;
		this.lastName = lastName;
		this.phone = phone;
		this.age = age;
		this.medications = medications;
		this.allergies = allergies;
	}
    
    
}
