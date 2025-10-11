package com.microservice.sensor.sensors;

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
public class SensorControllerHttpTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    private Sensor sensor;

    @BeforeEach
    public void init() {
        this.sensor = new Sensor();
        sensor.setName("Snensor");
        sensor.setActive(false);
        sensor.setLocation("Grafenschachen");
        sensor.setType(SensorType.INDOOR);
    }

    @Test
    public void getAllSensors() throws Exception {
        this.mockMvc.perform(get("/api/sensors"))
                .andExpect(status().isOk());
    }

    @Test
    public void getSensorById() throws Exception {
        this.mockMvc.perform(get("/api/sensors/65b0cb7bd1f0bd05e47fa1f2"))
                .andExpect(status().isInternalServerError());
    }

    @Test
    public void createSensor() throws Exception {
        this.mockMvc.perform(post("/api/sensors")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(this.sensor)))
                        .andExpect(status().isOk());
    }

    @Test
    public void updateSensor() throws Exception {
        this.mockMvc.perform(put("/api/sensors/65b0cb7bd1f0bd05e47fa1f2")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(this.sensor)))
                .andExpect(status().isOk());
    }

    @Test
    public void deleteSensor() throws Exception {
        this.mockMvc.perform(delete("/api/sensors/65b0cb7bd1f0bd05e47fa1f2"))
                .andExpect(status().isNoContent());
    }
}
