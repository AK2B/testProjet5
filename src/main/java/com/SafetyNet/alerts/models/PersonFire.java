package com.SafetyNet.alerts.models;

import java.util.List;

import lombok.Data;

@Data
public class PersonFire {
	
    private String firstName;
    private String lastName;
    private String phone;
    private int age;
    private MedicalRecord medicalRecord;
    
	public PersonFire(String firstName, String lastName, String phone, int age, MedicalRecord medicalRecord) {
		super();
		this.firstName = firstName;
		this.lastName = lastName;
		this.phone = phone;
		this.age = age;
		this.medicalRecord = medicalRecord;
	}
	
	public void setMedications(List<String> medications) {
		this.medicalRecord.getMedications();
		
	}
	public void setAllergies(List<String> allergies) {
		this.medicalRecord.getAllergies();
		
	}
   
}