FROM gradle:8.10-jdk21 AS build
WORKDIR /app
COPY build.gradle settings.gradle ./
COPY gradle ./gradle
RUN gradle dependencies --no-daemon
COPY . .
RUN gradle build -x test --no-daemon

FROM eclipse-temurin:21-jre AS runtime
WORKDIR /app
RUN addgroup --system spring && adduser --system spring --ingroup spring
USER spring
COPY --from=build /app/build/libs/*.jar app.jar
ENTRYPOINT ["java", "-jar", "app.jar"]