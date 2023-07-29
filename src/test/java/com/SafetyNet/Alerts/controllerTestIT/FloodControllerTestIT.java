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
public class FloodControllerTestIT {

	@Autowired
	private MockMvc mockMvc;

	@Test
	public void testGetFloodStations() throws Exception {

		mockMvc.perform(MockMvcRequestBuilders.get("/flood/stations").param("stations", "1")
				.contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
				.andExpect(jsonPath("$[0].address").value("644 Gershwin Cir"))
				.andExpect(jsonPath("$[0].persons[0].firstName").value("Peter"))
				.andExpect(jsonPath("$[0].persons[0].medications").isEmpty())
				.andExpect(jsonPath("$[0].persons[0].allergies").value("shellfish"))
				.andExpect(jsonPath("$[1].address").value("908 73rd St"))
				.andExpect(jsonPath("$[1].persons[0].firstName").value("Reginold"))
				.andExpect(jsonPath("$[1].persons[0].medications").value("thradox:700mg"))
				.andExpect(jsonPath("$[1].persons[0].allergies[0]").value("illisoxian"));
	}
	
	
}
