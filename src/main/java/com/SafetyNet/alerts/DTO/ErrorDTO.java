package com.SafetyNet.alerts.DTO;

import org.springframework.boot.autoconfigure.web.ErrorProperties.IncludeAttribute;

public class ErrorDTO {
    private int status;
    private String error;
    private IncludeAttribute message;

    public ErrorDTO() {
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public IncludeAttribute getMessage() {
        return message;
    }

    public void setMessage(IncludeAttribute includeAttribute) {
        this.message = includeAttribute;
    }
}
