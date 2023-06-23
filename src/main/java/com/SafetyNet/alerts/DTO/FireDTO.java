package com.SafetyNet.alerts.DTO;

import java.util.List;

public class FireDTO {
    private List<PersonFireDTO> persons;
    private String fireStationNumber;
	public FireDTO(List<PersonFireDTO> persons, String string) {
		super();
		this.persons = persons;
		this.fireStationNumber = string;
	}
	public List<PersonFireDTO> getPersons() {
		return persons;
	}
	public void setPersons(List<PersonFireDTO> persons) {
		this.persons = persons;
	}
	public String getFireStationNumber() {
		return fireStationNumber;
	}
	public void setFireStationNumber(String fireStationNumber) {
		this.fireStationNumber = fireStationNumber;
	}
}