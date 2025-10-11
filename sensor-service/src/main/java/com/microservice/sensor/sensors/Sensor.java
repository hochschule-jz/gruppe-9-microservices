package com.microservice.sensor.sensors;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

/**
 * Represents a sensor at a given location which captures measurements
 */
@Getter
@Setter
@Document
public class Sensor {
    @Id
    private String id;

    private String name;

    private String location;

    private boolean active;

    private SensorType type;

    List<String> measurements;
}
