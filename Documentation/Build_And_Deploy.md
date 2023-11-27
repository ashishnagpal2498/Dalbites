# Build and Deployment Guide

## Java (Backend)

### 1. Maven Build
- Ensure you have Maven installed on your machine.
- Open a terminal in the project root directory.
- Run the following Maven command to build the project:
  ```bash
  mvn clean install

### 2. Run the Application
- Once the build is successful, run the application using the Spring Boot Maven plugin:
  ```bash
    mvn spring-boot:run

### 3. Access the Application
- The backend will be accessible at http://localhost:8080. Ensure that the necessary dependencies are configured, such as a MySQL database and other environment-specific settings.

## React Native (Frontend)

### 1. Install Dependencies
- Make sure you have Node.js and npm installed on your machine.
- Navigate to the _____ directory (where package.json is located) in a terminal.
- Run the following command to install project dependencies:
  ```bash
    npm install

### 2. Start the Application
- After installing dependencies, start the React Native development server:
  ```bash
    npm start

### 3. Run on Android
- To run the app on a specific platform, use the following commands:

Android: npm run android

### 4. Access the Application
- The React Native app will be accessible on the specified platforms, and the development server typically runs at http://localhost:19002. Follow the instructions in the terminal to open the app on a connected device or emulator.

By following these steps, you can successfully build and deploy both the Java backend and React Native frontend components of the application.
