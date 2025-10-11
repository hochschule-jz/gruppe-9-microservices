package com.microservice.sensor.sensors;

import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/api")
public class SensorController {
    @Autowired
    private SensorService sensorService;

    /**
     * Get all sensors
     * @return Response with status code 200 and the list of all sensors
     */
    @Operation(summary = "Get all sensors")
    @GetMapping(path = "/sensors")
    public ResponseEntity<List<Sensor>> getAllSensors() {
        return ResponseEntity.ok(this.sensorService.getSensors());
    }

    /**
     * Get one specific sensor
     * @param id ID of the sensor to be searched for
     * @return Response with status code 200 and the retrieved sensor
     */
    @Operation(summary = "Get a sensor by its id")
    @GetMapping(path = "/sensors/{id}")
    public ResponseEntity<Sensor> getSensorById(@PathVariable String id) {
        return ResponseEntity.ok(this.sensorService.getSensorById(id));
    }

    /**
     * Creates a new sensor
     * @param sensor Sensor to be created
     * @return Response with status code 200 and the created sensor
     */
    @Operation(summary = "Create a sensor")
    @PostMapping(path = "/sensors")
    public ResponseEntity<Sensor> createSensor(@RequestBody Sensor sensor) {
        return ResponseEntity.ok(this.sensorService.createSensor(sensor));
    }

    /**
     * Updates an already existing sensor
     * @param sensor Sensor to be updated
     * @return Response with status code 200 and the updated sensor
     */
    @Operation(summary = "Update a sensor")
    @PutMapping(path = "/sensors/{id}")
    public ResponseEntity<Sensor> updateSensor(@RequestBody Sensor sensor) {
        return ResponseEntity.ok(this.sensorService.updateSensor(sensor));
    }

    /**
     * Deletes an already existing sensor
     * @param id ID of sensor to be deleted
     * @return Response with status code 204 and no content
     */
    @Operation(summary = "Delete a sensor")
    @DeleteMapping(path = "/sensors/{id}")
    public ResponseEntity<?> deleteSensor(@PathVariable String id) {
        this.sensorService.deleteSensor(id);
        return ResponseEntity.noContent().build();
    }
}
