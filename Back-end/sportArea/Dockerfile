#
# Build stage
#
FROM maven:3.8.3-openjdk-17 AS build
WORKDIR /app
COPY . .
RUN mvn clean package -DskipTests

#
# Package stage
#
FROM openjdk:17-alpine
WORKDIR /app
COPY --from=build /target/*.jar sportArea.jar
EXPOSE 5000
ENTRYPOINT ["java","-jar","sportArea.jar"]
