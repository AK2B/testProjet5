package com.SafetyNet.alerts.models.alerts;

import java.util.List;

import com.SafetyNet.alerts.models.Person;

import lombok.Data;


/**
 * Classe représentant une liste d'enfants avec les membres du foyer.
 */
@Data
public class ChildAlert {
    private List<Child> children;
    private List<Person> householdMembers;

    public ChildAlert(List<Child> children, List<Person> householdMembers) {
        this.children = children;
        this.householdMembers = householdMembers;
    }
   
    
}

