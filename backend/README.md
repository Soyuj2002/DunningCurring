# Dunning and Curring Backend

This is the backend service for the Dunning and Curring project, built using Spring Boot. It provides APIs for managing dunning and curing processes, user authentication, and other backend functionalities.

## Features
- User authentication and authorization using JWT.
- Management of customers, payments, notifications, and dunning rules.
- Scheduler for automated dunning processes.
- RESTful APIs for frontend integration.

## Project Structure
```
backend/
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── com/prodapt/DunningCurring/
│   │   │       ├── Config/
│   │   │       ├── Controller/
│   │   │       ├── DAO/
│   │   │       ├── DTO/
│   │   │       ├── Entity/
│   │   │       ├── Exception/
│   │   │       ├── Scheduler/
│   │   │       ├── Security/
│   │   │       └── Service/
│   │   └── resources/
│   │       ├── application.properties
│   │       ├── static/
│   │       └── templates/
│   └── test/
│       └── java/
│           └── com/prodapt/DunningCurring/
├── pom.xml
├── mvnw
└── mvnw.cmd
```

## Prerequisites
- Java 17 or later
- Maven
- MySQL

## Setup
1. Clone the repository.
2. Navigate to the `backend` directory.
3. Configure the database connection in `src/main/resources/application.properties`.
4. Run the application using:
   ```
   ./mvnw spring-boot:run
   ```

## API Endpoints
The backend provides the following API endpoints:
- `/auth` - Authentication-related endpoints.
- `/customers` - Customer management.
- `/payments` - Payment processing.
- `/dunning` - Dunning process management.
- `/notifications` - Notification management.

Refer to the API documentation for detailed information.