package com.SafetyNet.alerts.model;

import org.springframework.boot.autoconfigure.web.ErrorProperties.IncludeAttribute;

import lombok.Data;

@Data
public class CustomErrorException {
	private int status;
	private String error;
	private IncludeAttribute message;

	public CustomErrorException() {
	}

}
