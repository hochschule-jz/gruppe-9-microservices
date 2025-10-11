# Discovery Service

Makes use of Spring Eureka Server which provides service registration and discovery in a microservice architecture.
It allows services to register themselves and discover other services in the system, enabling dynamic scaling, load balancing, and fault-tolerance.

## Starting the Application

1. Navigate to **src/main/java/com.microservice.discovery**
2. Open **DiscoveryServiceApplication** class
3. **Run** DiscoveryServiceApplication

## Display registered services

In order to have a look at the registered services -> open browser with URL **http://localhost:8761**.
There you should see a list of all currently available/active services.