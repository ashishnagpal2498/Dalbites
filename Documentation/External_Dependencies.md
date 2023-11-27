# DalBites Project Backend

This project, **Dalbites**, serves as the backend for the DalBites Project. Below is a list of dependencies used in the project, along with a short description of each, explaining why it is included.

## Dependencies

### 1. Spring Boot Starter Web
- **GroupId:** org.springframework.boot
- **ArtifactId:** spring-boot-starter-web
- **Version:** 2.6.3
- **Description:** Starter for building web, including RESTful, applications. It simplifies the setup and development of web applications.

### 2. Spring Boot DevTools
- **GroupId:** org.springframework.boot
- **ArtifactId:** spring-boot-devtools
- **Version:** 2.6.3
- **Scope:** runtime
- **Optional:** true
- **Description:** Provides tools for automatic application restarts, among other development-time features, improving the development experience.

### 3. Spring Boot Starter Test
- **GroupId:** org.springframework.boot
- **ArtifactId:** spring-boot-starter-test
- **Version:** 2.6.3
- **Scope:** test
- **Description:** Starter for testing Spring Boot applications, including JUnit, Hamcrest, and Mockito dependencies.

### 4. Spring WebFlux
- **GroupId:** org.springframework
- **ArtifactId:** spring-webflux
- **Version:** 5.3.14
- **Scope:** test
- **Description:** Provides reactive programming support for building non-blocking, event-driven applications.


### 5. Spring Boot Starter Data JPA
- **GroupId:** org.springframework.boot
- **ArtifactId:** spring-boot-starter-data-jpa
- **Version:** 2.6.3
- **Description:** Starter for using Spring Data JPA for database access and manipulation.

### 6. MySQL Connector/J
- **GroupId:** com.mysql
- **ArtifactId:** mysql-connector-j
- **Version:** 8.0.28
- **Scope:** runtime
- **Description:** MySQL JDBC driver for connecting to MySQL databases.

### 7. Project Lombok
- **GroupId:** org.projectlombok
- **ArtifactId:** lombok
- **Version:** 1.18.30
- **Scope:** provided
- **Description:** Library that simplifies Java code by providing annotations to generate boilerplate code (getters, setters, etc.) during compilation.

### 8. Spring Boot Starter Security
- **GroupId:** org.springframework.boot
- **ArtifactId:** spring-boot-starter-security
- **Version:** 2.6.3
- **Description:** Starter for securing Spring applications using Spring Security.

### 9. JJWT (Java JWT: JSON Web Token for Java)
- **GroupId:** io.jsonwebtoken
- **ArtifactId:** jjwt
- **Version:** 0.9.1
- **Description:** Library for handling JSON Web Tokens, useful for authentication and authorization.

### 10. JAXB API
- **GroupId:** javax.xml.bind
- **ArtifactId:** jaxb-api
- **Version:** 2.3.1
- **Description:** Java Architecture for XML Binding (JAXB) API for working with XML data.

### 11. Javax Persistence API
- **GroupId:** javax.persistence
- **ArtifactId:** javax.persistence-api
- **Version:** 2.2
- **Description:** API for Java Persistence, providing a framework for managing relational data in Java applications.

### 12. Spring Boot Starter Mail
- **GroupId:** org.springframework.boot
- **ArtifactId:** spring-boot-starter-mail
- **Version:** 2.6.3
- **Description:** Starter for sending email using Spring's Email abstraction and JavaMail.

### 13. Firebase Admin SDK
- **GroupId:** com.google.firebase
- **ArtifactId:** firebase-admin
- **Version:** 8.1.0
- **Description:** SDK for interacting with Firebase services, used for various cloud functionalities.

### 14. Google Cloud Storage
- **GroupId:** com.google.cloud
- **ArtifactId:** google-cloud-storage
- **Version:** 1.96.0
- **Description:** Client library for Google Cloud Storage, enabling interaction with Google Cloud Storage services.

### 15. Google API Client
- **GroupId:** com.google.api-client
- **ArtifactId:** google-api-client
- **Version:** 1.30.4
- **Description:** Core library for Google API clients, allowing interaction with various Google APIs.

### 16. Mockito Core
- **GroupId:** org.mockito
- **ArtifactId:** mockito-core
- **Version:** 3.12.4
- **Scope:** test
- **Description:** Mocking framework for unit tests in Java.

### 17. Stripe Java
- **GroupId:** com.stripe
- **ArtifactId:** stripe-java
- **Version:** 20.119.0
- **Description:** Java library for the Stripe API, facilitating payment processing in the application.

## Build
The project is built using the Spring Boot Maven Plugin, configured in the build section of the POM file. The final name for the built artifact is set to **asdc-dalbites-backend**.

Feel free to reach out if you have any questions or need further clarification on the dependencies and their usage in the project.

# DalBites Project Frontend

This React Native project, named DalBites, is built using Expo. Below is a list of frontend dependencies used in the project, along with a short description of each, explaining why it is included.

## Dependencies

### 1. [@expo/vector-icons](https://docs.expo.dev/guides/icons/)
- **Version:** ^13.0.0
- **Description:** A library that provides a set of customizable vector icons for use with Expo.

### 2. [@expo/webpack-config](https://docs.expo.dev/guides/customizing-webpack/)
- **Version:** ^19.0.0
- **Description:** Configuration package for Webpack in Expo projects, allowing customization of the bundling process.

### 3. [@react-native-picker/picker](https://github.com/react-native-picker/picker)
- **Version:** ^2.5.1
- **Description:** A cross-platform picker component for React Native, providing a consistent interface for selecting items.

### 4. [@react-navigation/bottom-tabs](https://reactnavigation.org/docs/bottom-tab-navigator/)
- **Version:** ^6.5.9
- **Description:** React Navigation library for implementing a bottom tab navigator in the application.

### 5. [@react-navigation/drawer](https://reactnavigation.org/docs/drawer-based-navigation/)
- **Version:** ^6.6.4
- **Description:** React Navigation library for implementing a drawer navigation in the application.

### 6. [@react-navigation/native](https://reactnavigation.org/docs/getting-started/)
- **Version:** ^6.1.8
- **Description:** Core library of React Navigation, providing the fundamental building blocks for navigators.

### 7. [@react-navigation/stack](https://reactnavigation.org/docs/stack-navigator/)
- **Version:** ^6.3.18
- **Description:** React Navigation library for implementing a stack navigator in the application.

### 8. [@stripe/stripe-react-native](https://github.com/stripe/stripe-react-native)
- **Version:** ^0.35.0
- **Description:** Stripe's React Native SDK for securely handling payments within the application.

### 9. [axios](https://axios-http.com/)
- **Version:** ^1.5.1
- **Description:** A promise-based HTTP client for making requests to the server or external APIs.

### 10. [expo](https://expo.dev/)
- **Version:** ~49.0.13
- **Description:** Expo development environment for building React Native applications without native setup.

### 11. [expo-image-picker](https://docs.expo.dev/versions/latest/sdk/imagepicker/)
- **Version:** ^14.5.0
- **Description:** Expo SDK module for accessing the device's camera roll or taking a photo.

### 12. [expo-secure-store](https://docs.expo.dev/versions/latest/sdk/securestore/)
- **Version:** ~12.3.1
- **Description:** Expo SDK module for securely storing key-value pairs on the device.

### 13. [expo-status-bar](https://docs.expo.dev/versions/latest/sdk/status-bar/)
- **Version:** ~1.6.0
- **Description:** Expo SDK module for controlling the appearance of the status bar in the application.

### 14. [i](https://www.npmjs.com/package/i)
- **Version:** ^0.3.7
- **Description:** A simple utility for checking if a value is truthy.

### 15. [jest](https://jestjs.io/)
- **Version:** ^29.7.0
- **Description:** A JavaScript testing framework for unit testing applications.

### 16. [jest-expo](https://docs.expo.dev/guides/testing-with-jest/)
- **Version:** ^49.0.0
- **Description:** Expo-specific setup for Jest, allowing testing of Expo projects.

### 17. [lodash](https://lodash.com/)
- **Version:** ^4.17.21
- **Description:** A utility library that provides helpful functions for working with arrays, objects, and more.

### 18. [moment](https://momentjs.com/)
- **Version:** ^2.29.4
- **Description:** A library for parsing, validating, manipulating, and formatting dates.

### 19. [npm](https://www.npmjs.com/)
- **Version:** ^10.2.3
- **Description:** Node Package Manager for managing project dependencies.

### 20. [react](https://reactjs.org/)
- **Version:** 18.2.0
- **Description:** A JavaScript library for building user interfaces.

### 21. [react-native](https://reactnative.dev/)
- **Version:** 0.72.6
- **Description:** A framework for building native applications using React.

### 22. [react-native-dropdown-select-list](https://www.npmjs.com/package/react-native-dropdown-select-list)
- **Version:** ^2.0.5
- **Description:** A customizable dropdown/select list for React Native.

### 23. [react-native-gesture-handler](https://docs.swmansion.com/react-native-gesture-handler/docs/)
- **Version:** ~2.12.0
- **Description:** A declarative API exposing platform-native touch and gesture system to React Native.

### 24. [react-native-image-picker](https://github.com/react-native-image-picker/react-native-image-picker)
- **Version:** ^7.0.2
- **Description:** A React Native module for selecting images from the device's gallery or taking a photo with the camera.

### 25. [react-native-modal](https://github.com/react-native-modal/react-native-modal)
- **Version:** ^13.0.1
- **Description:** A React Native modal component for creating modals in the application.

### 26. [react-native-paper](https://callstack.github.io/react-native-paper/)
- **Version:** ^5.11.1
- **Description:** A material design component library for React Native.

### 27. [react-native-reanimated](https://docs.swmansion.com/react-native-reanimated/docs/)
- **Version:** ~3.3.0
- **Description:** A React Native library for creating smooth and complex animations.

### 28. [react-native-safe-area-context](https://reactnavigation.org/docs/handling-safe-area/)
- **Version:** 4.6.3
- **Description:** React Native library for handling safe areas, ensuring content displays correctly on devices with notches or rounded corners.

### 29. [react-native-screens](https://github.com/software-mansion/react-native-screens)
- **Version:** ~3.22.0
- **Description:** Native navigation primitives for React Native.

### 30. [react-native-select-dropdown](https://www.npmjs.com/package/react-native-select-dropdown)
- **Version:** ^3.4.0
- **Description:** A custom dropdown/select component for React Native.

### 31. [react-native-switch](https://www.npmjs.com/package/react-native-switch)
- **Version:** ^1.5.1
- **Description
