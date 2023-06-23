package com.SafetyNet.alerts.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.SafetyNet.alerts.model.FireStation;
import com.SafetyNet.alerts.repository.FireStationRepository;

@Service
public class FireStationService {
	 private final FireStationRepository fireStationRepository;
	    

    public FireStationService(FireStationRepository fireStationRepository) {
    	this.fireStationRepository = fireStationRepository;
       
    }

    public List<FireStation> getAllFireStations() {
        return fireStationRepository.getAllFireStations();
    }

    public FireStation getFireStationByAddress(String address) {
        return fireStationRepository.getFireStationByAddress(address);
    }

    public List<FireStation> getFireStationByStation(int station) {
        return fireStationRepository.getFireStationByStation(station);
    }

    public void addFireStation(FireStation fireStation) {
        fireStationRepository.addFireStation(fireStation);
    }

    public void updateFireStation(FireStation fireStation) {
        fireStationRepository.updateFireStation(fireStation);
    }

    public void deleteFireStation(String address) {
        fireStationRepository.deleteFireStation(address);
    }

}

