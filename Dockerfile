# ------------------------------
# Build stage: dùng Maven để build project
FROM maven:3.9.6-eclipse-temurin-17 AS build

WORKDIR /app

COPY . .

# ✅ Build & repackage để có fat jar Spring Boot
RUN mvn clean package spring-boot:repackage -DskipTests

# ------------------------------
# Runtime stage: chạy JAR trên Java nhẹ
FROM eclipse-temurin:17-jre

WORKDIR /app

COPY --from=build /app/target/FoodOrder-0.0.1-SNAPSHOT.jar app.jar

ENTRYPOINT ["java", "-jar", "app.jar"]