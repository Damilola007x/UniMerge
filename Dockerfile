# Step 1: Build the application
FROM maven:3.8.5-openjdk-17 AS build
COPY . .
RUN mvn clean package -DskipTests

# Step 2: Run the application
FROM eclipse-temurin:17-jdk-jammy
# UPDATED LINE BELOW TO MATCH VERSION 1.0-SNAPSHOT
COPY --from=build /target/UniMerge-1.0-SNAPSHOT.jar app.jar

# Expose the Port for the Web API and JADE
EXPOSE 8080 1099

ENTRYPOINT ["java", "-jar", "app.jar"]