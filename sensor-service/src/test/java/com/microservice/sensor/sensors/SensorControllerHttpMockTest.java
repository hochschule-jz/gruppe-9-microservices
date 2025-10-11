package com.microservice.sensor.sensors;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Collections;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


@WebMvcTest(SensorController.class)
public class SensorControllerHttpMockTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private SensorService sensorService;

    @Test
    public void getSensors() throws Exception {
        when(sensorService.getSensors()).thenReturn(Collections.emptyList());

        mockMvc.perform(MockMvcRequestBuilders.get("/api/sensors"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(""));

        verify(sensorService, times(1)).getSensors();
    }

    @Test
    public void getSensorById() throws Exception {
        Sensor sensor = new Sensor();
        when(sensorService.getSensorById("65b0cb7bd1f0bd05e47fa1f2")).thenReturn(sensor);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/sensors/65b0cb7bd1f0bd05e47fa1f2"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(objectMapper.writeValueAsString(sensor)));

        verify(sensorService, times(1)).getSensorById("65b0cb7bd1f0bd05e47fa1f2");
    }

    @Test
    public void createSensor() throws Exception {
        Sensor sensor = new Sensor();
        Sensor createdSensor = new Sensor();
        createdSensor.setId("65b0ccd8d1f0bd05e47fa1f4");

        when(sensorService.createSensor(sensor)).thenReturn(createdSensor);

        mockMvc.perform(MockMvcRequestBuilders.post("/api/sensors"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(objectMapper.writeValueAsString(createdSensor)));

        verify(sensorService, times(1)).createSensor(sensor);
    }

    @Test
    public void updateSensor() throws Exception {
        Sensor sensor = new Sensor();
        Sensor updatedSensor = new Sensor();
        updatedSensor.setName("Snensor");

        when(sensorService.updateSensor(sensor)).thenReturn(updatedSensor);

        mockMvc.perform(MockMvcRequestBuilders.put("/api/sensors/65b0ccd8d1f0bd05e47fa1f4"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(objectMapper.writeValueAsString(updatedSensor)));

        verify(sensorService, times(1)).updateSensor(sensor);
    }

    @Test
    public void deleteSensor() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete("/api/sensors/65b0ccd8d1f0bd05e47fa1f4"))
                .andExpect(MockMvcResultMatchers.status().isNoContent());

        verify(sensorService, times(1)).deleteSensor("65b0ccd8d1f0bd05e47fa1f4");
    }
}
