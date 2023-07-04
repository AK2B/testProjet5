package com.SafetyNet.alerts.services;

import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import com.SafetyNet.alerts.models.FireStation;
import com.SafetyNet.alerts.models.MedicalRecord;
import com.SafetyNet.alerts.models.Person;
import com.SafetyNet.alerts.models.alerts.Child;
import com.SafetyNet.alerts.models.alerts.ChildAlert;
import com.SafetyNet.alerts.models.alerts.CommunityEmail;
import com.SafetyNet.alerts.models.alerts.Fire;
import com.SafetyNet.alerts.models.alerts.FireStationCoverage;
import com.SafetyNet.alerts.models.alerts.Flood;
import com.SafetyNet.alerts.models.alerts.InfoPerson;
import com.SafetyNet.alerts.models.alerts.PersonCoverage;
import com.SafetyNet.alerts.models.alerts.PersonFire;
import com.SafetyNet.alerts.models.alerts.PersonFlood;
import com.SafetyNet.alerts.models.alerts.PersonInfo;
import com.SafetyNet.alerts.models.alerts.PhoneAlert;

@Service
public class AlertsService {
	private static final Logger logger = LogManager.getLogger(AlertsService.class);

	private PersonService personService;
	private FireStationService fireStationService;
	private MedicalRecordService medicalRecordService;

	public AlertsService(PersonService personService, FireStationService fireStationService,
			MedicalRecordService medicalRecordService) {
		this.personService = personService;
		this.fireStationService = fireStationService;
		this.medicalRecordService = medicalRecordService;
	}

	/**
	 * Cette méthode retourne les informations sur les personnes correspondant au
	 * prénom et au nom spécifiés.
	 *
	 * @param firstName le prénom de la personne
	 * @param lastName  le nom de la personne
	 * @return une liste d'objets PersonInfo contenant les informations demandées
	 */
	public List<PersonInfo> getPersonInfo(String firstName, String lastName) {
		logger.info("Recherche des informations pour la personne : " + firstName + " " + lastName);

		List<PersonInfo> personInfos = new ArrayList<>();

		try {
			// Récupérer toutes les personnes depuis le référentiel
			List<Person> persons = personService.getAllPersons();

			for (Person person : persons) {
				if (person.getFirstName().equals(firstName) && person.getLastName().equals(lastName)) {
					// Récupérer le dossier médical de la personne
					MedicalRecord medicalRecord = medicalRecordService.getMedicalRecordByFullName(person.getFirstName(),
							person.getLastName());

					if (medicalRecord != null) {
						// Créer un objet PersonMedicalInfo avec les informations de base de la personne
						InfoPerson infoPerson = new InfoPerson(person.getFirstName(), person.getLastName(),
								person.getAddress(), person.getEmail());

						// Calculer l'âge de la personne
						int age = calculateAge(medicalRecord.getBirthdate());

						// Créer un objet PersonInfo avec les informations de la personne et le
						// dossier médical
						PersonInfo personInfo = new PersonInfo(infoPerson, age,
								medicalRecord.getMedications(), medicalRecord.getAllergies());
						personInfos.add(personInfo);
					}
				}
			}

			logger.info("Informations récupérées avec succès pour la personne : " + firstName + " " + lastName);
		} catch (Exception e) {
			logger.error(
					"Erreur lors de la récupération des informations pour la personne : " + firstName + " " + lastName,
					e);
			// Lancer une exception personnalisée ou traiter l'erreur selon les besoins
		}

		return personInfos;
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
	 * Renvoie une liste d'enfants habitant à une adresse donnée, avec les membres
	 * du foyer.
	 *
	 * @param address l'adresse pour laquelle récupérer les informations
	 * @return un objet ChildAlert contenant la liste des enfants et les membres
	 *         du foyer
	 * @throws Exception si une erreur se produit lors de la récupération des
	 *                   informations
	 */
	public ChildAlert getChildAlert(String address) throws Exception {
		List<Child> children = new ArrayList<>();
		List<Person> householdMembers = new ArrayList<>();

		try {
			List<Person> persons = personService.getAllPersons();

			for (Person person : persons) {
				if (person.getAddress().equals(address)) {
					MedicalRecord medicalRecord = medicalRecordService.getMedicalRecordByFullName(person.getFirstName(),
							person.getLastName());
					int age = calculateAge(medicalRecord.getBirthdate());

					if (age <= 18) {
						Child child = new Child(person.getFirstName(), person.getLastName(), age);
						children.add(child);
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

		return new ChildAlert(children, householdMembers);
	}

	/**
	 * Récupère les numéros de téléphone des personnes liées à une caserne de
	 * pompiers spécifiée.
	 *
	 * @param fireStationNumber le numéro de la caserne de pompiers
	 * @return un objet PhoneAlert contenant les numéros de téléphone
	 * @throws AlertServiceException en cas d'erreur lors de la récupération des
	 *                               numéros de téléphone
	 */

	/**
	 * Cette méthode retourne les numéros de téléphone des personnes associées à une
	 * caserne de pompiers spécifiée.
	 *
	 * @param fireStationNumber le numéro de la caserne de pompiers
	 * @return une instance de PhoneAlert contenant les numéros de téléphone
	 */
	public PhoneAlert getPhoneAlert(String fireStationNumber) {
		try {
			List<FireStation> fireStations = fireStationService.getFireStationByStation(fireStationNumber);
			List<String> phoneNumbers = new ArrayList<>();

			for (FireStation fireStation : fireStations) {
				String fireStationAddress = fireStation.getAddress();
				List<Person> persons = personService.getPersonByAddress(fireStationAddress);

				for (Person person : persons) {
					phoneNumbers.add(person.getPhone());
				}
			}

			PhoneAlert phoneAlert = new PhoneAlert();
			phoneAlert.setPhoneNumbers(phoneNumbers);

			logger.info(
					"Numéros de téléphone récupérés avec succès pour la caserne de pompiers n° : " + fireStationNumber);
			return phoneAlert;
		} catch (Exception e) {
			logger.error("Erreur lors de la récupération des numéros de téléphone pour la caserne de pompiers n° : "
					+ fireStationNumber, e);
			// Gérer l'erreur ou la propager selon vos besoins
			// ...
			return null;
		}
	}

	/**
	 * Récupère les adresses e-mail des membres d'une communauté basée sur la ville.
	 *
	 * @param city la ville de la communauté
	 * @return CommunityEmail contenant les adresses e-mail des membres de la
	 *         communauté
	 * @throws IllegalArgumentException si la ville est nulle ou vide
	 */
	public CommunityEmail getCommunityEmails(String city) throws IllegalArgumentException {
		if (city == null || city.isEmpty()) {
			throw new IllegalArgumentException("La ville ne peut pas être nulle ou vide.");
		}

		List<Person> persons;
		try {
			persons = personService.getPersonByCity(city);
		} catch (Exception e) {
			logger.error("Erreur lors de la récupération des personnes par ville.", e);
			throw new RuntimeException("Une erreur est survenue lors de la récupération des personnes par ville.", e);
		}

		List<String> emails = new ArrayList<>();
		for (Person person : persons) {
			emails.add(person.getEmail());
		}

		CommunityEmail communityEmail = new CommunityEmail();
		communityEmail.setEmails(emails);

		logger.info("Adresses e-mail récupérées avec succès pour la ville : " + city);

		return communityEmail;
	}

	/**
	 * Cette méthode retourne les informations relatives à un incendie à une adresse
	 * spécifiée.
	 *
	 * @param address l'adresse où l'incendie s'est produit
	 * @return une instance de Fire contenant les informations sur les personnes
	 *         et la caserne de pompiers
	 */
	public Fire getFireInformation(String address) {
		try {
			// Rechercher les personnes associées à l'adresse spécifiée
			List<Person> persons = personService.getPersonByAddress(address);
			if (persons.isEmpty()) {
				return null;
			}

			// Récupérer la caserne de pompiers associée à l'adresse
			FireStation fireStation = fireStationService.getFireStationByAddress(address);
			if (fireStation == null) {
				return null;
			}

			// Créer une liste de PersonFire pour stocker les informations sur les
			// personnes
			List<PersonFire> personFires = new ArrayList<>();

			for (Person person : persons) {
				// Récupérer le dossier médical de la personne
				MedicalRecord medicalRecord = medicalRecordService.getMedicalRecordByFullName(person.getFirstName(),
						person.getLastName());

				// Créer un nouvel objet PersonFire pour stocker les informations sur la
				// personne
				String firstName = null;
				String lastName = null;
				String phone = null;
				int age = 0;
				PersonFire personFire = new PersonFire(firstName, lastName, phone, age, medicalRecord);
				personFire.setFirstName(person.getFirstName());
				personFire.setLastName(person.getLastName());
				personFire.setPhone(person.getPhone());
				personFire.setAge(calculateAge(medicalRecord.getBirthdate()));
				personFire.setMedications(medicalRecord.getMedications());
				personFire.setAllergies(medicalRecord.getAllergies());

				personFires.add(personFire);
			}

			// Créer un objet Fire avec la liste de PersonFire et le numéro de la
			// caserne de pompiers
			Fire fire = new Fire(personFires, fireStation.getStation());

			logger.info("Informations sur l'incendie récupérées avec succès pour l'adresse : " + address);
			return fire;
		} catch (Exception e) {
			logger.error("Erreur lors de la récupération des informations sur l'incendie pour l'adresse : " + address,
					e);
			// Gérer l'erreur ou la propager selon vos besoins
			// ...
			return null;
		}
	}

	/**
	 * Récupère les informations des personnes couvertes par la caserne de pompiers
	 * correspondante.
	 *
	 * @param fireStationNumber Le numéro de la caserne de pompiers
	 * @return Liste des personnes couvertes par la caserne de pompiers avec leurs
	 *         informations
	 */
	public List<FireStationCoverage> getFireStationCoverage(int fireStationNumber) {
		List<String> fireStationAddresses = new ArrayList<>();
		List<Person> persons = personService.getAllPersons();
		List<FireStationCoverage> fireStationCoverages = new ArrayList<>();

		try {
			// Trouver toutes les adresses des casernes avec le numéro donné
			for (FireStation fireStation : fireStationService.getAllFireStations()) {
				if (fireStation.getStation().equals(String.valueOf(fireStationNumber))) {
					fireStationAddresses.add(fireStation.getAddress());
				}
			}

			// Parcourir toutes les personnes vivant à ces adresses et regrouper les
			// informations demandées
			for (String address : fireStationAddresses) {
				int numAdults = 0;
				int numChildren = 0;
				List<PersonCoverage> personsCovered = new ArrayList<>();

				for (Person person : persons) {
					if (person.getAddress().equals(address)) {
						MedicalRecord medicalRecord = medicalRecordService
								.getMedicalRecordByFullName(person.getFirstName(), person.getLastName());
						int age = calculateAge(medicalRecord.getBirthdate());

						if (age >= 18) {
							numAdults++;
						} else {
							numChildren++;
						}

						personsCovered.add(new PersonCoverage(person.getFirstName(), person.getLastName(),
								person.getAddress(), person.getPhone()));
					}
				}

				FireStationCoverage fireStationCoverage = new FireStationCoverage();
				fireStationCoverage.setAddress(address);
				fireStationCoverage.setNumAdults(numAdults);
				fireStationCoverage.setNumChildren(numChildren);
				fireStationCoverage.setPersons(personsCovered);
				fireStationCoverages.add(fireStationCoverage);
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
	 * Récupère une liste de casernes affectées par des inondations avec les
	 * informations des habitants correspondants.
	 *
	 * @param stationNumbers La liste des numéros de caserne.
	 * @return Liste de Flood contenant les adresses et les informations des
	 *         habitants.
	 */
	public List<Flood> getFloodStations(List<Integer> stationNumbers) {
		List<Flood> floodStations = new ArrayList<>();

		try {
			// Trouver toutes les adresses correspondant aux numéros de station donnés
			List<String> addresses = new ArrayList<>();
			for (FireStation fireStation : fireStationService.getAllFireStations()) {
				if (stationNumbers.contains(Integer.parseInt(fireStation.getStation()))) {
					addresses.add(fireStation.getAddress());
				}
			}

			// Parcourir chaque adresse et trouver les personnes correspondantes
			for (String address : addresses) {
				List<PersonFlood> persons = new ArrayList<>();

				for (Person person : personService.getAllPersons()) {
					if (person.getAddress().equals(address)) {
						MedicalRecord medicalRecord = medicalRecordService
								.getMedicalRecordByFullName(person.getFirstName(), person.getLastName());
						List<String> medications = medicalRecord.getMedications();
						List<String> allergies = medicalRecord.getAllergies();

						PersonFlood personFlood = new PersonFlood(person.getFirstName(), person.getLastName(),
								person.getPhone(), calculateAge(medicalRecord.getBirthdate()), medications, allergies);
						persons.add(personFlood);
					}
				}

				Flood flood = new Flood(address, persons);
				floodStations.add(flood);
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