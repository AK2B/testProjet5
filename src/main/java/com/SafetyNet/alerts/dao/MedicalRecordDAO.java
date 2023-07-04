package com.SafetyNet.alerts.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.SafetyNet.alerts.config.DataConfig;
import com.SafetyNet.alerts.models.MedicalRecord;

@Repository
public class MedicalRecordDAO {

    private final List<MedicalRecord> medicalRecords;
    
    @Autowired
    public MedicalRecordDAO(DataConfig dataConfig) {
        this.medicalRecords = dataConfig.getMedicalRecords();
    }

    // CRUD operations only

    public List<MedicalRecord> getAllMedicalRecords() {
        return medicalRecords;
    }

    public void addMedicalRecord(MedicalRecord medicalRecord) {
        medicalRecords.add(medicalRecord);
    }

    public void updateMedicalRecord(MedicalRecord medicalRecord) {
        medicalRecords.stream()
                .filter(record -> record.getFirstName().equals(medicalRecord.getFirstName())
                        && record.getLastName().equals(medicalRecord.getLastName()))
                .findFirst()
                .ifPresent(record -> {
                    int index = medicalRecords.indexOf(record);
                    medicalRecords.set(index, medicalRecord);
                });
    }

    public void deleteMedicalRecord(String firstName, String lastName) {
        medicalRecords.removeIf(medicalRecord ->
                medicalRecord.getFirstName().equals(firstName) && medicalRecord.getLastName().equals(lastName));
    }
}