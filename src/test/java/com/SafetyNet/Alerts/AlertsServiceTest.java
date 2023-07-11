package com.SafetyNet.Alerts;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.SafetyNet.alerts.dao.FireStationDAO;
import com.SafetyNet.alerts.dao.MedicalRecordDAO;
import com.SafetyNet.alerts.dao.PersonDAO;
import com.SafetyNet.alerts.models.Child;
import com.SafetyNet.alerts.models.ChildAlert;
import com.SafetyNet.alerts.models.CommunityEmail;
import com.SafetyNet.alerts.models.Fire;
import com.SafetyNet.alerts.models.FireStation;
import com.SafetyNet.alerts.models.FireStationCoverage;
import com.SafetyNet.alerts.models.Flood;
import com.SafetyNet.alerts.models.MedicalRecord;
import com.SafetyNet.alerts.models.Person;
import com.SafetyNet.alerts.models.PersonCoverage;
import com.SafetyNet.alerts.models.PersonFlood;
import com.SafetyNet.alerts.models.PersonInfo;
import com.SafetyNet.alerts.models.PhoneAlert;
import com.SafetyNet.alerts.services.AlertsService;

public class AlertsServiceTest {

    @Mock
    private PersonDAO personDAO;

    @Mock
    private FireStationDAO fireStationDAO;

    @Mock
    private MedicalRecordDAO medicalRecordDAO;

    @InjectMocks
    private AlertsService alertsService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetPersonInfo() {
        
        Person person = new Person("John", "Boyd", "1509 Culver St", "Culver", "97451", "841-874-6512", "jaboyd@email.com");
        MedicalRecord medicalRecord = new MedicalRecord("John", "Boyd", "03/06/1984", new ArrayList<>(), new ArrayList<>());
        List<Person> persons = new ArrayList<>();
        persons.add(person);

        when(personDAO.getAllPersons()).thenReturn(persons);
        when(medicalRecordDAO.getMedicalRecordByFullName("John", "Boyd")).thenReturn(medicalRecord);

        List<PersonInfo> personInfos = alertsService.getPersonInfo("John", "Boyd");

        assertEquals(1, personInfos.size());
        PersonInfo personInfo = personInfos.get(0);
        assertEquals("John", personInfo.getInfoPerson().getFirstName());
        assertEquals("Boyd", personInfo.getInfoPerson().getLastName());
        assertEquals("1509 Culver St", personInfo.getInfoPerson().getAddress());
        assertEquals("jaboyd@email.com", personInfo.getInfoPerson().getEmail());
        assertEquals(39, personInfo.getAge());
        assertEquals(0, personInfo.getMedications().size());
        assertEquals(0, personInfo.getAllergies().size());

        verify(personDAO, times(1)).getAllPersons();
        verify(medicalRecordDAO, times(1)).getMedicalRecordByFullName("John", "Boyd");
    }

    @Test
    public void testCalculateAge() {
        String birthDate = "03/06/1984";

        int age = alertsService.calculateAge(birthDate);

        assertEquals(39, age);
    }
    
     
    
    @Test
    public void testGetChildAlert() throws Exception {
        // Adresse de test
        String address = "1509 Culver St";

        // Création de personnes
        Person person1 = new Person("John", "Boyd", address, "Culver", "97451", "841-874-6512", "john.boyd@example.com");
        Person person2 = new Person("Jane", "Smith", address, "Culver", "97451", "841-874-1234", "jane.smith@example.com");
        Person person3 = new Person("Robert", "Doe", "123 Main St", "Culver", "97451", "841-874-5678", "robert.doe@example.com");

        // Création de dossiers médicaux
        MedicalRecord medicalRecord1 = new MedicalRecord("John", "Boyd", "03/06/1984", new ArrayList<>(), new ArrayList<>());
        MedicalRecord medicalRecord2 = new MedicalRecord("Jane", "Smith", "02/01/2015", new ArrayList<>(), new ArrayList<>());
        MedicalRecord medicalRecord3 = new MedicalRecord("Robert", "Doe", "03/05/2016", new ArrayList<>(), new ArrayList<>());

        // Liste de personnes
        List<Person> persons = new ArrayList<>();
        persons.add(person1);
        persons.add(person2);
        persons.add(person3);

        // Configuration des méthodes simulées
        when(personDAO.getAllPersons()).thenReturn(persons);
        when(medicalRecordDAO.getMedicalRecordByFullName("John", "Boyd")).thenReturn(medicalRecord1);
        when(medicalRecordDAO.getMedicalRecordByFullName("Jane", "Smith")).thenReturn(medicalRecord2);
        when(medicalRecordDAO.getMedicalRecordByFullName("Robert", "Doe")).thenReturn(medicalRecord3);

        // Appel de la méthode à tester
        ChildAlert childAlert = alertsService.getChildAlert(address);

        // Vérification des résultats
        assertNotNull(childAlert);

        List<Child> expectedChildren = new ArrayList<>();

        // Ajouter les enfants qui habitent à l'adresse demandée
        for (Person person : persons) {
            if (person.getAddress().equals(address)) {
                MedicalRecord medicalRecord = medicalRecordDAO.getMedicalRecordByFullName(person.getFirstName(), person.getLastName());
                int age = alertsService.calculateAge(medicalRecord.getBirthdate());

                if (age <= 18) {
                    Child child = new Child(person.getFirstName(), person.getLastName(), age);
                    expectedChildren.add(child);
                }
            }
        }

        List<Person> expectedHouseholdMembers = new ArrayList<>();
        expectedHouseholdMembers.add(person1);

        assertEquals(expectedChildren.size(), childAlert.getChildren().size());
        assertEquals(expectedHouseholdMembers.size(), childAlert.getHouseholdMembers().size());

        for (int i = 0; i < expectedChildren.size(); i++) {
            Child expectedChild = expectedChildren.get(i);
            Child actualChild= childAlert.getChildren().get(i);

            assertEquals(expectedChild.getFirstName(), actualChild.getFirstName());
            assertEquals(expectedChild.getLastName(), actualChild.getLastName());
            assertEquals(expectedChild.getAge(), actualChild.getAge());
        }
        for (int i = 0; i < expectedHouseholdMembers.size(); i++) {
            Person expectedMember = expectedHouseholdMembers.get(i);
            Person actualMember = childAlert.getHouseholdMembers().get(i);

            assertEquals(expectedMember.getFirstName(), actualMember.getFirstName());
            assertEquals(expectedMember.getLastName(), actualMember.getLastName());
        }

        // Vérification des appels de méthodes simulées
        verify(personDAO, times(1)).getAllPersons();
        verify(medicalRecordDAO, times(2)).getMedicalRecordByFullName("John", "Boyd");
    }



    @Test
    public void testGetPhoneAlert() {
        String fireStationNumber = "1";
        String address1 = "1509 Culver St";
        String address2 = "123 Main St";

        FireStation fireStation1 = new FireStation(address1, fireStationNumber);
        FireStation fireStation2 = new FireStation(address2, fireStationNumber);

        Person person1 = new Person("John", "Boyd", address1, "Culver", "97451", "841-874-6512", "john.boyd@example.com");
        Person person2 = new Person("Jane", "Smith", address2, "Culver", "97451", "841-874-1234", "jane.smith@example.com");

        List<FireStation> fireStations = new ArrayList<>();
        fireStations.add(fireStation1);
        fireStations.add(fireStation2);

        List<Person> persons1 = new ArrayList<>();
        persons1.add(person1);

        List<Person> persons2 = new ArrayList<>();
        persons2.add(person2);

        when(fireStationDAO.getFireStationByStation(fireStationNumber)).thenReturn(fireStations);
        when(personDAO.getPersonByAddress(address1)).thenReturn(persons1);
        when(personDAO.getPersonByAddress(address2)).thenReturn(persons2);

        PhoneAlert phoneAlert = alertsService.getPhoneAlert(fireStationNumber);

        assertNotNull(phoneAlert);
        assertEquals(2, phoneAlert.getPhoneNumbers().size());
        assertTrue(phoneAlert.getPhoneNumbers().contains(person1.getPhone()));
        assertTrue(phoneAlert.getPhoneNumbers().contains(person2.getPhone()));

        verify(fireStationDAO, times(1)).getFireStationByStation(fireStationNumber);
        verify(personDAO, times(1)).getPersonByAddress(address1);
        verify(personDAO, times(1)).getPersonByAddress(address2);
    }
   
    @Test
    public void testGetCommunityEmails() {
        // Ville de test
        String city = "Culver";

        // Création de personnes
        Person person1 = new Person("John", "Boyd", "1509 Culver St", city, "97451", "841-874-6512", "john.boyd@example.com");
        Person person2 = new Person("Jane", "Smith", "1510 Culver St", city, "97451", "841-874-1234", "jane.smith@example.com");
        Person person3 = new Person("Robert", "Doe", "123 Main St", "Springfield", "12345", "555-123-4567", "robert.doe@example.com");

        // Liste de personnes
        List<Person> persons = new ArrayList<>();
        persons.add(person1);
        persons.add(person2);
        persons.add(person3);

        // Configuration de la méthode simulée
        when(personDAO.getPersonByCity(city)).thenReturn(persons);

        // Appel de la méthode à tester
        CommunityEmail communityEmail = alertsService.getCommunityEmails(city);

        // Vérification des résultats
        assertNotNull(communityEmail);

        List<String> expectedEmails = Arrays.asList("john.boyd@example.com", "jane.smith@example.com");
        assertEquals(expectedEmails.size(), communityEmail.getEmails().size());
        assertTrue(communityEmail.getEmails().containsAll(expectedEmails));

        // Vérification que seules les personnes de la ville sont présentes dans la liste
        for (Person person : persons) {
            if (person.getCity().equals(city)) {
                assertTrue(communityEmail.getEmails().contains(person.getEmail()));
            } else {
                assertFalse(communityEmail.getEmails().contains(person.getEmail()));
            }
        }

        // Vérification des appels de méthodes simulées
        verify(personDAO, times(1)).getPersonByCity(city);
    }

    @Test
    public void testGetFireInformation() {
        // Arrange
        String address = "1509 Culver St";

        // Mocking the personDAO
        List<Person> persons = new ArrayList<>();
        Person person = new Person("John", "Boyd", address, "Culver", "97451", "841-874-6512", "jaboyd@example.com");
        persons.add(person);
        when(personDAO.getPersonByAddress(address)).thenReturn(persons);

        // Mocking the fireStationDAO
        FireStation fireStation = new FireStation(address, "1");
        when(fireStationDAO.getFireStationByAddress(address)).thenReturn(fireStation);

        // Mocking the medicalRecordDAO
        MedicalRecord medicalRecord = new MedicalRecord("John", "Boyd", "03/06/1984", new ArrayList<>(), new ArrayList<>());
        when(medicalRecordDAO.getMedicalRecordByFullName("John", "Boyd")).thenReturn(medicalRecord);

        // Act
        Fire result = alertsService.getFireInformation(address);

        // Assert
        assertEquals(1, result.getPersonFires().size());
        assertEquals("1", result.getFireStationNumber());
    }
    
    @Test
    public void testGetFireStationCoverage() {
        // Arrange
        int fireStationNumber = 1;

        // Mocking the fireStationDAO
        List<FireStation> fireStations = Arrays.asList(
            new FireStation("1509 Culver St", "1"),
            new FireStation("123 Main St", "1"),
            new FireStation("456 Elm St", "2")
        );
        when(fireStationDAO.getAllFireStations()).thenReturn(fireStations);

        // Mocking the personDAO
        List<Person> persons = Arrays.asList(
            new Person("John", "Boyd", "1509 Culver St", "Culver", "97451", "841-874-6512", "john@example.com"),
            new Person("Jane", "Smith", "123 Main St", "Culver", "97451", "841-874-1234", "jane@example.com"),
            new Person("Robert", "Doe", "456 Elm St", "Culver", "97451", "841-874-5678", "robert@example.com")
        );
        when(personDAO.getAllPersons()).thenReturn(persons);

        // Mocking the medicalRecordDAO
        MedicalRecord medicalRecord1 = new MedicalRecord("John", "Boyd", "03/06/1984", new ArrayList<>(), new ArrayList<>());
        MedicalRecord medicalRecord2 = new MedicalRecord("Jane", "Smith", "05/01/2015", new ArrayList<>(), new ArrayList<>());
        MedicalRecord medicalRecord3 = new MedicalRecord("Robert", "Doe", "02/05/2016", new ArrayList<>(), new ArrayList<>());
        when(medicalRecordDAO.getMedicalRecordByFullName("John", "Boyd")).thenReturn(medicalRecord1);
        when(medicalRecordDAO.getMedicalRecordByFullName("Jane", "Smith")).thenReturn(medicalRecord2);
        when(medicalRecordDAO.getMedicalRecordByFullName("Robert", "Doe")).thenReturn(medicalRecord3);

        // Act
        List<FireStationCoverage> result = alertsService.getFireStationCoverage(fireStationNumber);

        // Assert
        assertNotNull(result);
        assertEquals(2, result.size());

        for (FireStationCoverage coverage : result) {

            List<PersonCoverage> expectedPersons = persons.stream()
                    .filter(person -> person.getAddress().equals(coverage.getAddress()))
                    .map(person -> new PersonCoverage(
                            person.getFirstName(),
                            person.getLastName(),
                            person.getAddress(),
                            person.getPhone()
                    ))
                    .collect(Collectors.toList());

            assertEquals(expectedPersons.size(), coverage.getPersons().size());
            assertEquals(expectedPersons, coverage.getPersons());
            assertEquals(expectedPersons.size(), coverage.getNumAdults() + coverage.getNumChildren());
        }

        // Verify the method calls
        verify(fireStationDAO, times(1)).getAllFireStations();
        verify(personDAO, times(1)).getAllPersons();
        verify(medicalRecordDAO, times(2)).getMedicalRecordByFullName(anyString(), anyString());
    }
    

    @Test
    public void testGetFloodStations() {
        // Arrange
        List<Integer> stationNumbers = Arrays.asList(1, 2, 3);

        // Mocking the fireStationDAO
        List<FireStation> fireStations = new ArrayList<>();
        fireStations.add(new FireStation("1509 Culver St", "1"));
        fireStations.add(new FireStation("123 Main St", "2"));
        fireStations.add(new FireStation("456 Elm St", "3"));
        when(fireStationDAO.getAllFireStations()).thenReturn(fireStations);

        // Mocking the personDAO
        List<Person> persons = new ArrayList<>();
        persons.add(new Person("John", "Boyd", "1509 Culver St", "Culver", "97451", "841-874-6512", "john@example.com"));
        persons.add(new Person("Jane", "Smith", "123 Main St", "Culver", "97451", "841-874-1234", "jane@example.com"));
        persons.add(new Person("Robert", "Doe", "456 Elm St", "Culver", "97451", "841-874-5678", "robert@example.com"));
        when(personDAO.getAllPersons()).thenReturn(persons);

        // Mocking the medicalRecordDAO
        MedicalRecord medicalRecord1 = new MedicalRecord("John", "Boyd", "03/06/1984", Arrays.asList("Medicine1"), Arrays.asList("Allergy1"));
        MedicalRecord medicalRecord2 = new MedicalRecord("Jane", "Smith", "05/01/2015", Arrays.asList("Medicine2"), Arrays.asList("Allergy2"));
        MedicalRecord medicalRecord3 = new MedicalRecord("Robert", "Doe", "01/05/2016", Arrays.asList("Medicine3"), Arrays.asList("Allergy3"));
        when(medicalRecordDAO.getMedicalRecordByFullName("John", "Boyd")).thenReturn(medicalRecord1);
        when(medicalRecordDAO.getMedicalRecordByFullName("Jane", "Smith")).thenReturn(medicalRecord2);
        when(medicalRecordDAO.getMedicalRecordByFullName("Robert", "Doe")).thenReturn(medicalRecord3);

        // Act
        List<Flood> result = alertsService.getFloodStations(stationNumbers);

        // Assert
        assertNotNull(result);
        assertEquals(stationNumbers.size(), result.size());

        for (int i = 0; i < result.size(); i++) {
            Flood flood = result.get(i);
            assertEquals(1, flood.getPersons().size());

            PersonFlood personFlood = flood.getPersons().get(0);
            Person person = persons.get(i);
            MedicalRecord medicalRecord = medicalRecordDAO.getMedicalRecordByFullName(person.getFirstName(), person.getLastName());

            assertEquals(person.getFirstName(), personFlood.getFirstName());
            assertEquals(person.getLastName(), personFlood.getLastName());
            assertEquals(person.getPhone(), personFlood.getPhone());
            assertEquals(alertsService.calculateAge(medicalRecord.getBirthdate()), personFlood.getAge());
            assertEquals(medicalRecord.getMedications(), personFlood.getMedications());
            assertEquals(medicalRecord.getAllergies(), personFlood.getAllergies());
        }

        // Verify the method calls
        verify(fireStationDAO, times(1)).getAllFireStations();
        verify(personDAO, times(3)).getAllPersons();
        verify(medicalRecordDAO, times(6)).getMedicalRecordByFullName(anyString(), anyString());
    }
}
