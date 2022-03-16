FROM openjdk:17-alpine3.13 as build
WORKDIR /usr/src/app/backend
COPY mvnw .
COPY .mvn .mvn
COPY pom.xml .
COPY src src
RUN ./mvnw clean package -DskipTests

FROM openjdk:17-alpine3.13
WORKDIR /usr/src/app/backend
ARG JAR_FILE=/usr/src/app/backend/target/*.jar
COPY  --from=build ${JAR_FILE} application.jar
ENTRYPOINT ["java", "-jar", "application.jar"]