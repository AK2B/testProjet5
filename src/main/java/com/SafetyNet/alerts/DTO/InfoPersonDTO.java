package com.SafetyNet.alerts.DTO;

import lombok.Data;

@Data
public class InfoPersonDTO {
    private String firstName;
    private String lastName;
    private String address;
    private String email;

    public InfoPersonDTO(String firstName, String lastName, String address, String email) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
        this.email = email;
    }
}