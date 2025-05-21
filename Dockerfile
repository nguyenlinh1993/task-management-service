# Stage 1: Build the application using Gradle
FROM gradle:8.13-jdk21-alpine as build

WORKDIR /app

# Copy source and dependencies
COPY --chown=gradle:gradle . .

# Build the application (use the correct task if it's not 'bootJar')
RUN gradle clean bootJar

# Stage 2: Run the application using a minimal image
FROM amazoncorretto:21-alpine3.18

WORKDIR /app

# Copy the built JAR from the previous stage
COPY --from=build /app/build/libs/*.jar app.jar

# Expose the port the app runs on (change if different)
EXPOSE $PORT

# Run the app
ENTRYPOINT [ "sh", "-c", "java \
-Dspring.profiles.active=$SPRING_PROFILE \
-jar app.jar \
" ]
