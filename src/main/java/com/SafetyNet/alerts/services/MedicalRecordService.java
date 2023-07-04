package com.SafetyNet.alerts.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.SafetyNet.alerts.dao.MedicalRecordDAO;
import com.SafetyNet.alerts.models.MedicalRecord;

@Service
public class MedicalRecordService {
	private MedicalRecordDAO medicalRecordDAO;

	@Autowired
	public MedicalRecordService(MedicalRecordDAO medicalRecordDAO) {
		this.medicalRecordDAO = medicalRecordDAO;
	}

	public List<MedicalRecord> getAllMedicalRecords() {
		return medicalRecordDAO.getAllMedicalRecords();
	}

	public MedicalRecord getMedicalRecordByFullName(String firstName, String lastName) {
		return getAllMedicalRecords().stream().filter(medicalRecord -> medicalRecord.getFirstName().equals(firstName)
				&& medicalRecord.getLastName().equals(lastName)).findFirst().orElse(null);
	}

	public void addMedicalRecord(MedicalRecord medicalRecord) {
		medicalRecordDAO.addMedicalRecord(medicalRecord);
	}

	public void updateMedicalRecord(MedicalRecord medicalRecord) {
		medicalRecordDAO.updateMedicalRecord(medicalRecord);
	}

	public void deleteMedicalRecord(String firstName, String lastName) {
		medicalRecordDAO.deleteMedicalRecord(firstName, lastName);
	}
}
