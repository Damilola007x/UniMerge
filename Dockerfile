# Build stage (Keep this as is)
FROM maven:3.8.5-openjdk-17 AS build
COPY . .
RUN mvn clean package -DskipTests

# Run stage (CHANGE THIS LINE)
FROM eclipse-temurin:17-jdk-jammy
COPY --from=build /target/UniMerge-1.0-SNAPSHOT.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","app.jar"]