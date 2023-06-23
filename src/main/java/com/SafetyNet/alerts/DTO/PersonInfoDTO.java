package com.SafetyNet.alerts.DTO;

import java.util.List;

import lombok.Data;

@Data
public class PersonInfoDTO {
    private InfoPersonDTO infoPersonDTO;
    private int age;
    private List<String> medications;
    private List<String> allergies;

    public PersonInfoDTO(InfoPersonDTO infoPersonDTO, int age, List<String> medications, List<String> allergies) {
        this.infoPersonDTO = infoPersonDTO;
        this.age = age;
        this.medications = medications;
        this.allergies = allergies;
    }

}