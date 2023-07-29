package com.SafetyNet.Alerts.controllerTestIT;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@SpringBootTest
@AutoConfigureMockMvc
public class ChildAlertControllerTestIT {

	@Autowired
	private MockMvc mockMvc;

	@Test
	public void testGetChildAlertIT() throws Exception {

		mockMvc.perform(MockMvcRequestBuilders.get("/childAlert").param("address", "1509 Culver St")
				.contentType(MediaType.APPLICATION_JSON)).andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.jsonPath("$.children[0].firstName").value("Tenley"))
				.andExpect(MockMvcResultMatchers.jsonPath("$.children[0].lastName").value("Boyd"))
				.andExpect(MockMvcResultMatchers.jsonPath("$.children[0].age").value(11))
				.andExpect(MockMvcResultMatchers.jsonPath("$.children[1].firstName").value("Roger"))
				.andExpect(MockMvcResultMatchers.jsonPath("$.children[1].lastName").value("Boyd"))
				.andExpect(MockMvcResultMatchers.jsonPath("$.children[1].age").value(5))
				.andExpect(MockMvcResultMatchers.jsonPath("$.householdMembers[0].firstName").value("John"))
				.andExpect(MockMvcResultMatchers.jsonPath("$.householdMembers[0].lastName").value("Boyd"))
				.andExpect(MockMvcResultMatchers.jsonPath("$.householdMembers[0].address").value("1509 Culver St"))
				.andExpect(MockMvcResultMatchers.jsonPath("$.householdMembers[0].city").value("Culver"))
				.andExpect(MockMvcResultMatchers.jsonPath("$.householdMembers[0].zip").value("97451"))
				.andExpect(MockMvcResultMatchers.jsonPath("$.householdMembers[0].phone").value("841-874-6512"))
				.andExpect(MockMvcResultMatchers.jsonPath("$.householdMembers[0].email").value("jaboyd@email.com"))
				.andExpect(MockMvcResultMatchers.jsonPath("$.householdMembers[1].firstName").value("Jacob"))
				.andExpect(MockMvcResultMatchers.jsonPath("$.householdMembers[1].lastName").value("Boyd"))
				.andExpect(MockMvcResultMatchers.jsonPath("$.householdMembers[1].address").value("1509 Culver St"))
				.andExpect(MockMvcResultMatchers.jsonPath("$.householdMembers[1].city").value("Culver"))
				.andExpect(MockMvcResultMatchers.jsonPath("$.householdMembers[1].zip").value("97451"))
				.andExpect(MockMvcResultMatchers.jsonPath("$.householdMembers[1].phone").value("841-874-6513"))
				.andExpect(MockMvcResultMatchers.jsonPath("$.householdMembers[1].email").value("drk@email.com"));

	}
	
}
