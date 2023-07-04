package com.SafetyNet.alerts.models;

import org.springframework.boot.autoconfigure.web.ErrorProperties.IncludeAttribute;

import lombok.Data;

@Data
public class Error {
	private int status;
	private String error;
	private IncludeAttribute message;

	public Error() {
	}

}
