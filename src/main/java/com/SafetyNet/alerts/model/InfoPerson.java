package com.SafetyNet.alerts.model;

import lombok.Data;
/**
 * Classe représentant une liste de personnes, composition de personne info.
 */
@Data
public class InfoPerson {
    private String firstName;
    private String lastName;
    private String address;
    private String email;

    public InfoPerson(String firstName, String lastName, String address, String email) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
        this.email = email;
    }
}