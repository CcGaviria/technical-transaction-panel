FROM openjdk:17-jdk-slim AS builder
WORKDIR /app
COPY . .
RUN chmod +x mvnw
RUN ./mvnw clean package -DskipTests


FROM openjdk:17-jdk-slim
WORKDIR /app
COPY --from=builder /app/target/transaction-service-0.0.1-SNAPSHOT.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]