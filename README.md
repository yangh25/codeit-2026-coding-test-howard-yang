# CodeIt 2026 Coding Test

A Spring Boot 3 application using Java 17 and Gradle.

## Prerequisites

- Java 17 SDK installed
- MongoDB running locally on `localhost:27017`
- Gradle is not required globally because the project includes the Gradle wrapper

## MongoDB Setup

### Using Docker

To start a local MongoDB instance using Docker:

```powershell
docker run -d --name codeit-mongo -p 27017:27017 mongo:latest
```

This will start MongoDB in the background on port 27017. To stop it:

```powershell
docker stop codeit-mongo
docker rm codeit-mongo
```

## Local MongoDB configuration

The application uses the following MongoDB settings in `src/main/resources/application.properties`:

```properties
spring.data.mongodb.host=localhost
spring.data.mongodb.port=27017
spring.data.mongodb.database=codeitdb
```

Make sure MongoDB is running before starting the application.

## Build and run

From the project root, use the Gradle wrapper on Windows:

```powershell
.\gradlew.bat clean build
.\gradlew.bat bootRun
```

If you prefer to run the app directly after build:

```powershell
.\gradlew.bat bootJar
java -jar build\libs\codeit-2026-coding-test-howard-yang-0.0.1-SNAPSHOT.jar
```

## Application entry point

The Spring Boot application starts from:

- `src/main/java/com/codeit/test/CodeitTestApplication.java`

## Notes

- The app listens on port `8080` by default.
- Update `application.properties` if you need a different MongoDB host, port, or database name.
