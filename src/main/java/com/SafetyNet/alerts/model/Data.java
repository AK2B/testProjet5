package com.SafetyNet.alerts.model;

import java.util.List;


public class Data {
    
	private List<Person> persons;
    private List<FireStation> firestations;
    private List<MedicalRecord> medicalRecords;

    
	public Data(List<Person> persons, List<FireStation> firestations, List<MedicalRecord> medicalRecords) {
		this.persons = persons;
		this.firestations = firestations;
		this.medicalRecords = medicalRecords;
	} 
	
	public Data() {
		super();
	}

	public List<Person> getPersons() {
		return persons;
	}
	public void setPersons(List<Person> persons) {
		this.persons = persons;
	}
	
	public List<FireStation> getFireStations() {
		return firestations;
	}
	public void setFirestations(List<FireStation> firestations) {
		this.firestations = firestations;
	}
	
	public List<MedicalRecord> getMedicalRecords() {
		return medicalRecords;
	}
	public void setMedicalRecords(List<MedicalRecord> medicalRecords) {
		this.medicalRecords = medicalRecords;
	}
}