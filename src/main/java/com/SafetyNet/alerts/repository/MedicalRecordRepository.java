package com.SafetyNet.alerts.repository;

import java.util.List;

import com.SafetyNet.alerts.model.MedicalRecord;

public interface MedicalRecordRepository {
    List<MedicalRecord> getAllMedicalRecords();
    MedicalRecord getMedicalRecordByFullName(String firstName, String lastName);
    void addMedicalRecord(MedicalRecord medicalRecord);
    void updateMedicalRecord(MedicalRecord medicalRecord);
    void deleteMedicalRecord(String firstName, String lastName);
}