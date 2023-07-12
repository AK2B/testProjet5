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

import com.SafetyNet.alerts.controllers.FloodController;
import com.SafetyNet.alerts.models.Flood;
import com.SafetyNet.alerts.models.PersonFlood;
import com.SafetyNet.alerts.services.AlertsService;

@WebMvcTest(FloodController.class)
public class FloodControllerTest {
    @Mock
    private AlertsService alertsService;

    @InjectMocks
    private FloodController floodController;

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void testGetFloodStations() throws Exception {
        // Numéros de station pour lesquels nous voulons obtenir les informations de crue
        List<Integer> stationNumbers = Arrays.asList(1, 2);

        // Créer une liste de Flood
        List<Flood> floodStations = new ArrayList<>();
        List<PersonFlood> persons1 = Arrays.asList(
                new PersonFlood("Jonanathan", "Marrack", "841-874-6513", 34, new ArrayList<>(), new ArrayList<>()));
        
        floodStations.add(new Flood("29 15th St", persons1));

        List<PersonFlood> persons2 = Arrays.asList(
                new PersonFlood("Peter", "Duncan", "841-874-6512", 22, new ArrayList<>(), Arrays.asList("shellfish")));
        
        floodStations.add(new Flood("644 Gershwin Cir", persons2));

        // Définir le comportement du service de l'application mocké
        when(alertsService.getFloodStations(stationNumbers)).thenReturn(floodStations);

        // Exécuter la requête GET pour obtenir les informations sur les stations de crue
        mockMvc.perform(MockMvcRequestBuilders.get("/flood/stations")
        	    .param("stations", "1,2")
        	    .contentType(MediaType.APPLICATION_JSON))
        	    .andExpect(status().isOk())
        	    .andExpect(jsonPath("$[0].address").value("29 15th St"))
        	    .andExpect(jsonPath("$[0].persons[0].firstName").value("Jonanathan"))
        	    .andExpect(jsonPath("$[0].persons[0].medications").isEmpty())
        	    .andExpect(jsonPath("$[0].persons[0].allergies").isEmpty())
        	    .andExpect(jsonPath("$[1].address").value("644 Gershwin Cir"))
        	    .andExpect(jsonPath("$[1].persons[0].firstName").value("Peter"))
        	    .andExpect(jsonPath("$[1].persons[0].medications").isEmpty())
        	    .andExpect(jsonPath("$[1].persons[0].allergies[0]").value("shellfish"));

    }
}
