FROM eclipse-temurin:21-jdk AS build

WORKDIR /app
COPY mvnw .
COPY .mvn .mvn
COPY pom.xml .
COPY src ./src

RUN chmod +x mvnw
RUN ./mvnw package -DskipTests

##

FROM eclipse-temurin:21-jdk
WORKDIR /app
COPY --from=build /app/target/client_service-0.0.1-SNAPSHOT.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]
