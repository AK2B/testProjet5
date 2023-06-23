package com.SafetyNet.alerts.DTO;

import java.util.List;

import com.SafetyNet.alerts.model.MedicalRecord;

import lombok.Data;

@Data
public class PersonFireDTO {
	
    private String firstName;
    private String lastName;
    private String phone;
    private int age;
    private MedicalRecord medicalRecord;
    
	public PersonFireDTO(String firstName, String lastName, String phone, int age, MedicalRecord medicalRecord) {
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