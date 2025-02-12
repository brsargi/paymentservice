FROM openjdk:17-jdk-slim AS builder

WORKDIR /app

COPY . .

RUN ./gradlew build

RUN ./gradlew bootJar

FROM alpine:latest

RUN apk add --no-cache openjdk17-jre

WORKDIR /app

COPY --from=builder /app/build/libs/*.jar app.jar

EXPOSE 8080

CMD ["java","-Dspring.profiles.active=docker", "-jar", "app.jar"]