# Starwars App
## Overview
This document provides a comprehensive guide to the architecture, libraries, and patterns used in the development of the Starwars app. The app follows modern Android development best practices, leveraging various libraries and architectural patterns for maintainability, scalability, and testability.

### Architecture
The app uses the MVVM architecture pattern with clean architecture principles. This architecture's clear separation of the presentation layer from business logic and data management promotes maintainability and testability.
When an application is created based on features, it is simple to add, update, or remove new features as required. There is a core feature that contains code common to all of the application's features. This essential component is not dependent on any specific feature. This ensures that it and other features remain untouched by any future additions or deletions.

Each feature is organized into three layers: presentation, domain, and data. The presentation layer contains user interface-related code, the domain handles business logic, and the data layer manages data in the app. The domain depends on the presentation layer, which in turn depends on the data layer.
In the presentation layer, each screen is implemented using Jetpack Compose for fast and intuitive user interface development. Each screen is integrated with a ViewModel tasked with managing its state. UI actions are transmitted to the ViewModel as "Events," while the ViewModel conveys data to the UI through "UiState."
The domain layer encapsulates "Use-cases" for handling UI Events, UI models and abstract repositories, establishing a communication bridge with the data layer. This layer plays a pivotal role in transforming raw data from the data layer into a format compatible with the UI.
Within the data layer, you'll encounter code dedicated to data-related operations. This encompasses API calls, concrete implementations of repositories, pagination mechanisms, and API response and request models.

Each feature encompasses a dedicated dependency injection layer responsible for managing its dependencies. The core feature also has a utils layer for utility functions and classes like internet connection and error handling. 
This meticulous decoupling process ensures that the components remain highly reusable and maintainable, facilitating robust testing procedures.

### Design Patterns
Several design patterns have been incorporated into the application to ensure code efficiency and final product readability. 
#### Singleton
The singleton pattern restricts the creation of a single instance and provides access to that unique instance. Throughout the application, this pattern has been implemented for instances such as Retrofit, API, and repositories. This approach proves valuable in ensuring well-coordinated actions across the entire app.
#### Dependency Injection
This architectural pattern guarantees the streamlined management of app dependencies. The application employs Dagger Hilt for dependency management, specifying dependencies in the injection module and injecting them wherever required. This proves advantageous during testing, as these dependencies can be seamlessly substituted with fakes or mocks, ensuring effective and reliable testing procedures.
#### Builder 
The Builder pattern helps in creating complex objects by separating the construction process from its representation. This allows the same construction steps to result in different forms or variations of the object. Its purpose is to systematically construct a complex object incrementally, with the final step culminating in the return of the fully constructed object.

### Testing
The application has been broadly unit-tested, it helps catch and fix bugs early, ensuring code reliability and overall software quality. The application's decoupled architecture enhances the efficiency of unit testing by simplifying the testing of individual code units. Additionally, the incorporation of dependency injection in the app streamlines the use of fakes and mocks during the testing process. Mockito has been used to create mocks of dependencies, it provides a clean and intuitive syntax for creating mocks. The Turbine library is used for awaiting the collection of flows for asynchronous operations. Mockwebserver is used for simulating network calls to test the API implementations.

### Libraries and Util classes
Several libraries and utility classes/functions have been used throughout the app. From network operations to UI development and testing, each library contributes to the app's robust architecture and seamless user experience. Below is a comprehensive list explaining them:

## MVVM with Clean Architecture
- **Separation of Concerns:** MVVM ensures a clear separation of the presentation layer from the business logic and data handling, promoting maintainability and testability.
- **Testability:** The architecture facilitates unit testing of ViewModel and UseCase components.

## Retrofit
- **Network Operations:** Retrofit simplifies the implementation of network operations, providing a type-safe HTTP client.
- **Easy Integration:** It seamlessly integrates with Kotlin through coroutines and supports multiple data formats.

## Paging 3
- **Efficient Data Loading:** Paging 3 enables efficient loading of large data sets in a Lazy column, reducing memory consumption.
- **Seamless Pagination:** It simplifies the process of paginating data from the network or a local database.

## Kotlin Flows
- **Asynchronous Programming:** Kotlin Flows provide a concise and expressive way to handle asynchronous operations.
- **Integration with Coroutines:** Flows seamlessly integrate with Coroutines, making it easy to handle asynchronous tasks.

## Network Resource Handler Sealed Class
- **Clear Error Handling:** This sealed class provides a structured way to handle network resource states, making error handling clear and manageable.
- **Extensibility:** Easily extendable for future modifications or additions to network resource states.

## Use Cases
- **Business Logic Separation:** Use cases encapsulate business logic, promoting a clean and maintainable codebase.
- **Testability:** Isolating business logic into use cases makes it easier to unit test.

## Model Mapper
- **Data Transformation:** Model mapping ensures a clean separation between the data layer and the presentation layer.
- **Code Maintainability:** Centralizing mapping logic reduces redundancy and enhances code maintainability.

## Retrofit Response Handler Extension Function
- **Centralized Error Handling:** Extension functions on Retrofit responses provide a centralized location for handling network errors.
- **Consistent Error Handling:** Ensures a consistent approach to error handling across the app.

## Exception and Error Type Handlings
- **Granular Error Handling:** Differentiating between exceptions and custom error types allows for granular error handling.
- **User-Friendly Error Messages:** Custom error types enable the presentation of user-friendly error messages.

## StateFlows
- **Reactive UI:** StateFlows provide a reactive approach to UI updates, ensuring consistent and smooth user experiences.
- **Consistent State Management:** Helps manage the UI state in a consistent and predictable manner.

## UIState Handler Sealed Class
- **Structured UI State:** UIState sealed class ensures a structured approach to handling UI state changes.
- **Readability:** Enhances code readability by clearly defining different UI states.

## UI Event Pattern
- **Decoupled Components:** UI events decouple user interactions from the business logic, promoting modularity.
- **Testability:** UI events make it easier to write unit tests for user interactions.

## Jetpack Compose
- **Declarative UI:** Jetpack Compose offers a declarative way to build UI, simplifying UI development.
- **Interactive UI:** Compose allows for interactive UI components with less boilerplate code.

## Navigation Compose
- **Type-Safe Navigation:** Navigation Compose ensures type-safe navigation within the app.
- **Declarative Navigation Graph:** Simplifies the definition of the app's navigation graph.

## Compose State Hoisting
- **Predictable State:** Hoisting state to higher-level components ensures a predictable and controlled flow of data.
- **Maintainable Code:** State hoisting enhances code maintainability by avoiding complex state management.

## Unit Test with JUnit 4
- **Automated Testing:** JUnit 4 facilitates the creation and execution of automated unit tests.
- **Integration with IDEs:** Seamless integration with popular IDEs for efficient test development and execution.

## Mockito
- **Mocking Dependencies:** Mockito simplifies the process of mocking dependencies for unit testing.
- **Flexible Mocking:** Offers flexibility in configuring and verifying interactions with mocked objects.

## MockWebServer
- **Mocking Network Responses:** MockWebServer allows for mocking network responses, facilitating isolated testing of network operations.
- **Consistent Test Environment:** Ensures a consistent and controlled environment for testing network-related functionality.

## Dagger Hilt
- **Dependency Injection:** Dagger Hilt simplifies dependency injection in Android applications.
- **Scalability:** Supports the scalability of the project by managing dependencies effectively.

## Turbine Library
- **Simplified Testing of Flows:** Turbine simplifies the testing of Kotlin Flows by providing utilities for collecting values emitted by flows.
- **Test Readability:** Enhances test readability by offering concise and expressive assertions.

## Coroutines
- **Asynchronous Programming:** Coroutines simplify asynchronous programming in Kotlin.
- **Concurrency:** Enables concurrent and non-blocking execution of tasks, improving app responsiveness.

## Kotlin-Parcelize
- **Efficient Parcelable Implementation:** Kotlin-Parcelize generates efficient Parcelable implementations for data classes.
- **Reduced Boilerplate Code:** Reduces the amount of boilerplate code needed for Parcelable implementation.

### Conclusion
By adopting the MVVM architecture with clean architecture principles and integrating various libraries and patterns, the Starwars app achieves a balance between maintainability, scalability, and testability. Each chosen component contributes to the development of a robust and efficient application, enhancing the overall developer experience and user satisfaction.


