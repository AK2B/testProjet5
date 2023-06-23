package com.SafetyNet.Alerts;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.io.InputStream;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;

import com.SafetyNet.alerts.model.Data;
import com.SafetyNet.alerts.repository.DataRepository;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

@ExtendWith(MockitoExtension.class)
public class DataRepositoryTest {

    @Mock
    private ResourceLoader resourceLoader;

    @Mock
    private ObjectMapper objectMapper;

    private DataRepository dataRepository;


    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        dataRepository = new DataRepository(resourceLoader, objectMapper);
    }

    @Test
    public void testGetData() throws IOException { 
        //GIVEN
        // Mocking resourceLoader.getResource() method
        Resource resource = mock(Resource.class);
        when(resourceLoader.getResource("classpath:data.json")).thenReturn(resource);

        // Mocking resource.getInputStream() method
        InputStream inputStream = mock(InputStream.class);
        when(resource.getInputStream()).thenReturn(inputStream);

        // Mocking objectMapper.readTree() method
        JsonNode rootNode = mock(JsonNode.class);
        when(objectMapper.readTree(inputStream)).thenReturn(rootNode);

        // Add additional mock configurations as per your needs to cover the code

        //WHEN
        Data actualData = dataRepository.getData();

        //THEN
        assertNotNull(actualData);
        // Add assertions to verify data parsing and processing

        verify(resourceLoader, times(1)).getResource("classpath:data.json");
        verify(resource, times(1)).getInputStream();
        verify(objectMapper, times(1)).readTree(inputStream);
    }

}
