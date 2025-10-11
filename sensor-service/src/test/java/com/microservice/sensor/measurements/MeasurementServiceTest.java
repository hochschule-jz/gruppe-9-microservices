package com.microservice.sensor.measurements;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@SpringBootTest
class MeasurementServiceTest {

    @Autowired
    private MeasurementService service;

    @MockBean
    private MeasurementRepository repository;

    @Test
    void getMeasurements() {
        List<Measurement> measurements = new ArrayList<>();
        when(this.repository.findAll()).thenReturn(measurements);

        assertEquals(measurements, this.service.getMeasurements(Optional.empty()));
        verify(repository, times(1)).findAll();
    }

    @Test
    void getMeasurementsBySensor() {
        List<Measurement> measurements = new ArrayList<>();
        when(this.repository.findBySensor("068358bb-d194-48b2-8f36-e7e55805a8fa")).thenReturn(measurements);

        assertEquals(measurements, this.service.getMeasurements(Optional.of("068358bb-d194-48b2-8f36-e7e55805a8fa")));
        verify(repository, times(1)).findBySensor("068358bb-d194-48b2-8f36-e7e55805a8fa");
    }

    @Test
    void getMeasurementById() {
        Measurement measurement = new Measurement();
        when(this.repository.findById("cc92869e-e7f3-46b9-a5a0-8be3e3d01cec")).thenReturn(Optional.of(measurement));

        assertEquals(measurement, this.service.getMeasurementById("cc92869e-e7f3-46b9-a5a0-8be3e3d01cec"));
        verify(repository, times(1)).findById("cc92869e-e7f3-46b9-a5a0-8be3e3d01cec");
    }

    @Test
    void createMeasurement() {
        Measurement measurement = new Measurement();
        when(this.repository.insert(measurement)).thenReturn(measurement);

        assertEquals(measurement, this.service.createMeasurement(measurement));
        verify(repository, times(1)).insert(measurement);
    }

    @Test
    void updateMeasurement() {
        Measurement measurement = new Measurement();
        when(this.repository.save(measurement)).thenReturn(measurement);

        assertEquals(measurement, this.service.updateMeasurement(measurement));
        verify(repository, times(1)).save(measurement);
    }

    @Test
    void deleteMeasurement() {
        this.service.deleteMeasurement("cc92869e-e7f3-46b9-a5a0-8be3e3d01cec");
        verify(repository, times(1)).deleteById("cc92869e-e7f3-46b9-a5a0-8be3e3d01cec");
    }

    @Test
    void getDailyMeanTemperatures() {
        assertEquals(25, this.service.getMeanValues("068358bb-d194-48b2-8f36-e7e55805a8fa", 1).size());
    }

    @Test
    void getWeeklyMeanTemperatures() {
        assertEquals(7, this.service.getMeanValues("068358bb-d194-48b2-8f36-e7e55805a8fa", 7).size());
    }

    @Test
    void getMonthlyMeanTemperatures() {
        assertEquals(30, this.service.getMeanValues("068358bb-d194-48b2-8f36-e7e55805a8fa", 30).size());
    }
}