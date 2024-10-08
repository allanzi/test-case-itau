# Itau Test Case

This project was developed to fulfill the Itaú Software Engineer challenge.

## Insights
- [See here output of tests](https://github.com/allanzi/test-case-itau/blob/main/test-case-additional/test-output.txt)
- [See here coverage of tests (Generated by Jacoco)](https://github.com/allanzi/test-case-itau/blob/main/test-case-additional/Pasted%20Graphic.png)
- [See here others screenshots](https://github.com/allanzi/test-case-itau/tree/main/test-case-additional/screenshots)

## Technologies Used

- **Java 17**: Programming language used for the project.
- **Spring Boot 3.2.3**: Framework used to quickly and efficiently create the application.
- **MongoDB**: NoSQL database used to store data.
- **Apache Maven 3.8.6**: Build automation tool used to manage project dependencies.
- **RabbitMQ**: Messaging system used for communication between services.
- **Redis**: Used for caching.
- **Logback/SLF4J**: Used for application logging.
- **Docker**: Used for containerization.

## How to Install

1. **Clone the Project**:
    ```sh
    https://github.com/allanzi/test-case-itau.git
    cd test-case
    ```

2. **Create the `.env` File**:
    - Use the `.env.example` file as a template to create a `.env` file in the project root.
    - Configure the environment variables as needed.

3. **Run Project**:
    - `docker compose up -d`

4. **Configure the Queue/Exchange**:
    - Ensure RabbitMQ is running.
    - Configure the queues and exchanges as specified in the `.env` file.

## Project URL

- The application can be accessed at: [http://localhost:8080/api](http://localhost:8080/api)

## Swagger URL

- The API documentation can be accessed at: [http://localhost:8080/api/swagger-ui/index.html](http://localhost:8080/api/swagger-ui/index.html)

## Tests

- Unit tests cover the entire application and can be run using Maven:
    ```sh
    mvn test
    ```
- Or using docker:
    ```sh
    docker exec -it insurance-quote-api mvn test
    ```