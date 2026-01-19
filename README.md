# Dunning Curing Project

## Overview
This project is a comprehensive solution for managing dunning and curing processes. It consists of two main components:

1. **Backend**: A Java-based backend built with Spring Boot, providing APIs and business logic for the application.
2. **Frontend**: A React-based frontend built with Vite, offering a user-friendly interface for interacting with the system.

## Project Structure

### Backend
- **Path**: `backend/`
- **Technologies**: Java, Spring Boot
- **Key Folders**:
  - `src/main/java`: Contains the main application code.
  - `src/main/resources`: Configuration files and templates.
  - `target/`: Compiled classes and build artifacts.

### Frontend
- **Path**: `dunning-frontend/`
- **Technologies**: React, Vite
- **Key Folders**:
  - `src/`: Contains the main React components, pages, and services.
  - `public/`: Static assets.

## Prerequisites

- **Backend**:
  - Java 11 or higher
  - Maven

- **Frontend**:
  - Node.js (v16 or higher)
  - npm or yarn

## Setup Instructions

### Backend
1. Navigate to the `backend/` directory:
   ```bash
   cd backend
   ```
2. Build the project using Maven:
   ```bash
   ./mvnw clean install
   ```
3. Run the application:
   ```bash
   ./mvnw spring-boot:run
   ```

### Frontend
1. Navigate to the `dunning-frontend/` directory:
   ```bash
   cd dunning-frontend
   ```
2. Install dependencies:
   ```bash
   npm install
   ```
3. Start the development server:
   ```bash
   npm run dev
   ```

## Contributing

1. Fork the repository.
2. Create a new branch for your feature or bug fix.
3. Commit your changes and push the branch.
4. Create a pull request.

## License
This project is licensed under the MIT License. See the LICENSE file for details.