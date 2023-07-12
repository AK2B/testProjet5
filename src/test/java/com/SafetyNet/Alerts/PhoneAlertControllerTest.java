package com.SafetyNet.Alerts;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

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

import com.SafetyNet.alerts.controllers.PhoneAlertController;
import com.SafetyNet.alerts.models.PhoneAlert;
import com.SafetyNet.alerts.services.AlertsService;

@WebMvcTest(PhoneAlertController.class)
public class PhoneAlertControllerTest {
    @Mock
    private AlertsService alertsService;

    @InjectMocks
    private PhoneAlertController phoneAlertController;

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void testGetPhoneAlert() throws Exception {
        // Numéro de caserne de pompiers pour lequel nous voulons obtenir les numéros de téléphone
        String fireStationNumber = "1";

        // Créer une liste de numéros de téléphone
        List<String> phoneNumbers = Arrays.asList("841-874-6512", "841-874-8547");

        // Créer un objet PhoneAlert avec les numéros de téléphone
        PhoneAlert phoneAlert = new PhoneAlert();
        phoneAlert.setPhoneNumbers(phoneNumbers);

        // Définir le comportement du service de l'application mocké
        when(alertsService.getPhoneAlert(fireStationNumber)).thenReturn(phoneAlert);

        // Exécuter la requête GET pour obtenir les numéros de téléphone de la caserne de pompiers
        mockMvc.perform(MockMvcRequestBuilders.get("/phoneAlert")
                .param("firestation", "1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.phoneNumbers[0]").value("841-874-6512"))
                .andExpect(jsonPath("$.phoneNumbers[1]").value("841-874-8547"));
    }
}
