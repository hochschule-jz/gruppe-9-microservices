package com.microservice.sensor.measurements;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

/**
 * Represents the repository all measurement-related queries
 */
@Repository
public interface MeasurementRepository extends MongoRepository<Measurement, String> {
    /**
     * Returns all measurements that have been captured by the given sensor
     * @param sensor ID of the sensor whose measurements are affected
     * @return List of sensor-related measurements
     */
    List<Measurement> findBySensor(String sensor);

    /**
     * Returns all measurements of a sensor that have been captured between the two given dates
     * @param sensor ID of the sensor whose measurements are affected
     * @param minDate Date which represents the lower boundary of the timespan
     * @param maxDate Date which represents the upper boundary of the timespan
     * @return List of sensor-related measurements during the timespan
     */
    List<Measurement> findBySensorAndTimestampBetween(String sensor, Date minDate, Date maxDate);
}
