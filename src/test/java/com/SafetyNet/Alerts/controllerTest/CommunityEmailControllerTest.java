package com.SafetyNet.Alerts.controllerTest;

import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.SafetyNet.alerts.controller.CommunityEmailController;
import com.SafetyNet.alerts.model.CommunityEmail;
import com.SafetyNet.alerts.service.AlertsService;

@WebMvcTest(controllers = { CommunityEmailController.class, AlertsService.class })
@ExtendWith(SpringExtension.class)
public class CommunityEmailControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private AlertsService alertsService;

	@Test
	public void testGetCommunityEmails() throws Exception {
		String city = "Culver";

		// Créer une liste d'email
		List<String> emails = Arrays.asList("jaboyd@email.com", "drk@email.com");

		// Créer un objet CommunityEmail avec les emails
		CommunityEmail communityEmail = new CommunityEmail();
		communityEmail.setEmails(emails);

		when(alertsService.getCommunityEmails(city)).thenReturn(communityEmail);

		mockMvc.perform(MockMvcRequestBuilders.get("/communityEmail").param("city", city)
				.contentType(MediaType.APPLICATION_JSON)).andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.jsonPath("$.emails[0]").value("jaboyd@email.com"))
				.andExpect(MockMvcResultMatchers.jsonPath("$.emails[1]").value("drk@email.com"));

		verify(alertsService, times(1)).getCommunityEmails(city);
	}

	@Test
    public void testGetCommunityEmailsWithEmptyCity() throws Exception {
        String city = ""; 
        mockMvc.perform(MockMvcRequestBuilders.get("/communityEmail")
                .param("city", city)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    public void testGetCommunityEmailsWithInvalidCity() throws Exception {
        String city = "invalid_city"; 
        mockMvc.perform(MockMvcRequestBuilders.get("/communityEmail")
                .param("city", city)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }
    
	 @Test
	    public void testGetCommunityEmails_InternalServerError() throws Exception {
	        String city = "Culver";
	        doAnswer(invocation -> {
	            throw new Exception("Test Internal Server Error");
	        }).when(alertsService).getCommunityEmails(city);

	        mockMvc.perform(MockMvcRequestBuilders.get("/communityEmail")
	                .param("city", city)
	                .contentType(MediaType.APPLICATION_JSON))
	                .andExpect(MockMvcResultMatchers.status().isInternalServerError());
	    }

}
