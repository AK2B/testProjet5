package com.SafetyNet.Alerts.controllerTestIT;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

@SpringBootTest
@AutoConfigureMockMvc
public class FireStationControllerTestIT {

	@Autowired
	private MockMvc mockMvc;

	@Test
	public void testGetFireStationCoverage() throws Exception {
		int fireStationNumber = 3;
		mockMvc.perform(MockMvcRequestBuilders.get("/firestation")
				.param("stationNumber", String.valueOf(fireStationNumber)).contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk()).andExpect(jsonPath("$[0].address").value("1509 Culver St"))
				.andExpect(jsonPath("$[0].numAdults").value(3)).andExpect(jsonPath("$[0].numChildren").value(2));
	}

	

}
