package com.SafetyNet.alerts.config;

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

import com.SafetyNet.alerts.models.FireStation;
import com.SafetyNet.alerts.models.MedicalRecord;
import com.SafetyNet.alerts.models.Person;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.annotation.PostConstruct;

@Repository
public class DataConfig {
    private static final Logger logger = LogManager.getLogger(DataConfig.class);

    private final ResourceLoader resourceLoader;
    private final ObjectMapper objectMapper;
    private List<Person> persons;
    private List<FireStation> fireStations;
    private List<MedicalRecord> medicalRecords;

    @Autowired
    public DataConfig(ResourceLoader resourceLoader, ObjectMapper objectMapper) {
        this.resourceLoader = resourceLoader;
        this.objectMapper = objectMapper;
    }

    @PostConstruct
    public void loadData() {
        try {
            Resource resource = resourceLoader.getResource("classpath:data.json");
            InputStream inputStream = resource.getInputStream();
            JsonNode rootNode = objectMapper.readTree(inputStream);

            extractPersons(rootNode);
            extractFireStations(rootNode);
            extractMedicalRecords(rootNode);

            logger.info("Données récupérées avec succès.");
        } catch (IOException e) {
            logger.error("Erreur lors de la récupération des données.", e);
        }
    }

    private void extractPersons(JsonNode rootNode) {
        persons = new ArrayList<>();
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
    }

    private void extractFireStations(JsonNode rootNode) {
        fireStations = new ArrayList<>();
        JsonNode fireStationsNode = rootNode.get("firestations");

        if (fireStationsNode != null && fireStationsNode.isArray()) {
            for (JsonNode fireStationNode : fireStationsNode) {
                String address = fireStationNode.get("address").asText();
                String station = fireStationNode.get("station").asText();

                FireStation fireStation = new FireStation(address, station);
                fireStations.add(fireStation);
            }
        }
    }

    private void extractMedicalRecords(JsonNode rootNode) {
        medicalRecords = new ArrayList<>();
        JsonNode medicalRecordsNode = rootNode.get("medicalrecords");

        if (medicalRecordsNode != null && medicalRecordsNode.isArray()) {
            for (JsonNode medicalRecordNode : medicalRecordsNode) {
                String firstName = medicalRecordNode.get("firstName").asText();
                String lastName = medicalRecordNode.get("lastName").asText();
                String birthdate = medicalRecordNode.get("birthdate").asText();

                List<String> medications = extractMedications(medicalRecordNode);
                List<String> allergies = extractAllergies(medicalRecordNode);

                MedicalRecord medicalRecord = new MedicalRecord(firstName, lastName, birthdate, medications, allergies);
                medicalRecords.add(medicalRecord);
            }
        }
    }

    private List<String> extractMedications(JsonNode medicalRecordNode) {
        List<String> medications = new ArrayList<>();
        JsonNode medicationsNode = medicalRecordNode.get("medications");

        if (medicationsNode != null && medicationsNode.isArray()) {
            for (JsonNode medicationNode : medicationsNode) {
                medications.add(medicationNode.asText());
            }
        }

        return medications;
    }

    private List<String> extractAllergies(JsonNode medicalRecordNode) {
        List<String> allergies = new ArrayList<>();
        JsonNode allergiesNode = medicalRecordNode.get("allergies");

        if (allergiesNode != null && allergiesNode.isArray()) {
            for (JsonNode allergyNode : allergiesNode) {
                allergies.add(allergyNode.asText());
            }
        }

        return allergies;
    }

    public List<Person> getPersons() {
        return persons;
    }

    public List<FireStation> getFireStations() {
        return fireStations;
    }

    public List<MedicalRecord> getMedicalRecords() {
        return medicalRecords;
    }

}
