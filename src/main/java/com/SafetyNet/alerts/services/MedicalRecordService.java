package com.SafetyNet.alerts.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.SafetyNet.alerts.model.MedicalRecord;
import com.SafetyNet.alerts.repository.MedicalRecordRepository;

@Service
public class MedicalRecordService {
    private MedicalRecordRepository medicalRecordRepository;

    public MedicalRecordService(MedicalRecordRepository medicalRecordRepository) {
        this.medicalRecordRepository = medicalRecordRepository;
    }

    public List<MedicalRecord> getAllMedicalRecords() {
        return medicalRecordRepository.getAllMedicalRecords();
    }

    public MedicalRecord getMedicalRecordByFullName(String firstName, String lastName) {
        return medicalRecordRepository.getMedicalRecordByFullName(firstName, lastName);
    }

    public void addMedicalRecord(MedicalRecord medicalRecord) {
        medicalRecordRepository.addMedicalRecord(medicalRecord);
    }

    public void updateMedicalRecord(MedicalRecord medicalRecord) {
        medicalRecordRepository.updateMedicalRecord(medicalRecord);
    }

    public void deleteMedicalRecord(String firstName, String lastName) {
        medicalRecordRepository.deleteMedicalRecord(firstName, lastName);
    }
}
