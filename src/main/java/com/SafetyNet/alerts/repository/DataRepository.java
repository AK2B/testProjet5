package com.SafetyNet.alerts.repository;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Repository;

import com.SafetyNet.alerts.model.Data;
import com.SafetyNet.alerts.model.FireStation;
import com.SafetyNet.alerts.model.MedicalRecord;
import com.SafetyNet.alerts.model.Person;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.annotation.PostConstruct;

@Repository
public class DataRepository {
    private static final Logger logger = LogManager.getLogger(DataRepository.class);

    private final ResourceLoader resourceLoader;
    private final ObjectMapper objectMapper;
    private Data data;

    @Autowired
    public DataRepository(ResourceLoader resourceLoader, ObjectMapper objectMapper) {
        this.resourceLoader = resourceLoader;
        this.objectMapper = objectMapper;
    }

    @PostConstruct
    public void loadData() {
        data = getData();
    }

    /**
     * Récupère les données à partir du fichier JSON.
     *
     * @return Les données récupérées, ou null en cas d'erreur.
     */
    public Data getData() {
        if (data != null) {
            return data; // Retourne les données déjà chargées si elles existent
        }

        try {
            // Charger data.json via resourceLoader
            Resource resource = resourceLoader.getResource("classpath:data.json");
            InputStream inputStream = resource.getInputStream();

            // Désérialiser le JSON en tant qu'arbre d'instances JsonNode
            JsonNode rootNode = objectMapper.readTree(inputStream);

         // Traiter les données lues
            List<Person> persons = new ArrayList<>();
            JsonNode personsNode = rootNode.get("persons");

            if (personsNode != null && personsNode.isArray()) {
                for (JsonNode personNode : personsNode) {
                    String firstName = personNode.get("firstName").asText();
                    String lastName = personNode.get("lastName").asText();
                    String address = personNode.get("address").asText();
                    String city = personNode.get("city").asText();
                    String zip = personNode.get("zip").asText();
                    String phone = personNode.get("phone").asText();
                    String email = personNode.get("email").asText();

                    Person person = new Person(firstName, lastName, address, city, zip, phone, email);
                    persons.add(person);     
                }
                               
            }  
           
            List<FireStation> fireStations = new ArrayList<>();
            JsonNode fireStationsNode = rootNode.get("firestations");
            if (fireStationsNode != null && fireStationsNode.isArray()) {
                for (JsonNode fireStationNode : fireStationsNode) {
                    String address = fireStationNode.get("address").asText();
                    String station = fireStationNode.get("station").asText();

                    FireStation fireStation = new FireStation(address, station);
                    fireStations.add(fireStation);       
                }
            }
           
            List<MedicalRecord> medicalRecords = new ArrayList<>();
            JsonNode medicalRecordsNode = rootNode.get("medicalrecords");
            if (medicalRecordsNode != null && medicalRecordsNode.isArray()) {
                for (JsonNode medicalRecordNode : medicalRecordsNode) {
                    String firstName = medicalRecordNode.get("firstName").asText();
                    String lastName = medicalRecordNode.get("lastName").asText();
                    String birthdate = medicalRecordNode.get("birthdate").asText();

                    List<String> medications = new ArrayList<>();
                    JsonNode medicationsNode = medicalRecordNode.get("medications");
                    if (medicationsNode != null && medicationsNode.isArray()) {
                        for (JsonNode medicationNode : medicationsNode) {
                            medications.add(medicationNode.asText());
                        }
                    }

                    List<String> allergies = new ArrayList<>();
                    JsonNode allergiesNode = medicalRecordNode.get("allergies");
                    if (allergiesNode != null && allergiesNode.isArray()) {
                        for (JsonNode allergyNode : allergiesNode) {
                            allergies.add(allergyNode.asText());
                        }
                    }

                    MedicalRecord medicalRecord = new MedicalRecord(firstName, lastName, birthdate, medications, allergies);
                    medicalRecords.add(medicalRecord);                     
                } 
            }


            logger.info("Données récupérées avec succès.");
            data = new Data(persons, fireStations, medicalRecords);
            return data;
        } catch (IOException e) {
            logger.error("Erreur lors de la récupération des données.", e);
            return null;
        }
    }
}

