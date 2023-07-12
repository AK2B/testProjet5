package com.SafetyNet.Alerts;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
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

import com.SafetyNet.alerts.controllers.PersonInfoController;
import com.SafetyNet.alerts.models.InfoPerson;
import com.SafetyNet.alerts.models.PersonInfo;
import com.SafetyNet.alerts.services.AlertsService;

@WebMvcTest(PersonInfoController.class)
public class PersonInfoControllerTest {
    @Mock
    private AlertsService alertsService;

    @InjectMocks
    private PersonInfoController personInfoController;

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void testGetPersonInfo() throws Exception {
        String firstName = "John";
        String lastName = "Boyd";

        // Créer une liste de PersonInfo
        List<PersonInfo> personInfos = new ArrayList<>();
        personInfos.add(new PersonInfo(new InfoPerson(firstName, lastName, "1509 Culver St", "jaboyd@email.com"), 39,  Arrays.asList("aznol:350mg","hydrapermazol:100mg"), Arrays.asList("nillacilan")));

        // Définir le comportement du service de l'application mocké
        when(alertsService.getPersonInfo(firstName, lastName)).thenReturn(personInfos);

        // Exécuter la requête GET pour obtenir les informations sur la personne
        mockMvc.perform(MockMvcRequestBuilders.get("/personInfo")
                .param("firstName", firstName)
                .param("lastName", lastName)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].infoPerson.firstName").value(firstName))
                .andExpect(jsonPath("$[0].infoPerson.lastName").value(lastName))
                .andExpect(jsonPath("$[0].age").value(39))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].medications[0]").value("aznol:350mg"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].medications[1]").value("hydrapermazol:100mg"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].allergies[0]").value("nillacilan"));
    }
}
