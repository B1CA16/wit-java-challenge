FROM openjdk:24-ea-jdk-slim

WORKDIR /app

# Create logs directory
RUN mkdir -p logs

# Copy the JAR file
COPY target/calculator-core-0.0.1-SNAPSHOT.jar app.jar

# Expose port
EXPOSE 8081

# Run the application
ENTRYPOINT ["java", "-jar", "app.jar"]