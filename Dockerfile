# Use lightweight OpenJDK 17 image
FROM openjdk:17-jdk-slim

# Copy the built jar file into the container
COPY target/*.jar dtreasures-0.0.1-SNAPSHOT.jar

# Run the jar file
ENTRYPOINT ["java", "-jar", "dtreasures-0.0.1-SNAPSHOT.jar"]