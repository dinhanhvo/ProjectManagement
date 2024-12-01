#Build
FROM maven:3.9.4-eclipse-temurin-21 AS build
LABEL authors="Vo Dinh"

RUN mkdir -p /backend
WORKDIR /backend

COPY . /backend
RUN mvn package  -DskipTests

#ARG JAR_FILE=target/*.jar
#COPY ${JAR_FILE} app.jar

# Stage 2: Runtime
FROM eclipse-temurin:21-jre-alpine

# Set the working directory
WORKDIR /app

# Copy the built JAR from the build stage
COPY --from=build /backend/target/*.jar app.jar

#COPY --from=build /backend/entrypoint.sh /entrypoint.sh
#RUN chmod +x /entrypoint.sh
#ENTRYPOINT ["/entrypoint.sh"]

# Expose the application port
EXPOSE 8088

# Run the application
ENTRYPOINT ["java", "--enable-preview", "-jar", "app.jar"]

