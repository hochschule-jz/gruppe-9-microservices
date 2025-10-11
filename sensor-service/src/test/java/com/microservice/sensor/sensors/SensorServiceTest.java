package com.microservice.sensor.sensors;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
class SensorServiceTest {

    @Autowired
    private SensorService service;

    @MockBean
    private SensorRepository repository;

    @Test
    void getSensors() {
        List<Sensor> sensors = new ArrayList<>();
        when(this.repository.findAll()).thenReturn(sensors);

        assertEquals(sensors, this.service.getSensors());
        verify(repository, times(1)).findAll();
    }

    @Test
    void getSensorById() {
        Sensor sensor = new Sensor();
        when(this.repository.findById("cc92869e-e7f3-46b9-a5a0-8be3e3d01cec")).thenReturn(Optional.of(sensor));

        assertEquals(sensor, this.service.getSensorById("cc92869e-e7f3-46b9-a5a0-8be3e3d01cec"));
        verify(repository, times(1)).findById("cc92869e-e7f3-46b9-a5a0-8be3e3d01cec");
    }

    @Test
    void createSensor() {
        Sensor sensor = new Sensor();
        when(this.repository.insert(sensor)).thenReturn(sensor);

        assertEquals(sensor, this.service.createSensor(sensor));
        verify(repository, times(1)).insert(sensor);
    }

    @Test
    void updateSensor() {
        Sensor sensor = new Sensor();
        when(this.repository.save(sensor)).thenReturn(sensor);

        assertEquals(sensor, this.service.updateSensor(sensor));
        verify(repository, times(1)).save(sensor);
    }

    @Test
    void deleteSensor() {
        this.service.deleteSensor("cc92869e-e7f3-46b9-a5a0-8be3e3d01cec");
        verify(repository, times(1)).deleteById("cc92869e-e7f3-46b9-a5a0-8be3e3d01cec");
    }
}