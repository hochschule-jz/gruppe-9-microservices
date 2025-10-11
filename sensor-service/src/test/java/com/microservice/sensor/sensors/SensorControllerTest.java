package com.microservice.sensor.sensors;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
class SensorControllerTest {

    @Autowired
    private SensorController controller;

    @MockBean
    private SensorService service;

    @Test
    void getAllSensors() {
        List<Sensor> sensors = new ArrayList<>();
        when(this.service.getSensors()).thenReturn(sensors);

        assertEquals(ResponseEntity.ok(sensors), this.controller.getAllSensors());
        verify(service, times(1)).getSensors();
    }

    @Test
    void getSensorById() {
        Sensor sensor = new Sensor();
        when(this.service.getSensorById("cc92869e-e7f3-46b9-a5a0-8be3e3d01cec")).thenReturn(sensor);

        assertEquals(ResponseEntity.ok(sensor), this.controller.getSensorById("cc92869e-e7f3-46b9-a5a0-8be3e3d01cec"));
        verify(service, times(1)).getSensorById("cc92869e-e7f3-46b9-a5a0-8be3e3d01cec");
    }

    @Test
    void createSensor() {
        Sensor sensor = new Sensor();
        when(this.service.createSensor(sensor)).thenReturn(sensor);

        assertEquals(ResponseEntity.ok(sensor), this.controller.createSensor(sensor));
        verify(service, times(1)).createSensor(sensor);
    }

    @Test
    void updateSensor() {
        Sensor sensor = new Sensor();
        when(this.service.updateSensor(sensor)).thenReturn(sensor);

        assertEquals(ResponseEntity.ok(sensor), this.controller.updateSensor(sensor));
        verify(service, times(1)).updateSensor(sensor);
    }

    @Test
    void deleteSensor() {
        assertEquals(ResponseEntity.noContent().build(), this.controller.deleteSensor("cc92869e-e7f3-46b9-a5a0-8be3e3d01cec"));
        verify(service, times(1)).deleteSensor("cc92869e-e7f3-46b9-a5a0-8be3e3d01cec");
    }
}