# Build stage
FROM gradle:latest AS BUILD
WORKDIR /usr/app
COPY . .
RUN gradle build -x test

# Package stage
FROM openjdk:21-jdk
ENV JAR_NAME=app.jar
ENV APP_HOME=/usr/app/
WORKDIR $APP_HOME
COPY --from=BUILD /usr/app/build/libs/*.jar $JAR_NAME
COPY src/main/resources/application.properties $APP_HOME
RUN sed -i 's/spring.profiles.active=dev/spring.profiles.active=prod/' $APP_HOME/application.properties
EXPOSE 8080
ENTRYPOINT java -jar $APP_HOME$JAR_NAME