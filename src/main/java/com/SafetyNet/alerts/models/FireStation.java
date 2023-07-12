package com.SafetyNet.alerts.models;

import io.swagger.annotations.ApiModel;
import lombok.Data;

@ApiModel(description = "Représentation d'une station de pompier")
@Data
public class FireStation {

	private String address;
	private String station;

	public FireStation(String address, String station) {
		this.address = address;
		this.station = station;
	}

}
