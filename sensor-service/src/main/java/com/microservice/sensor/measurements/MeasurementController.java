package com.microservice.sensor.measurements;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(value = "/api")
@Validated
public class MeasurementController {
    @Autowired
    private MeasurementService measurementService;

    /**
     * Get all measurements
     * @param sensor Optional ID of sensor which limits measurements to that sensor
     * @return Response with status code 200 and the list of all recorded measurements
     */
    @Operation(summary = "Get all measurements")
    @GetMapping(path = "/measurements")
    public ResponseEntity<List<Measurement>> getAllMeasurements(@RequestParam Optional<String> sensor) {
        return ResponseEntity.ok(this.measurementService.getMeasurements(sensor));
    }

    /**
     * Get one specific measurement
     * @param id ID of the measurement to be searched for
     * @return Response with status code 200 and the retrieved measurement
     */
    @Operation(summary = "Get a measurement by its id")
    @GetMapping(path = "/measurements/{id}")
    public ResponseEntity<Measurement> getMeasurementById(@PathVariable String id) {
        return ResponseEntity.ok(this.measurementService.getMeasurementById(id));
    }

    /**
     * Create a new measurement
     * @param measurement Measurement to be created
     * @return Response with status code 200 and the created measurement
     */
    @Operation(summary = "Create a measurement")
    @PostMapping(path = "/measurements")
    public ResponseEntity<Measurement> createMeasurement(@RequestBody Measurement measurement) {
        return ResponseEntity.ok(this.measurementService.createMeasurement(measurement));
    }

    /**
     * Updates an already existing measurement
     * @param measurement Measurement to be updated
     * @return Response with status code 200 and the updated measurement
     */
    @Operation(summary = "Update a measurement")
    @PutMapping(path = "/measurements/{id}")
    public ResponseEntity<Measurement> updateMeasurement(@RequestBody Measurement measurement) {
        return ResponseEntity.ok(this.measurementService.updateMeasurement(measurement));
    }

    /**
     * Deletes an already existing measurement
     * @param id ID of the measurement to be deleted
     * @return Response with status code 204 and no content
     */
    @Operation(summary = "Delete a measurement")
    @DeleteMapping(path = "/measurements/{id}")
    public ResponseEntity<?> deleteMeasurement(@PathVariable String id) {
        this.measurementService.deleteMeasurement(id);
        return ResponseEntity.noContent().build();
    }

    /**
     * Get mean values for temperature and humidity measurements
     * @param sensor ID of sensor whose measurements are affected
     * @param days Number of days in the past which is used as the lower boundary for the timespan
     * @return Response with status code 200 and the list of all mean values for sensor measurements
     */
    @Operation(summary = "Get mean values for temperature and humidity measurements")
    @GetMapping(path = "/measurements/mean")
    public ResponseEntity<List<MeanValue>> getMeanValues(
            @RequestParam String sensor,
            @RequestParam
            @Min(value = 1, message = "Days have to be between 1 and 365")
            @Max(value = 365, message = "Days have to be between 1 and 365")
            int days) {
        return ResponseEntity.ok(this.measurementService.getMeanValues(sensor, days));
    }
}
