package com.SafetyNet.Alerts;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.SafetyNet.alerts.models.MedicalRecord;
import com.SafetyNet.alerts.models.Person;
import com.SafetyNet.alerts.models.alerts.Child;
import com.SafetyNet.alerts.models.alerts.ChildAlert;
import com.SafetyNet.alerts.models.alerts.PersonInfo;
import com.SafetyNet.alerts.services.AlertsService;
import com.SafetyNet.alerts.services.FireStationService;
import com.SafetyNet.alerts.services.MedicalRecordService;
import com.SafetyNet.alerts.services.PersonService;

public class AlertsServiceTest {

    @Mock
    private PersonService personService;

    @Mock
    private FireStationService fireStationService;

    @Mock
    private MedicalRecordService medicalRecordService;

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

        when(personService.getAllPersons()).thenReturn(persons);
        when(medicalRecordService.getMedicalRecordByFullName("John", "Boyd")).thenReturn(medicalRecord);

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

        verify(personService, times(1)).getAllPersons();
        verify(medicalRecordService, times(1)).getMedicalRecordByFullName("John", "Boyd");
    }

    @Test
    public void testCalculateAge() {
        String birthDate = "03/06/1984";

        int age = alertsService.calculateAge(birthDate);

        assertEquals(39, age);
    }
    
    
   
    

}
