package com.microservice.sensor.measurements;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class MeasurementControllerHttpTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    private Measurement measurement;

    @BeforeEach
    public void init() {
        this.measurement = new Measurement();
        measurement.setHumidity(40);
        measurement.setTemperature(20);
        measurement.setSensor("65b0cb7bd1f0bd05e47fa1f2");
    }

    @Test
    public void getMeasurements() throws Exception {
        this.mockMvc.perform(get("/api/measurements"))
                .andExpect(status().isOk());
    }

    @Test
    public void getAllMeasurementsBySensor() throws Exception {
        this.mockMvc.perform(get("/api/measurements?sensor=65b0cb7bd1f0bd05e47fa1f2"))
                .andExpect(status().isOk());
    }

    @Test
    public void getMeasurementById() throws Exception {
        this.mockMvc.perform(get("/api/measurements/65b0ccded1f0bd05e47fa1f9"))
                .andExpect(status().isInternalServerError());
    }

    @Test
    public void createMeasurement() throws Exception {
        this.mockMvc.perform(post("/api/measurements")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(this.measurement)))
                        .andExpect(status().isOk());
    }

    @Test
    public void updateMeasurement() throws Exception {
        this.mockMvc.perform(put("/api/measurements/65b0ccded1f0bd05e47fa1f9")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(this.measurement)))
                        .andExpect(status().isOk());
    }

    @Test
    public void deleteMeasurement() throws Exception {
        this.mockMvc.perform(delete("/api/measurements/65b0ccded1f0bd05e47fa1f9"))
                .andExpect(status().isNoContent());
    }

    @Test
    public void getDailyMeanTemperatures() throws Exception {
        this.mockMvc.perform(get("/api/measurements/mean?sensor=65b0cb7bd1f0bd05e47fa1f2&days=1"))
                .andExpect(status().isOk());
    }

    @Test
    public void getWeeklyMeanTemperatures() throws Exception {
        this.mockMvc.perform(get("/api/measurements/mean?sensor=65b0cb7bd1f0bd05e47fa1f2&days=7"))
                .andExpect(status().isOk());
    }

    @Test
    public void getMonthlyMeanTemperatures() throws Exception {
        this.mockMvc.perform(get("/api/measurements/mean?sensor=65b0cb7bd1f0bd05e47fa1f2&days=30"))
                .andExpect(status().isOk());
    }

    @Test
    public void getMeanValuesLessThanLimit() throws Exception {
        this.mockMvc.perform(get("/api/measurements/mean?sensor=65b0cb7bd1f0bd05e47fa1f2&days=0"))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void getMeanValuesGreaterThanLimit() throws Exception {
        this.mockMvc.perform(get("/api/measurements/mean?sensor=65b0cb7bd1f0bd05e47fa1f2&days=3000"))
                .andExpect(status().isBadRequest());
    }
}
