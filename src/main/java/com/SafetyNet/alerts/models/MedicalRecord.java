package com.SafetyNet.alerts.models;

import java.util.List;

import io.swagger.annotations.ApiModel;
import lombok.Data;

@ApiModel(description = "Représentation d'un dossier médical")
@Data
public class MedicalRecord {
	private String firstName;
	private String lastName;
	private String birthdate;
	private List<String> medications;
	private List<String> allergies;

	public MedicalRecord(String firstName, String lastName, String birthdate, List<String> medications,
			List<String> allergies) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.birthdate = birthdate;
		this.medications = medications;
		this.allergies = allergies;
	}

}
