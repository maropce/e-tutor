# Wybierz obraz bazowy z OpenJDK
FROM openjdk:17-jdk-alpine

# Ustaw katalog roboczy wewnątrz kontenera
WORKDIR /app

# Skopiuj plik JAR zbudowany przez aplikację Spring Boot
COPY target/e-tutor-0.0.1-SNAPSHOT.jar /app/e-tutor-0.0.1-SNAPSHOT.jar

# Ustaw zmienną środowiskową dla Spring Boot (opcjonalne)
#ENV SPRING_PROFILES_ACTIVE=prod

# Uruchom aplikację Spring Boot
ENTRYPOINT ["java", "-jar", "/app/e-tutor-0.0.1-SNAPSHOT.jar"]
