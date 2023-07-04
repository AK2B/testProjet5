package com.SafetyNet.alerts.models.alerts;

import java.util.List;
import lombok.Data;

@Data
public class Fire {
	private List<PersonFire> persons;
	private String fireStationNumber;

	public Fire(List<PersonFire> persons, String string) {
		super();
		this.persons = persons;
		this.fireStationNumber = string;
	}

}