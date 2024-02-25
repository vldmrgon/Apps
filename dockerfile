FROM maven:3.6.3-jdk-13 AS build
WORKDIR /app
COPY src ./src
COPY pom.xml .
COPY src/main/resources/data/player.csv src/main/resources/data/player.csv
RUN mvn clean package
FROM openjdk:13-jdk-slim
WORKDIR /app
COPY --from=build /app/target/*.jar ./app.jar
EXPOSE 8080
CMD ["java", "-jar", "app.jar"]

# docker build -t apps .
# docker run -d -p 8080:8080 apps