package com.SafetyNet.Alerts;

import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.SafetyNet.alerts.controllers.MedicalRecordController;
import com.SafetyNet.alerts.models.MedicalRecord;
import com.SafetyNet.alerts.services.MedicalRecordService;

@WebMvcTest(MedicalRecordController.class)
public class MedicalRecordControllerTest {
    @Mock
    private MedicalRecordService medicalRecordService;

    @InjectMocks
    private MedicalRecordController medicalRecordController;

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void testGetAllMedicalRecords() throws Exception {
        MedicalRecord medicalRecord1 = new MedicalRecord("John", "Boyd", "03/06/1984", Arrays.asList("aznol:350mg","hydrapermazol:100mg"), Arrays.asList("nillacilan"));
        MedicalRecord medicalRecord2 = new MedicalRecord("Jacob", "Boyd", "03/06/1989", Arrays.asList("pharmacol:5000mg","terazine:10mg","noznazol:250mg"), Arrays.asList());
        List<MedicalRecord> medicalRecords = Arrays.asList(medicalRecord1, medicalRecord2);

        when(medicalRecordService.getAllMedicalRecords()).thenReturn(medicalRecords);

        mockMvc.perform(MockMvcRequestBuilders.get("/medicalrecord")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].firstName").value("John"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].lastName").value("Boyd"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].birthdate").value("03/06/1984"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].medications[0]").value("aznol:350mg"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].medications[1]").value("hydrapermazol:100mg"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].allergies.length()").value(1))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].allergies[0]").value("nillacilan"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].firstName").value("Jacob"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].lastName").value("Boyd"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].birthdate").value("03/06/1989"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].medications[0]").value("pharmacol:5000mg"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].medications[1]").value("terazine:10mg"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].medications[2]").value("noznazol:250mg"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].allergies.length()").value(0));
    }

    @Test
    public void testGetMedicalRecordByFullName() throws Exception {
        String firstName = "John";
        String lastName = "Doe";
        MedicalRecord medicalRecord = new MedicalRecord(firstName, lastName, "2000-01-01", Arrays.asList("Medication 1", "Medication 2"), Arrays.asList("Allergy 1", "Allergy 2"));

        when(medicalRecordService.getMedicalRecordByFullName(firstName, lastName)).thenReturn(medicalRecord);

        mockMvc.perform(MockMvcRequestBuilders.get("/medicalrecord/{firstName}/{lastName}", firstName, lastName)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.firstName").value(firstName))
                .andExpect(MockMvcResultMatchers.jsonPath("$.lastName").value(lastName))
                .andExpect(MockMvcResultMatchers.jsonPath("$.birthdate").value("2000-01-01"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.medications.length()").value(2))
                .andExpect(MockMvcResultMatchers.jsonPath("$.medications[0]").value("Medication 1"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.medications[1]").value("Medication 2"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.allergies.length()").value(2))
                .andExpect(MockMvcResultMatchers.jsonPath("$.allergies[0]").value("Allergy 1"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.allergies[1]").value("Allergy 2"));
    }

    @Test
    public void testGetMedicalRecordByFullName_NotFound() throws Exception {
        String firstName = "Non-existent";
        String lastName = "Person";

        when(medicalRecordService.getMedicalRecordByFullName(firstName, lastName)).thenReturn(null);

        mockMvc.perform(MockMvcRequestBuilders.get("/medicalrecord/{firstName}/{lastName}", firstName, lastName)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    public void testAddMedicalRecord() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/medicalrecord")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"firstName\":\"John\",\"lastName\":\"Doe\",\"birthdate\":\"2000-01-01\",\"medications\":[\"Medication 1\",\"Medication 2\"],\"allergies\":[\"Allergy 1\",\"Allergy 2\"]}"))
                .andExpect(MockMvcResultMatchers.status().isCreated());
    }

    @Test
    public void testUpdateMedicalRecord() throws Exception {
        String firstName = "John";
        String lastName = "Boyd";
        MedicalRecord existingMedicalRecord = new MedicalRecord(firstName, lastName, "03/06/1984", Arrays.asList("aznol:350mg","hydrapermazol:100mg"), Arrays.asList("nillacilan"));

        when(medicalRecordService.getMedicalRecordByFullName(firstName, lastName)).thenReturn(existingMedicalRecord);

        mockMvc.perform(MockMvcRequestBuilders.put("/medicalrecord/{firstName}/{lastName}", firstName, lastName)
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"firstName\":\"John\",\"lastName\":\"Doe\",\"birthdate\":\"03/06/1984\",\"medications\":[\"Medication 3\",\"Medication 4\"],\"allergies\":[\"Allergy 3\",\"Allergy 4\"]}"))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void testUpdateMedicalRecord_NotFound() throws Exception {
        String firstName = "Non-existent";
        String lastName = "Person";

        when(medicalRecordService.getMedicalRecordByFullName(firstName, lastName)).thenReturn(null);

        mockMvc.perform(MockMvcRequestBuilders.put("/medicalrecord/{firstName}/{lastName}", firstName, lastName)
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"firstName\":\"Non-existent\",\"lastName\":\"Person\",\"birthdate\":\"2000-01-01\",\"medications\":[\"Medication 1\",\"Medication 2\"],\"allergies\":[\"Allergy 1\",\"Allergy 2\"]}"))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    public void testDeleteMedicalRecord() throws Exception {
        String firstName = "John";
        String lastName = "Doe";

        mockMvc.perform(MockMvcRequestBuilders.delete("/medicalrecord/{firstName}/{lastName}", firstName, lastName)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }
}
