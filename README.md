# Transfer Route Calculator

A Spring Boot application for calculating optimal transfer routes based on weight and cost constraints.

## Prerequisites

- Java JDK 17 or later
- Gradle

## Building the Application

From the project root directory:

```bash
# Using Gradle Wrapper
./gradlew build   # On Linux/Mac
.\gradlew.bat build   # On Windows

# OR using Gradle directly if installed
gradle build
```

The built JAR file will be located in `build/libs/transfer-0.0.1-SNAPSHOT.jar`

## Running the Application

After building, you can run the application using:

```bash
java -jar build/libs/transfer-0.0.1-SNAPSHOT.jar
```

The application will start on port 8080.

## API Usage

The application exposes a REST API endpoint for calculating transfer routes.

### Calculate Transfer Route

**Endpoint:** POST `/api/transfers/calculate`

**Request Body:**
```json
{
    "maxWeight": 15,
    "availableTransfers": [
        {
            "weight": 5,
            "cost": 10
        },
        {
            "weight": 10,
            "cost": 20
        },
        {
            "weight": 3,
            "cost": 5
        },
        {
            "weight": 8,
            "cost": 15
        }
    ]
}
```

**Response:**
```json
{
    "selectedTransfers": [
        {
            "weight": 5,
            "cost": 10
        },
        {
            "weight": 10,
            "cost": 20
        }
    ],
    "totalCost": 30,
    "totalWeight": 15
}
```

### Example CURL Commands

**Windows (Command Prompt):**
```bash
curl -X POST http://localhost:8080/api/transfers/calculate -H "Content-Type: application/json" -d "{\"maxWeight\": 15, \"availableTransfers\": [{\"weight\": 5, \"cost\": 10}, {\"weight\": 10, \"cost\": 20}, {\"weight\": 3, \"cost\": 5}, {\"weight\": 8, \"cost\": 15}]}"
```

**Linux/Mac/Git Bash:**
```bash
curl -X POST http://localhost:8080/api/transfers/calculate -H "Content-Type: application/json" -d '{
  "maxWeight": 15,
  "availableTransfers": [
    {"weight": 5, "cost": 10},
    {"weight": 10, "cost": 20},
    {"weight": 3, "cost": 5},
    {"weight": 8, "cost": 15}
  ]
}'
```

## Implementation Details

The application implements a solution to the 0/1 knapsack problem using dynamic programming to find the optimal combination of transfers that:
- Maximizes total cost
- Keeps total weight under or equal to the specified limit
- Handles various edge cases (empty transfers, zero weight limit, etc.)

## Project Structure

```
src/
├── main/
│   └── java/
│       └── com/
│           └── zerobyte/
│               └── transfer/
│                   ├── TransferApplication.java
│                   ├── controller/
│                   │   └── TransferController.java
│                   ├── service/
│                   │   └── TransferService.java
│                   └── objects/
│                       ├── Transfer.java
│                       ├── TransferRequest.java
│                       └── TransferResponse.java
└── test/
    └── java/
        └── com/
            └── zerobyte/
                └── transfer/
                    ├── controller/
                    │   └── TransferControllerTest.java
                    ├── service/
                    │   └── TransferServiceTest.java
                    └── TransferApplicatonTest.java
```
