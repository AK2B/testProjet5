package com.SafetyNet.alerts.dao;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.SafetyNet.alerts.config.DataConfig;
import com.SafetyNet.alerts.models.FireStation;

@Repository
public class FireStationDAO {

    private final List<FireStation> fireStations;
    @Autowired
    public FireStationDAO(DataConfig dataConfig) {
        this.fireStations = dataConfig.getFireStations();
    }

    // CRUD operations only

    public List<FireStation> getAllFireStations() {
        return new ArrayList<>(fireStations);
    }

    public void addFireStation(FireStation fireStation) {
        fireStations.add(fireStation);
    }

    public void updateFireStation(FireStation fireStation) {
        fireStations.stream()
                .filter(station -> station.getAddress().equals(fireStation.getAddress()))
                .findFirst()
                .ifPresent(station -> {
                    int index = fireStations.indexOf(station);
                    fireStations.set(index, fireStation);
                });
    }

    public void deleteFireStation(String address) {
        fireStations.removeIf(fireStation -> fireStation.getAddress().equals(address));
    }
}
