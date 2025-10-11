package com.microservice.sensor.sensors;

import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * Represents the repository for all sensor-related queries
 */
public interface SensorRepository extends MongoRepository<Sensor, String> { }
