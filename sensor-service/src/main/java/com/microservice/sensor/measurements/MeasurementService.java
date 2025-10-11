package com.microservice.sensor.measurements;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.*;

@Service
public class MeasurementService {
    @Autowired
    private MeasurementRepository measurementRepository;

    /**
     * Gets the entirety of measurements or all sensor measurements depending on the existence of sensor parameter
     * @param sensor Optional ID of the sensor whose measurements are affected
     * @return List of measurements
     */
    public List<Measurement> getMeasurements(Optional<String> sensor) {
        List<Measurement> measurements;

        if (sensor.isPresent()) {
            measurements = this.measurementRepository.findBySensor(sensor.get());
        } else {
            measurements = this.measurementRepository.findAll();
        }

        return measurements;
    }

    /**
     * Gets one specific measurement
     * @param id ID of the measurement to be searched for
     * @return Measurement that holds the same id value as the id parameter
     * @throws RuntimeException If no measurement with the given id exists
     */
    public Measurement getMeasurementById(String id) {
        return this.measurementRepository.findById(id).orElseThrow(() -> new RuntimeException("No measurement with ID: " + id));
    }

    /**
     * Creates a new measurement
     * @param measurement Measurement to be created
     * @return Created measurement including an ID and timestamp
     */
    public Measurement createMeasurement(Measurement measurement) {
        return this.measurementRepository.insert(measurement);
    }

    /**
     * Updates an already existing measurement
     * @param measurement Measurement to be updated
     * @return Measurement with updated values
     */
    public Measurement updateMeasurement(Measurement measurement) {
        return this.measurementRepository.save(measurement);
    }

    /**
     * Deletes an already existing measurement
     * @param id ID of the measurement to be deleted
     */
    public void deleteMeasurement(String id) {
        this.measurementRepository.deleteById(id);
    }

    /**
     * Calculates and returns the mean temperature and humidity values of a sensor over the given days
     * @param sensor ID of sensor whose measurements are affected
     * @param days Specifies the number of days in the past which is used to calculate the timespan of measurements
     * @return List of mean values in the past n number of days
     */
    public List<MeanValue> getMeanValues(String sensor, int days) {
        List<MeanValue> meanValues = new ArrayList<>();

        // Use Calendar to subtract days
        Calendar cal = Calendar.getInstance();
        Date maxDate = cal.getTime();
        cal.add(Calendar.DAY_OF_YEAR, -days);
        Date minDate = cal.getTime();

        // Get all measurements of sensor during the calculated timespan
        List<Measurement> measurements = this.measurementRepository.findBySensorAndTimestampBetween(sensor, minDate, maxDate);

        // Set number of time entries depending on the amount of days
        int times = days == 1 ? 25 : days;

        for (int i = 0; i < times; i++) {
            float meanTemperature = 0;
            float meanHumidity = 0;

            // Duration of the start of timespan
            Duration duration1 = days == 1 ? Duration.ofHours(i) : Duration.ofDays(i);
            // Duration of the end of timespan
            Duration duration2 = days == 1 ? Duration.ofHours(i + 1) : Duration.ofDays(i + 1);

            Date fromDate = Date.from(minDate.toInstant().plus(duration1));
            Date toDate = Date.from(minDate.toInstant().plus(duration2));

            // Limit measurements to only those where the timestamp is between the fromDate and toDate
            List<Measurement> measurementsBetween = measurements.stream()
                    .filter(measurement -> measurement.getTimestamp().after(fromDate) && measurement.getTimestamp().before(toDate))
                    .toList();

            // If there are any measurements during the timespan --> calculate the mean temperature and humidity values
            if (!measurementsBetween.isEmpty()) {
                meanTemperature = measurementsBetween.stream().map(Measurement::getTemperature).reduce(0F, Float::sum) / measurementsBetween.size();
                meanHumidity = measurementsBetween.stream().map(Measurement::getHumidity).reduce(0F, Float::sum) / measurementsBetween.size();
            }

            meanValues.add(new MeanValue(days == 1 ? i : i + 1, meanTemperature, meanHumidity));
        }

        return meanValues;
    }
}
