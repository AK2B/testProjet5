package com.SafetyNet.alerts.models.alerts;

import java.util.List;

import lombok.Data;

@Data
public class PersonInfo {
    private InfoPerson infoPerson;
    private int age;
    private List<String> medications;
    private List<String> allergies;

    public PersonInfo(InfoPerson infoPerson, int age, List<String> medications, List<String> allergies) {
        this.infoPerson = infoPerson;
        this.age = age;
        this.medications = medications;
        this.allergies = allergies;
    }

}