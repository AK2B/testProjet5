package com.SafetyNet.Alerts;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.SafetyNet.alerts.controllers.FireStationCoverageController;
import com.SafetyNet.alerts.models.FireStationCoverage;
import com.SafetyNet.alerts.services.AlertsService;

@WebMvcTest(FireStationCoverageController.class)
public class FireStationCoverageControllerTest {
    @Mock
    private AlertsService alertsService;

    @InjectMocks
    private FireStationCoverageController fireStationCoverageController;

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void testGetFireStationCoverage() throws Exception {
        int fireStationNumber = 3;

        // Créer une liste de FireStationCoverage
        List<FireStationCoverage> fireStationCoverages = new ArrayList<>();
        fireStationCoverages.add(new FireStationCoverage("1509 Culver St", 3, 2, new ArrayList<>()));

        // Définir le comportement du service de l'application mocké
        when(alertsService.getFireStationCoverage(fireStationNumber)).thenReturn(fireStationCoverages);

        // Exécuter la requête GET pour obtenir la couverture de la caserne de pompiers
        mockMvc.perform(MockMvcRequestBuilders.get("/firestation")
                .param("stationNumber", String.valueOf(fireStationNumber))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].address").value("1509 Culver St"))
                .andExpect(jsonPath("$[0].numAdults").value(3))
                .andExpect(jsonPath("$[0].numChildren").value(2));
    }
}
