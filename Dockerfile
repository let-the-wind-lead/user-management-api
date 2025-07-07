# 1) Build stage: produce a jar
FROM maven:3.9.4-eclipse-temurin-17 AS build
WORKDIR /app

# Copy only the pom (for dependency resolution) then the rest
COPY pom.xml .
COPY src ./src

# Build your jar (skipping tests for speed)
RUN mvn clean package -DskipTests

# 2) Run stage: copy the jar produced above
FROM eclipse-temurin:17
WORKDIR /app

# Copy the exact jar output from the build stage into 'app.jar'
COPY --from=build /app/target/user-management-api-0.0.1-SNAPSHOT.jar app.jar

# Launch
ENTRYPOINT ["java","-jar","app.jar"]
