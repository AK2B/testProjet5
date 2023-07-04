package com.SafetyNet.alerts.models.alerts;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;

@Data
public class PhoneAlert {
	private List<String> phoneNumbers;

	public PhoneAlert() {
		this.phoneNumbers = new ArrayList<>();
	}

}
