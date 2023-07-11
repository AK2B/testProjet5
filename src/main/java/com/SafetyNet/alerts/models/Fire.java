package com.SafetyNet.alerts.models;

import java.util.List;
import lombok.Data;

@Data
public class Fire {
	private List<PersonFire> personFires;
	private String fireStationNumber;

	public Fire(List<PersonFire> personFires, String fireStationNumber) {
		super();
		this.personFires = personFires;
		this.fireStationNumber = fireStationNumber;
	}

}