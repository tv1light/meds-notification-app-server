# ── Стадия 1: сборка ──────────────────────────────────────
FROM maven:3.9.6-eclipse-temurin-17 AS builder

WORKDIR /app

# Сначала копируем только pom.xml — кэшируем зависимости отдельно
COPY pom.xml .
RUN mvn dependency:go-offline -q

# Теперь копируем исходники и собираем jar
COPY src ./src
RUN mvn package -DskipTests -q

# ── Стадия 2: запуск ──────────────────────────────────────
FROM eclipse-temurin:17-jre

WORKDIR /app

# Копируем только jar из стадии сборки
COPY --from=builder /app/target/*.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]