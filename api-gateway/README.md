# API Gateway

Makes use of Spring Cloud Gateway which is a lightweight and flexible gateway that enables routing and filtering of requests to microservices.
Moreover, it provides essential features for building robust and scalable microservices-based systems.

## Starting the Application

1. Navigate to **src/main/java/com.microservice.gateway**
2. Open **ApiGatewayApplication** class
3. **Run** ApiGatewayApplication

## Create new routes

1. Navigate to **src/main/java/com.microservice.gateway**
2. Open **ApiGatewayApplication** class
3. Find **routeLocator** method
4. Add new route via **.route(...)** before **.build()** call