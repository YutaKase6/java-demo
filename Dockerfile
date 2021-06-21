FROM adoptopenjdk/openjdk11:alpine-slim as builder

WORKDIR /app
COPY . .
RUN ./gradlew clean build

FROM adoptopenjdk/openjdk11:alpine-slim

WORKDIR /app
COPY --from=builder /app/build/libs/*.jar app.jar

ENTRYPOINT ["java", "-jar", "app.jar"]
