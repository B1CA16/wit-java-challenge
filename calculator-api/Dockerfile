FROM openjdk:24-ea-jdk-slim

WORKDIR /app

# Create logs directory
RUN mkdir -p logs

# Copy the JAR file
COPY target/calculator-api-0.0.1-SNAPSHOT.jar app.jar

# Expose port
EXPOSE 8080

# Run the application
ENTRYPOINT ["java", "-jar", "app.jar"]