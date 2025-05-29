# Calculator App

A distributed calculator application built with Spring Boot and Apache Kafka, consisting of two modules:

-   **calculator-api**: REST API module that exposes calculator endpoints
-   **calculator-core**: Core calculation logic module that processes operations

## Architecture

The application uses Apache Kafka for inter-module communication:

1. The API module receives HTTP requests and publishes calculation requests to the Kafka topic `calculation-requests`
2. The Core module consumes these requests, performs the calculations, and publishes the results to the Kafka topic `calculation-results`
3. The API module consumes the result and returns the final response to the HTTP client

## Features

-   RESTful API with basic arithmetic operations (sum, subtract, multiply, divide)
-   Support for arbitrary precision decimal numbers using BigDecimal
-   Unique request identifiers with MDC logging propagation
-   SLF4J logging with file appenders
-   Docker containerization
-   Unit tests

## Prerequisites

-   Java 24
-   Maven 3.6+
-   Docker and Docker Compose

## Building the Project

### 1. Clone the Repository

```bash
git clone https://github.com/b1ca16/wit-java-challenge.git
```

### 2. Build with Maven

```bash
# Build all modules
mvn clean package

# Or build individual modules
cd calculator-core && mvn clean package
cd calculator-api && mvn clean package
```

### 3. Build Docker Images

```bash
# Build both images
docker-compose build

# Or build individually
docker build -t calculator-core ./calculator-core
docker build -t calculator-api ./calculator-api
```

## Running the Application

### Using Docker Compose

```bash
# Start all services (Kafka, Zookeeper, and both applications)
docker-compose up -d

# View logs
docker-compose logs -f

# Stop all services
docker-compose down
```

## API Usage

The calculator API is available at `http://localhost:8080/api/calculator`

### Endpoints

-   **GET** `/api/calculator/sum?a={value}&b={value}` - Addition
-   **GET** `/api/calculator/subtract?a={value}&b={value}` - Subtraction
-   **GET** `/api/calculator/multiply?a={value}&b={value}` - Multiplication
-   **GET** `/api/calculator/divide?a={value}&b={value}` - Division

### Examples

```bash
# Addition: 1 + 2 = 3
curl "http://localhost:8080/api/calculator/sum?a=1&b=2"

# Subtraction: 10 - 3 = 7
curl "http://localhost:8080/api/calculator/subtract?a=10&b=3"

# Multiplication: 4 * 5 = 20
curl "http://localhost:8080/api/calculator/multiply?a=4&b=5"

# Division: 15 / 3 = 5
curl "http://localhost:8080/api/calculator/divide?a=15&b=3"

# High precision decimals
curl "http://localhost:8080/api/calculator/sum?a=1.123456789&b=2.987654321"
```

### Response Format

**Success Response:**

```json
{
    "result": 3
}
```

**Error Response:**

```json
{
    "error": "Division by zero is not allowed."
}
```

All responses include an `X-Request-ID` header for request tracking.

## Testing

```bash
# Run all tests
mvn test

# Run tests for specific module
cd calculator-core && mvn test
cd calculator-api && mvn test
```

## Logging

Logs are written to:

-   Console output with request IDs in MDC
-   File logs in `logs/` directory:
    -   `calculator-api.log`
    -   `calculator-core.log`
