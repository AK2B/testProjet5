package com.SafetyNet.alerts.repository;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.SafetyNet.alerts.model.FireStation;

@Repository
public class FireStationRepositoryImpl implements FireStationRepository {
    private final List<FireStation> fireStations;

    @Autowired
    public FireStationRepositoryImpl(DataRepository dataRepository) throws IOException {
        fireStations = dataRepository.getData().getFireStations();
    }

    @Override
    public List<FireStation> getAllFireStations() {
        return fireStations;
    }

    @Override
    public FireStation getFireStationByAddress(String address) {
        for (FireStation fireStation : fireStations) {
            if (fireStation.getAddress().equals(address)) {
                return fireStation;
            }
        }
        return null;
    }

    @Override
    public List<FireStation> getFireStationByStation(int station) {
        List<FireStation> result = new ArrayList<>();
        for (FireStation fireStation : fireStations) {
            if (fireStation.getStation().equals(String.valueOf(station))) {
                result.add(fireStation);
            }
        }
        return result;
    }

    @Override
    public void addFireStation(FireStation fireStation) {
        fireStations.add(fireStation);
    }

    @Override
    public void updateFireStation(FireStation fireStation) {
        for (int i = 0; i < fireStations.size(); i++) {
            if (fireStations.get(i).getAddress().equals(fireStation.getAddress())) {
                fireStations.set(i, fireStation);
                return;
            }
        }
    }

    @Override
    public void deleteFireStation(String address) {
        fireStations.removeIf(fireStation -> fireStation.getAddress().equals(address));
    }

	
}
