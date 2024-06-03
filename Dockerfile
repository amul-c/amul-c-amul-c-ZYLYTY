# Use a Java runtime as base image
FROM openjdk:17-jdk-alpine

# Set the working directory inside the container
WORKDIR /app

# Copy the Spring Boot application JAR file into the container
COPY target/productinventrymanagementapp-0.0.1-SNAPSHOT.jar /app/productinventrymanagementapp-0.0.1-SNAPSHOT.jar

# Expose the port your Spring Boot application uses (e.g., 8080)
EXPOSE 8080

# Define the command to run your application
CMD ["java", "-jar", "productinventrymanagementapp-0.0.1-SNAPSHOT.jar"]