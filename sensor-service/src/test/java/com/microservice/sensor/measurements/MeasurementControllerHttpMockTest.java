package com.microservice.sensor.measurements;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Collections;
import java.util.Optional;

import static org.mockito.Mockito.*;

@WebMvcTest(MeasurementController.class)
public class MeasurementControllerHttpMockTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private MeasurementService measurementService;

    @Test
    public void getMeasurements() throws Exception {
        when(measurementService.getMeasurements(Optional.empty())).thenReturn(Collections.emptyList());

        mockMvc.perform(MockMvcRequestBuilders.get("/api/measurements"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(""));

        verify(measurementService, times(1)).getMeasurements(Optional.empty());
    }

    @Test
    public void getMeasurementsBySensor() throws Exception {
        when(measurementService.getMeasurements(Optional.of("65b0cb7bd1f0bd05e47fa1f2"))).thenReturn(Collections.emptyList());

        mockMvc.perform(MockMvcRequestBuilders.get("/api/measurements?sensor=65b0cb7bd1f0bd05e47fa1f2"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(""));

        verify(measurementService, times(1)).getMeasurements(Optional.of("65b0cb7bd1f0bd05e47fa1f2"));
    }

    @Test
    public void getMeasurementById() throws Exception {
        Measurement measurement = new Measurement();
        when(measurementService.getMeasurementById("65b0ccded1f0bd05e47fa1f9")).thenReturn(measurement);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/measurements/65b0ccded1f0bd05e47fa1f9"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(objectMapper.writeValueAsString(measurement)));

        verify(measurementService, times(1)).getMeasurementById("65b0ccded1f0bd05e47fa1f9");
    }

    @Test
    public void createMeasurement() throws Exception {
        Measurement measurement = new Measurement();
        Measurement createdMeasurement = new Measurement();
        createdMeasurement.setId("65b0ccd8d1f0bd05e47fa1f4");

        when(measurementService.createMeasurement(measurement)).thenReturn(createdMeasurement);

        mockMvc.perform(MockMvcRequestBuilders.post("/api/measurements"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(objectMapper.writeValueAsString(createdMeasurement)));

        verify(measurementService, times(1)).createMeasurement(measurement);
    }

    @Test
    public void updateMeasurement() throws Exception {
        Measurement measurement = new Measurement();
        Measurement updatedMeasurement = new Measurement();
        updatedMeasurement.setHumidity(45);

        when(measurementService.updateMeasurement(measurement)).thenReturn(updatedMeasurement);

        mockMvc.perform(MockMvcRequestBuilders.put("/api/measurements/65b0ccd8d1f0bd05e47fa1f4"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(objectMapper.writeValueAsString(updatedMeasurement)));

        verify(measurementService, times(1)).updateMeasurement(measurement);
    }

    @Test
    public void deleteMeasurement() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete("/api/measurements/65b0ccd8d1f0bd05e47fa1f4"))
                .andExpect(MockMvcResultMatchers.status().isNoContent());

        verify(measurementService, times(1)).deleteMeasurement("65b0ccd8d1f0bd05e47fa1f4");
    }

    @Test
    public void getDailyMeanTemperatures() throws Exception {
        when(measurementService.getMeanValues("65b0cb7bd1f0bd05e47fa1f2", 1)).thenReturn(Collections.emptyList());

        mockMvc.perform(MockMvcRequestBuilders.get("/api/measurements/mean?sensor=65b0cb7bd1f0bd05e47fa1f2&days=1"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(""));

        verify(measurementService, times(1)).getMeanValues("65b0cb7bd1f0bd05e47fa1f2", 1);
    }

    @Test
    public void getWeeklyMeanTemperatures() throws Exception {
        when(measurementService.getMeanValues("65b0cb7bd1f0bd05e47fa1f2", 7)).thenReturn(Collections.emptyList());

        mockMvc.perform(MockMvcRequestBuilders.get("/api/measurements/mean?sensor=65b0cb7bd1f0bd05e47fa1f2&days=7"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(""));

        verify(measurementService, times(1)).getMeanValues("65b0cb7bd1f0bd05e47fa1f2", 7);
    }

    @Test
    public void getMonthlyMeanTemperatures() throws Exception {
        when(measurementService.getMeanValues("65b0cb7bd1f0bd05e47fa1f2", 30)).thenReturn(Collections.emptyList());

        mockMvc.perform(MockMvcRequestBuilders.get("/api/measurements/mean?sensor=65b0cb7bd1f0bd05e47fa1f2&days=30"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(""));

        verify(measurementService, times(1)).getMeanValues("65b0cb7bd1f0bd05e47fa1f2", 30);
    }
}
