package com.SafetyNet.alerts.services;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import com.SafetyNet.alerts.model.Data;
import com.SafetyNet.alerts.repository.DataRepository;

@Service
public class DataService {
    public Logger logger = LogManager.getLogger(DataService.class);
    private final DataRepository dataRepository;
    
    public DataService(DataRepository dataRepository) {
    	this.dataRepository = dataRepository;
    }

    public Data getData() {
    	logger.info("Obtention des données...");
        return dataRepository.getData();
    }
    

}
