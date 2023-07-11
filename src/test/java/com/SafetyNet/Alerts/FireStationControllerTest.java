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

import com.SafetyNet.alerts.controllers.FireStationController;
import com.SafetyNet.alerts.models.FireStation;
import com.SafetyNet.alerts.services.FireStationService;

@WebMvcTest(FireStationController.class)
public class FireStationControllerTest {
    @Mock
    private FireStationService fireStationService;

    @InjectMocks
    private FireStationController fireStationController;

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void testGetAllFireStations() throws Exception {
        FireStation fireStation1 = new FireStation("29 15th St", "2");
        FireStation fireStation2 = new FireStation("834 Binoc Ave","3");
        List<FireStation> fireStations = Arrays.asList(fireStation1, fireStation2);

        when(fireStationService.getAllFireStations()).thenReturn(fireStations);

        mockMvc.perform(MockMvcRequestBuilders.get("/firestations")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].address").value("29 15th St"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].station").value("2"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].address").value("834 Binoc Ave"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].station").value("3"));
    }

    @Test
    public void testGetFireStationByAddress() throws Exception {
        String address = "29 15th St";
        FireStation fireStation = new FireStation(address, "2");

        when(fireStationService.getFireStationByAddress(address)).thenReturn(fireStation);

        mockMvc.perform(MockMvcRequestBuilders.get("/firestations/{address}", address)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.address").value(address))
                .andExpect(MockMvcResultMatchers.jsonPath("$.station").value("2"));
    }

    @Test
    public void testGetFireStationByAddress_NotFound() throws Exception {
        String address = "Non-existent Address";

        when(fireStationService.getFireStationByAddress(address)).thenReturn(null);

        mockMvc.perform(MockMvcRequestBuilders.get("/firestations/{address}", address)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    public void testAddFireStation() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/firestations")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"address\":\"Address 1\",\"station\":\"Station 1\"}"))
                .andExpect(MockMvcResultMatchers.status().isCreated());
    }

    @Test
    public void testUpdateFireStation() throws Exception {
        String address = "1509 Culver St";
        FireStation existingFireStation = new FireStation(address, "3");

        when(fireStationService.getFireStationByAddress(address)).thenReturn(existingFireStation);

        mockMvc.perform(MockMvcRequestBuilders.put("/firestations/{address}", address)
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"address\":\"Address 1\",\"station\":\"Station 2\"}"))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void testUpdateFireStation_NotFound() throws Exception {
        String address = "Non-existent Address";

        when(fireStationService.getFireStationByAddress(address)).thenReturn(null);

        mockMvc.perform(MockMvcRequestBuilders.put("/firestations/{address}", address)
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"address\":\"Address 1\",\"station\":\"Station 2\"}"))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    public void testDeleteFireStation() throws Exception {
        String address = "1509 Culver St";

        mockMvc.perform(MockMvcRequestBuilders.delete("/firestations/{address}", address)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }
}
