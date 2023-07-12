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

import com.SafetyNet.alerts.controllers.ChildAlertController;
import com.SafetyNet.alerts.models.Child;
import com.SafetyNet.alerts.models.ChildAlert;
import com.SafetyNet.alerts.models.Person;
import com.SafetyNet.alerts.services.AlertsService;

@WebMvcTest(ChildAlertController.class)
public class ChildAlertControllerTest {
    @Mock
    private AlertsService alertsService;

    @InjectMocks
    private ChildAlertController childAlertController;

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void testGetChildAlert() throws Exception {
        String address = "1509 Culver St";
        Child child1 = new Child("Tenley", "Boyd", 11);
        Child child2 = new Child("Roger", "Boyd", 5);
        List<Child> children = Arrays.asList(child1, child2);
        Person person1 = new Person("John", "Boyd", address, "Culver", "97451", "841-874-6512", "jaboyd@email.com");
        Person person2 = new Person("Jacob", "Boyd", address, "Culver", "97451", "841-874-6513", "drk@email.com");
        List<Person> householdMembers = Arrays.asList(person1, person2);
        ChildAlert childAlert = new ChildAlert(children, householdMembers);

        when(alertsService.getChildAlert(address)).thenReturn(childAlert);

        mockMvc.perform(MockMvcRequestBuilders.get("/childAlert")
                .param("address", address)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.children[0].firstName").value("Tenley"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.children[0].lastName").value("Boyd"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.children[0].age").value(11))
                .andExpect(MockMvcResultMatchers.jsonPath("$.children[1].firstName").value("Roger"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.children[1].lastName").value("Boyd"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.children[1].age").value(5))
                .andExpect(MockMvcResultMatchers.jsonPath("$.householdMembers[0].firstName").value("John"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.householdMembers[0].lastName").value("Boyd"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.householdMembers[0].address").value(address))
                .andExpect(MockMvcResultMatchers.jsonPath("$.householdMembers[0].city").value("Culver"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.householdMembers[0].zip").value("97451"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.householdMembers[0].phone").value("841-874-6512"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.householdMembers[0].email").value("jaboyd@email.com"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.householdMembers[1].firstName").value("Jacob"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.householdMembers[1].lastName").value("Boyd"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.householdMembers[1].address").value(address))
                .andExpect(MockMvcResultMatchers.jsonPath("$.householdMembers[1].city").value("Culver"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.householdMembers[1].zip").value("97451"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.householdMembers[1].phone").value("841-874-6513"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.householdMembers[1].email").value("drk@email.com"));
    }
}
