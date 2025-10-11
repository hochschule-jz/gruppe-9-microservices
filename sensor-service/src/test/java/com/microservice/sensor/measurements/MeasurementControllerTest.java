package com.microservice.sensor.measurements;

import jakarta.validation.ConstraintViolationException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
class MeasurementControllerTest {

    @Autowired
    private MeasurementController controller;

    @MockBean
    private MeasurementService service;

    @Test
    void getAllMeasurements() {
        List<Measurement> measurements = new ArrayList<>();
        when(this.service.getMeasurements(Optional.empty())).thenReturn(measurements);

        assertEquals(ResponseEntity.ok(measurements), this.controller.getAllMeasurements(Optional.empty()));
        verify(service, times(1)).getMeasurements(Optional.empty());
    }

    @Test
    void getAllMeasurementsBySensor() {
        List<Measurement> measurements = new ArrayList<>();
        when(this.service.getMeasurements(Optional.of("068358bb-d194-48b2-8f36-e7e55805a8fa"))).thenReturn(measurements);

        assertEquals(ResponseEntity.ok(measurements), this.controller.getAllMeasurements(Optional.of("068358bb-d194-48b2-8f36-e7e55805a8fa")));
        verify(service, times(1)).getMeasurements(Optional.of("068358bb-d194-48b2-8f36-e7e55805a8fa"));
    }

    @Test
    void getMeasurementById() {
        Measurement measurement = new Measurement();
        when(this.service.getMeasurementById("cc92869e-e7f3-46b9-a5a0-8be3e3d01cec")).thenReturn(measurement);

        assertEquals(ResponseEntity.ok(measurement), this.controller.getMeasurementById("cc92869e-e7f3-46b9-a5a0-8be3e3d01cec"));
        verify(service, times(1)).getMeasurementById("cc92869e-e7f3-46b9-a5a0-8be3e3d01cec");
    }

    @Test
    void createMeasurement() {
        Measurement measurement = new Measurement();
        when(this.service.createMeasurement(measurement)).thenReturn(measurement);

        assertEquals(ResponseEntity.ok(measurement), this.controller.createMeasurement(measurement));
        verify(service, times(1)).createMeasurement(measurement);
    }

    @Test
    void updateMeasurement() {
        Measurement measurement = new Measurement();
        when(this.service.updateMeasurement(measurement)).thenReturn(measurement);

        assertEquals(ResponseEntity.ok(measurement), this.controller.updateMeasurement(measurement));
        verify(service, times(1)).updateMeasurement(measurement);
    }

    @Test
    void deleteMeasurement() {
        assertEquals(ResponseEntity.noContent().build(), this.controller.deleteMeasurement("cc92869e-e7f3-46b9-a5a0-8be3e3d01cec"));
        verify(service, times(1)).deleteMeasurement("cc92869e-e7f3-46b9-a5a0-8be3e3d01cec");
    }

    @Test
    void getDailyMeanTemperatures() {
        List<MeanValue> meanValues = new ArrayList<>();
        when(this.service.getMeanValues("068358bb-d194-48b2-8f36-e7e55805a8fa", 1)).thenReturn(meanValues);

        assertEquals(ResponseEntity.ok(meanValues), this.controller.getMeanValues("068358bb-d194-48b2-8f36-e7e55805a8fa", 1));
        verify(service, times(1)).getMeanValues("068358bb-d194-48b2-8f36-e7e55805a8fa", 1);
    }

    @Test
    void getWeeklyMeanTemperatures() {
        List<MeanValue> meanValues = new ArrayList<>();
        when(this.service.getMeanValues("068358bb-d194-48b2-8f36-e7e55805a8fa", 7)).thenReturn(meanValues);

        assertEquals(ResponseEntity.ok(meanValues), this.controller.getMeanValues("068358bb-d194-48b2-8f36-e7e55805a8fa", 7));
        verify(service, times(1)).getMeanValues("068358bb-d194-48b2-8f36-e7e55805a8fa", 7);
    }

    @Test
    void getMonthlyMeanTemperatures() {
        List<MeanValue> meanValues = new ArrayList<>();
        when(this.service.getMeanValues("068358bb-d194-48b2-8f36-e7e55805a8fa", 30)).thenReturn(meanValues);

        assertEquals(ResponseEntity.ok(meanValues), this.controller.getMeanValues("068358bb-d194-48b2-8f36-e7e55805a8fa", 30));
        verify(service, times(1)).getMeanValues("068358bb-d194-48b2-8f36-e7e55805a8fa", 30);
    }

    @Test
    void getMeanValuesLessThanLimit() {
        when(this.service.getMeanValues("068358bb-d194-48b2-8f36-e7e55805a8fa", 0)).thenThrow(new ConstraintViolationException("Days have to be between 1 and 365", null));
        assertThrows(ConstraintViolationException.class, () -> this.controller.getMeanValues("068358bb-d194-48b2-8f36-e7e55805a8fa", 0));
    }

    @Test
    void getMeanValuesGreaterThanLimit() {
        when(this.service.getMeanValues("068358bb-d194-48b2-8f36-e7e55805a8fa", 370)).thenThrow(new ConstraintViolationException("Days have to be between 1 and 365", null));
        assertThrows(ConstraintViolationException.class, () -> this.controller.getMeanValues("068358bb-d194-48b2-8f36-e7e55805a8fa", 370));
    }
}