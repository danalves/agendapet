FROM eclipse-temurin:17-jdk-alpine
WORKDIR /app
COPY target/agendapet-1.0.0.jar agendapet-1.0.0.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "agendapet-1.0.0.jar"]