package com.microservice.sensor.measurements;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

/**
 * Holds the mean values of temperature and humidity at a given time unit
 */
@Getter
@Setter
@RequiredArgsConstructor
public class MeanValue {
    private final int time;

    private final float temperature;

    private final float humidity;
}
