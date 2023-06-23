package com.SafetyNet.alerts.repository;

import java.util.List;

import com.SafetyNet.alerts.model.FireStation;

public interface FireStationRepository {
    List<FireStation> getAllFireStations();
    FireStation getFireStationByAddress(String address);
    List<FireStation> getFireStationByStation(int stationNumber);
    void addFireStation(FireStation fireStation);
    void updateFireStation(FireStation fireStation);
    void deleteFireStation(String address);
    
}
