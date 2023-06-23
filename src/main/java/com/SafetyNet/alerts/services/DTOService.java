package com.SafetyNet.alerts.services;

import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import com.SafetyNet.alerts.DTO.ChildAlertDTO;
import com.SafetyNet.alerts.DTO.ChildDTO;
import com.SafetyNet.alerts.DTO.CommunityEmailDTO;
import com.SafetyNet.alerts.DTO.FireDTO;
import com.SafetyNet.alerts.DTO.FireStationCoverageDTO;
import com.SafetyNet.alerts.DTO.FloodDTO;
import com.SafetyNet.alerts.DTO.InfoPersonDTO;
import com.SafetyNet.alerts.DTO.PersonCoverageDTO;
import com.SafetyNet.alerts.DTO.PersonDTO;
import com.SafetyNet.alerts.DTO.PersonFireDTO;
import com.SafetyNet.alerts.DTO.PersonInfoDTO;
import com.SafetyNet.alerts.DTO.PhoneAlertDTO;
import com.SafetyNet.alerts.model.FireStation;
import com.SafetyNet.alerts.model.MedicalRecord;
import com.SafetyNet.alerts.model.Person;
import com.SafetyNet.alerts.repository.FireStationRepository;
import com.SafetyNet.alerts.repository.MedicalRecordRepository;
import com.SafetyNet.alerts.repository.PersonRepository;

@Service
public class DTOService {
    private static final Logger logger = LogManager.getLogger(DTOService.class);

    private PersonRepository personRepository;
    private FireStationRepository fireStationRepository;
    private MedicalRecordRepository medicalRecordRepository;

    public DTOService(PersonRepository personRepository, FireStationRepository fireStationRepository,
            MedicalRecordRepository medicalRecordRepository) {
        this.personRepository = personRepository;
        this.fireStationRepository = fireStationRepository;
        this.medicalRecordRepository = medicalRecordRepository;
    }

    /**
     * Cette méthode retourne les informations sur les personnes correspondant au prénom et au nom spécifiés.
     *
     * @param firstName le prénom de la personne
     * @param lastName  le nom de la personne
     * @return une liste d'objets PersonInfoDTO contenant les informations demandées
     */
    public List<PersonInfoDTO> getPersonInfoDTO(String firstName, String lastName) {
        logger.info("Recherche des informations pour la personne : " + firstName + " " + lastName);

        List<PersonInfoDTO> personInfoDTOs = new ArrayList<>();

        try {
            // Récupérer toutes les personnes depuis le référentiel
            List<Person> persons = personRepository.getAllPersons();

            for (Person person : persons) {
                if (person.getFirstName().equals(firstName) && person.getLastName().equals(lastName)) {
                    // Récupérer le dossier médical de la personne
                    MedicalRecord medicalRecord = medicalRecordRepository.getMedicalRecordByFullName(person.getFirstName(), person.getLastName());

                    if (medicalRecord != null) {
                        // Créer un objet PersonMedicalInfo avec les informations de base de la personne
                    	InfoPersonDTO infoPersonDTO = new InfoPersonDTO(person.getFirstName(), person.getLastName(), person.getAddress(), person.getEmail());

                        // Calculer l'âge de la personne
                        int age = calculateAge(medicalRecord.getBirthdate());

                        // Créer un objet PersonInfoDTO avec les informations de la personne et le dossier médical
                        PersonInfoDTO personInfoDTO = new PersonInfoDTO(infoPersonDTO, age, medicalRecord.getMedications(), medicalRecord.getAllergies());
                        personInfoDTOs.add(personInfoDTO);
                    }
                }
            }

            logger.info("Informations récupérées avec succès pour la personne : " + firstName + " " + lastName);
        } catch (Exception e) {
            logger.error("Erreur lors de la récupération des informations pour la personne : " + firstName + " " + lastName, e);
            // Lancer une exception personnalisée ou traiter l'erreur selon les besoins
        }

        return personInfoDTOs;
    }
    
    
    
    /**
     * Calcule l'âge à partir de la date de naissance.
     *
     * @param birthDate la date de naissance (au format "MM/dd/yyyy")
     * @return l'âge calculé
     */
    public int calculateAge(String birthDate) {
        logger.debug("Calcul de l'âge pour la date de naissance : " + birthDate);

        int age = 0;

        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");
            LocalDate parsedDate = LocalDate.parse(birthDate, formatter);
            LocalDate currentDate = LocalDate.now();
            Period agePeriod = Period.between(parsedDate, currentDate);
            age = agePeriod.getYears();

            logger.debug("Âge calculé : " + age);
        } catch (Exception e) {
            logger.error("Erreur lors du calcul de l'âge pour la date de naissance : " + birthDate, e);
            // Lancer une exception personnalisée ou traiter l'erreur selon les besoins
        }

        return age;
    }

    /**
     * Renvoie une liste d'enfants habitant à une adresse donnée, avec les membres du foyer.
     *
     * @param address l'adresse pour laquelle récupérer les informations
     * @return un objet ChildAlertDTO contenant la liste des enfants et les membres du foyer
     * @throws Exception si une erreur se produit lors de la récupération des informations
     */
    public ChildAlertDTO getChildAlert(String address) throws Exception {
        List<ChildDTO> children = new ArrayList<>();
        List<Person> householdMembers = new ArrayList<>();

        try {
            List<Person> persons = personRepository.getAllPersons();

            for (Person person : persons) {
                if (person.getAddress().equals(address)) {
                    MedicalRecord medicalRecord = medicalRecordRepository.getMedicalRecordByFullName(person.getFirstName(), person.getLastName());
                    int age = calculateAge(medicalRecord.getBirthdate());

                    if (age <= 18) {
                        ChildDTO childDTO = new ChildDTO(person.getFirstName(), person.getLastName(), age);
                        children.add(childDTO);
                    } else {
                        householdMembers.add(person);
                    }
                }
            }

            logger.info("La méthode getChildAlert a été exécutée avec succès.");
        } catch (Exception e) {
            logger.error("Une erreur s'est produite lors de l'exécution de la méthode getChildAlert.", e);
            throw new Exception("Une erreur s'est produite lors de la récupération des informations.");
        }

        return new ChildAlertDTO(children, householdMembers);
    }


    
    /**
     * Récupère les numéros de téléphone des personnes liées à une caserne de pompiers spécifiée.
     *
     * @param fireStationNumber le numéro de la caserne de pompiers
     * @return un objet PhoneAlertDTO contenant les numéros de téléphone
     * @throws AlertServiceException en cas d'erreur lors de la récupération des numéros de téléphone
     */
    
    
    /**
     * Cette méthode retourne les numéros de téléphone des personnes associées à une caserne de pompiers spécifiée.
     *
     * @param fireStationNumber le numéro de la caserne de pompiers
     * @return une instance de PhoneAlertDTO contenant les numéros de téléphone
     */
    public PhoneAlertDTO getPhoneAlert(int fireStationNumber) {
        try {
            List<FireStation> fireStations = fireStationRepository.getFireStationByStation(fireStationNumber);
            List<String> phoneNumbers = new ArrayList<>();

            for (FireStation fireStation : fireStations) {
                String fireStationAddress = fireStation.getAddress();
                List<Person> persons = personRepository.findByAddress(fireStationAddress);

                for (Person person : persons) {
                    phoneNumbers.add(person.getPhone());
                }
            }

            PhoneAlertDTO phoneAlertDTO = new PhoneAlertDTO();
            phoneAlertDTO.setPhoneNumbers(phoneNumbers);

            logger.info("Numéros de téléphone récupérés avec succès pour la caserne de pompiers n° : " + fireStationNumber);
            return phoneAlertDTO;
        } catch (Exception e) {
            logger.error("Erreur lors de la récupération des numéros de téléphone pour la caserne de pompiers n° : " + fireStationNumber, e);
            // Gérer l'erreur ou la propager selon vos besoins
            // ...
            return null;
        }
    }

    /**
     * Récupère les adresses e-mail des membres d'une communauté basée sur la ville.
     *
     * @param city la ville de la communauté
     * @return CommunityEmailDTO contenant les adresses e-mail des membres de la communauté
     * @throws IllegalArgumentException si la ville est nulle ou vide
     */
    public CommunityEmailDTO getCommunityEmails(String city) throws IllegalArgumentException {
        if (city == null || city.isEmpty()) {
            throw new IllegalArgumentException("La ville ne peut pas être nulle ou vide.");
        }

        List<Person> persons;
        try {
            persons = personRepository.findByCity(city);
        } catch (Exception e) {
            logger.error("Erreur lors de la récupération des personnes par ville.", e);
            throw new RuntimeException("Une erreur est survenue lors de la récupération des personnes par ville.", e);
        }

        List<String> emails = new ArrayList<>();
        for (Person person : persons) {
            emails.add(person.getEmail());
        }

        CommunityEmailDTO communityEmailDTO = new CommunityEmailDTO();
        communityEmailDTO.setEmails(emails);

        logger.info("Adresses e-mail récupérées avec succès pour la ville : " + city);

        return communityEmailDTO;
    }
    
    /**
     * Cette méthode retourne les informations relatives à un incendie à une adresse spécifiée.
     *
     * @param address l'adresse où l'incendie s'est produit
     * @return une instance de FireDTO contenant les informations sur les personnes et la caserne de pompiers
     */
    public FireDTO getFireInformation(String address) {
        try {
            // Rechercher les personnes associées à l'adresse spécifiée
            List<Person> persons = personRepository.findByAddress(address);
            if (persons.isEmpty()) {
                return null;
            }

            // Récupérer la caserne de pompiers associée à l'adresse
            FireStation fireStation = fireStationRepository.getFireStationByAddress(address);
            if (fireStation == null) {
                return null;
            }

            // Créer une liste de PersonFireDTO pour stocker les informations sur les personnes
            List<PersonFireDTO> personFireDTOs = new ArrayList<>();

            for (Person person : persons) {
                // Récupérer le dossier médical de la personne
                MedicalRecord medicalRecord = medicalRecordRepository.getMedicalRecordByFullName(person.getFirstName(), person.getLastName());

                // Créer un nouvel objet PersonFireDTO pour stocker les informations sur la personne
                String firstName = null;
    			String lastName = null;
    			String phone = null;
    			int age = 0;
    			PersonFireDTO personFireDTO = new PersonFireDTO(firstName, lastName, phone, age, medicalRecord);
                personFireDTO.setFirstName(person.getFirstName());
                personFireDTO.setLastName(person.getLastName());
                personFireDTO.setPhone(person.getPhone());
                personFireDTO.setAge(calculateAge(medicalRecord.getBirthdate()));
                personFireDTO.setMedications(medicalRecord.getMedications());
                personFireDTO.setAllergies(medicalRecord.getAllergies());

                personFireDTOs.add(personFireDTO);
            }

            // Créer un objet FireDTO avec la liste de PersonFireDTO et le numéro de la caserne de pompiers
            FireDTO fireDTO = new FireDTO(personFireDTOs, fireStation.getStation());

            logger.info("Informations sur l'incendie récupérées avec succès pour l'adresse : " + address);
            return fireDTO;
        } catch (Exception e) {
            logger.error("Erreur lors de la récupération des informations sur l'incendie pour l'adresse : " + address, e);
            // Gérer l'erreur ou la propager selon vos besoins
            // ...
            return null;
        }
    }

  
	    
    /**
     * Récupère les informations des personnes couvertes par la caserne de pompiers correspondante.
     *
     * @param fireStationNumber Le numéro de la caserne de pompiers
     * @return Liste des personnes couvertes par la caserne de pompiers avec leurs informations
     */
    public List<FireStationCoverageDTO> getFireStationCoverage(int fireStationNumber) {
        List<String> fireStationAddresses = new ArrayList<>();
        List<Person> persons = personRepository.getAllPersons();
        List<FireStationCoverageDTO> fireStationCoverages = new ArrayList<>();

        try {
            // Trouver toutes les adresses des casernes avec le numéro donné
            for (FireStation fireStation : fireStationRepository.getAllFireStations()) {
                if (fireStation.getStation().equals(String.valueOf(fireStationNumber))) {
                    fireStationAddresses.add(fireStation.getAddress());
                }
            }

            // Parcourir toutes les personnes vivant à ces adresses et regrouper les informations demandées
            for (String address : fireStationAddresses) {
                int numAdults = 0;
                int numChildren = 0;
                List<PersonCoverageDTO> personsCovered = new ArrayList<>();

                for (Person person : persons) {
                    if (person.getAddress().equals(address)) {
                        MedicalRecord medicalRecord = medicalRecordRepository.getMedicalRecordByFullName(person.getFirstName(), person.getLastName());
                        int age = calculateAge(medicalRecord.getBirthdate());

                        if (age >= 18) {
                            numAdults++;
                        } else {
                            numChildren++;
                        }

                        personsCovered.add(new PersonCoverageDTO(
                                person.getFirstName(),
                                person.getLastName(),
                                person.getAddress(),
                                person.getPhone()
                        ));
                    }
                }

                FireStationCoverageDTO fireStationCoverageDTO = new FireStationCoverageDTO();
                fireStationCoverageDTO.setAddress(address);
                fireStationCoverageDTO.setNumAdults(numAdults);
                fireStationCoverageDTO.setNumChildren(numChildren);
                fireStationCoverageDTO.setPersons(personsCovered);
                fireStationCoverages.add(fireStationCoverageDTO);
            }

            // Journal des réponses réussies au niveau Info
            logger.info("La méthode getFireStationCoverage a été exécutée avec succès.");

        } catch (Exception e) {
            // Enregistrer les erreurs ou exceptions au niveau Erreur
            logger.error("Une erreur s'est produite lors de l'exécution de la méthode getFireStationCoverage.", e);
        }

        return fireStationCoverages;
    }

    /**
     * Récupère une liste de casernes affectées par des inondations avec les informations des habitants correspondants.
     *
     * @param stationNumbers La liste des numéros de caserne.
     * @return Liste de FloodDTO contenant les adresses et les informations des habitants.
     */
    public List<FloodDTO> getFloodStations(List<Integer> stationNumbers) {
        List<FloodDTO> floodStations = new ArrayList<>();

        try {
            // Trouver toutes les adresses correspondant aux numéros de station donnés
            List<String> addresses = new ArrayList<>();
            for (FireStation fireStation : fireStationRepository.getAllFireStations()) {
                if (stationNumbers.contains(Integer.parseInt(fireStation.getStation()))) {
                    addresses.add(fireStation.getAddress());
                }
            }

            // Parcourir chaque adresse et trouver les personnes correspondantes
            for (String address : addresses) {
                List<PersonDTO> persons = new ArrayList<>();

                for (Person person : personRepository.getAllPersons()) {
                    if (person.getAddress().equals(address)) {
                        MedicalRecord medicalRecord = medicalRecordRepository.getMedicalRecordByFullName(person.getFirstName(), person.getLastName());
                        List<String> medications = medicalRecord.getMedications();
                        List<String> allergies = medicalRecord.getAllergies();

                        PersonDTO personDTO = new PersonDTO(person.getFirstName(), person.getLastName(), person.getPhone(), calculateAge(medicalRecord.getBirthdate()), medications, allergies);
                        persons.add(personDTO);
                    }
                }

                FloodDTO floodDTO = new FloodDTO(address, persons);
                floodStations.add(floodDTO);
            }

            // Journal des réponses réussies au niveau Info
            logger.info("La méthode getFloodStations a été exécutée avec succès.");
        } catch (Exception e) {
            // Enregistrer les erreurs ou exceptions au niveau Erreur
            logger.error("Une erreur s'est produite lors de l'exécution de la méthode getFloodStations.", e);
        }
        
        return floodStations;
    }
}

	
	
	
	

