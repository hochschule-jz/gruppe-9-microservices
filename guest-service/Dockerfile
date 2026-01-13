# Use a lightweight Java runtime
FROM eclipse-temurin:17-jre-alpine

# Set the working directory inside the container
WORKDIR /app

# Copy the built JAR file into the image
# (Assumes you run 'mvn package' before building docker)
COPY target/*.jar app.jar

# Expose the port (Optional, for documentation)
EXPOSE 8080

# Command to run the app
ENTRYPOINT ["java", "-jar", "app.jar"]