package com.SafetyNet.alerts.DTO;

import java.util.List;

import com.SafetyNet.alerts.model.Person;

import lombok.Data;


/**
 * Classe représentant une liste d'enfants avec les membres du foyer.
 */
@Data
public class ChildAlertDTO {
    private List<ChildDTO> children;
    private List<Person> householdMembers;

    public ChildAlertDTO(List<ChildDTO> children, List<Person> householdMembers) {
        this.children = children;
        this.householdMembers = householdMembers;
    }
   
    
}

