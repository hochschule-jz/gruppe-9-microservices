package com.microservice.gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@EnableDiscoveryClient
public class ApiGatewayApplication {
    public static void main(String[] args) {
        SpringApplication.run(ApiGatewayApplication.class, args);
    }

    /**
     * Constructs necessary routes for sensors and measurements and enables load-balancing
     * @param builder Used to create the routes
     * @return Available routes of application which use load-balancing
     */
    @Bean
    public RouteLocator routeLocator(RouteLocatorBuilder builder) {
        return builder.routes()
                .route("sensor-route", r -> r.path("/api/sensors/**").uri("lb://sensor-service"))
                .route("measurement-route", r -> r.path("/api/measurements/**").uri("lb://sensor-service"))
                .build();
    }
}
