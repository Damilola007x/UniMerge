FROM maven:3.8.5-openjdk-17 AS build
COPY . .
RUN mvn clean package -DskipTests

FROM openjdk:17-jdk-slim
COPY --from=build /target/UniMerge-0.0.1-SNAPSHOT.jar app.jar
# Expose the Port for the Web API and JADE
EXPOSE 8080 1099
ENTRYPOINT ["java", "-jar", "app.jar"]