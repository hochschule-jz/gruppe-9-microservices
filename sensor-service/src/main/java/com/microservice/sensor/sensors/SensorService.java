package com.microservice.sensor.sensors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SensorService {

    @Autowired
    private SensorRepository sensorRepository;

    /**
     * Get all sensors
     * @return List of all available sensors
     */
    public List<Sensor> getSensors() {
        return this.sensorRepository.findAll();
    }

    /**
     * Returns one specific sensor
     * @param id ID of the sensor to be searched for
     * @return Sensor that holds the same id value as the id parameter
     * @throws RuntimeException If no sensor with the given id exists
     */
    public Sensor getSensorById(String id) {
        return this.sensorRepository.findById(id).orElseThrow(() -> new RuntimeException("No sensor with ID: " + id));
    }

    /**
     * Creates a new sensor
     * @param sensor Sensor to be created
     * @return Created sensor including an ID
     */
    public Sensor createSensor(Sensor sensor) {
        return this.sensorRepository.insert(sensor);
    }

    /**
     * Updates an already existing sensor
     * @param sensor Sensor to be updated
     * @return Sensor with updated values
     */
    public Sensor updateSensor(Sensor sensor) {
        return this.sensorRepository.save(sensor);
    }

    /**
     * Deletes an already existing sensor
     * @param id ID of the sensor to be deleted
     */
    public void deleteSensor(String id) {
        this.sensorRepository.deleteById(id);
    }
}
