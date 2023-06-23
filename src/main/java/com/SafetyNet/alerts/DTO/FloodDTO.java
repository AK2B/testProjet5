package com.SafetyNet.alerts.DTO;

import java.util.List;

import lombok.Data;

@Data
public class FloodDTO {
	
    private String address;
    private List<PersonDTO> persons;

    public FloodDTO(String address, List<PersonDTO> persons) {
        this.address = address;
        this.persons = persons;
    }

}

