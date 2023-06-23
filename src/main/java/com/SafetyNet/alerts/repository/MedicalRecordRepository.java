package com.SafetyNet.alerts.repository;

import java.io.IOException;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.SafetyNet.alerts.model.MedicalRecord;

@Repository
public class MedicalRecordRepository {
    private List<MedicalRecord> medicalRecords;
    public MedicalRecordRepository(DataRepository dataRepository) throws IOException {
        medicalRecords = dataRepository.getData().getMedicalRecords();
    }

   
    public List<MedicalRecord> getAllMedicalRecords() {
        return medicalRecords;
    }

    
    public MedicalRecord getMedicalRecordByFullName(String firstName, String lastName) {
        for (MedicalRecord medicalRecord : medicalRecords) {
            if (medicalRecord.getFirstName().equals(firstName) && medicalRecord.getLastName().equals(lastName)) {
                return medicalRecord;
            }
        }
        return null;
    }

    
    public void addMedicalRecord(MedicalRecord medicalRecord) {
        medicalRecords.add(medicalRecord);
    }

    
    public void updateMedicalRecord(MedicalRecord medicalRecord) {
        for (int i = 0; i < medicalRecords.size(); i++) {
            MedicalRecord record = medicalRecords.get(i);
            if (record.getFirstName().equals(medicalRecord.getFirstName())
                    && record.getLastName().equals(medicalRecord.getLastName())) {
                medicalRecords.set(i, medicalRecord);
                return;
            }
        }
    }

    
    public void deleteMedicalRecord(String firstName, String lastName) {
        medicalRecords.removeIf(medicalRecord ->
                medicalRecord.getFirstName().equals(firstName) && medicalRecord.getLastName().equals(lastName));
    }
}
