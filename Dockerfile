# Stage 1: Build the application
FROM maven AS build

# Set the working directory
WORKDIR /app

# Copy the pom.xml and source code
COPY pom.xml .
COPY src ./src

# Build the application
RUN mvn clean install

# Stage 2: Create a lightweight image for running the application
FROM openjdk:21-jdk

# Set the working directory
WORKDIR /app

# Copy the JAR file from the build stage
COPY --from=build /app/target/shared_database_api-0.0.1-SNAPSHOT.jar shared_database_api-0.0.1-SNAPSHOT.jar

# Set the entry point
ENTRYPOINT ["java", "-jar", "shared_database_api-0.0.1-SNAPSHOT.jar"]
