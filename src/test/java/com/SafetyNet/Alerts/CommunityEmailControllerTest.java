package com.SafetyNet.Alerts;

import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.SafetyNet.alerts.controllers.CommunityEmailController;
import com.SafetyNet.alerts.models.CommunityEmail;
import com.SafetyNet.alerts.services.AlertsService;

@WebMvcTest(CommunityEmailController.class)
public class CommunityEmailControllerTest {
    @Mock
    private AlertsService alertsService;

    @InjectMocks
    private CommunityEmailController communityEmailController;

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void testGetCommunityEmails() throws Exception {
        String city = "Culver";
        CommunityEmail communityEmail = new CommunityEmail();

        when(alertsService.getCommunityEmails(city)).thenReturn(communityEmail);

        mockMvc.perform(MockMvcRequestBuilders.get("/communityEmail")
                .param("city", city)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.emails[0]").value("jaboyd@email.com"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.emails[1]").value("drk@email.com"));
    }
}
