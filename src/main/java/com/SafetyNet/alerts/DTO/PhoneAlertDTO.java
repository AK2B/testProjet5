package com.SafetyNet.alerts.DTO;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;

@Data
public class PhoneAlertDTO {
    private List<String> phoneNumbers;

    public PhoneAlertDTO() {
        this.phoneNumbers = new ArrayList<>();
    }

    
}
