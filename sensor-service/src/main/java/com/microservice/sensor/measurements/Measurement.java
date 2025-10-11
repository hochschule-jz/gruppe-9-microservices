package com.microservice.sensor.measurements;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

/**
 * Represents a measurement of temperature and humidity which is conducted by a sensor at a given time
 */
@Getter
@Setter
@Document
public class Measurement {
    @Id
    private String id;

    @CreatedDate
    private Date timestamp;

    private float temperature;

    private float humidity;

    private String sensor;
}
