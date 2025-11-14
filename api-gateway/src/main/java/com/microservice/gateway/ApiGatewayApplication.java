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
     * Constructs the necessary routes and enables load-balancing
     * @param builder Used to create the routes
     * @return Available routes of application which use load-balancing
     */
    @Bean
    public RouteLocator routeLocator(RouteLocatorBuilder builder) {
        return builder.routes()
                .route("guest-route", r -> r.path("/api/guests/**").filters(f -> f.stripPrefix(1)).uri("lb://hotel-service"))
                .route("room-route", r -> r.path("/api/rooms/**").filters(f -> f.stripPrefix(1)).uri("lb://hotel-service"))
                .route("booking-route", r -> r.path("/api/bookings/**").filters(f -> f.stripPrefix(1)).uri("lb://hotel-service"))
                .build();
    }
}