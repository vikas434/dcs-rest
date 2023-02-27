# Charge Detail Records (CDR) Management REST API

This is a REST API for managing Charge Detail Records (CDR) in real time for a network of Charge Point Operators (CPO). The project is built using Java 1.8+ and the Spring Framework.

## Features

- Create a Charge Detail Record with the following fields:
    - Session identification
    - Vehicle identification
    - Start time
    - End time
    - Total cost
- The "End time" cannot be smaller than "Start time"
- The "Start time" of an upcoming Charge Detail Record for a particular vehicle must always be bigger than the "End time" of any previous Charge Detail Records.
- The "Total cost" must be greater than 0
- Get a Charge Detail Record by id
- Search all Charge Detail Records for a particular vehicle
    - "Start time" and "End time" fields must be sortable

## Requirements

- Java 1.8+
- Spring Framework
- Maven or Gradle as a build tool

## Building and Running the Application

1. Clone the repository using the command `git clone https://github.com/<<placeholder>>/cdr-management-api.git`.
2. Navigate into the project directory using the command `cd cdr-management-api`.
3. Run the command `mvn clean install` to build the project.
4. Run the command `java -jar target/cdr-management-api.jar` to start the application.

## Testing and Coverage

Best way to test is it via swagger - http://localhost:8080/swagger-ui/index.html

The project includes unit tests to ensure the correctness of the code. You can run the tests using the command `mvn test`.

Code coverage can be checked using the command `mvn jacoco:report`. The coverage report can be found in the `target/site/jacoco/index.html` file.

## Design Patterns

The project uses the following design patterns to solve known issues:

- Dependency Injection: The Spring Framework is used for dependency injection, which allows for easier testing and decoupling of components.

## Known Issues

1. Check for total cost of vehicle as 0.0.
2. Manually generating code coverage.

## Contributing

Pull requests are welcome. For major changes, please open an issue first to discuss what you would like to change.

Please make sure to update tests as appropriate.

## License

[MIT](https://choosealicense.com/licenses/mit/)
