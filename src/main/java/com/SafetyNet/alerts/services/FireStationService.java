package com.SafetyNet.alerts.services;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.SafetyNet.alerts.dao.FireStationDAO;
import com.SafetyNet.alerts.models.FireStation;

@Service
public class FireStationService {
	private final FireStationDAO fireStationDAO;

	@Autowired
	public FireStationService(FireStationDAO fireStationDAO) {
		this.fireStationDAO = fireStationDAO;
	}

	public List<FireStation> getAllFireStations() {
		return fireStationDAO.getAllFireStations();
	}

	public FireStation getFireStationByAddress(String address) {
		return getAllFireStations().stream().filter(fireStation -> fireStation.getAddress().equals(address)).findFirst()
				.orElse(null);
	}

	public List<FireStation> getFireStationByStation(String station) {
		return getAllFireStations().stream().filter(fireStation -> fireStation.getStation().equals(station))
				.collect(Collectors.toList());
	}

	public void addFireStation(FireStation fireStation) {
		fireStationDAO.addFireStation(fireStation);
	}

	public void updateFireStation(FireStation fireStation) {
		fireStationDAO.updateFireStation(fireStation);
	}

	public void deleteFireStation(String address) {
		fireStationDAO.deleteFireStation(address);
	}
}
