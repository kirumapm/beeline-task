FROM maven:3.9.6-eclipse-temurin-17-alpine AS build

WORKDIR /beeline-task
COPY ../ ./

RUN --mount=type=cache,target=/root/.m2 \
        mvn -B clean package -DskipTests=true


FROM eclipse-temurin:17-jre-alpine
WORKDIR /app

COPY --from=build beeline-task/order-consumer/target/*.jar app.jar

EXPOSE 8081
ENTRYPOINT ["java", "-jar", "app.jar"]