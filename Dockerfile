FROM gradle:7.0.2-jdk11 AS cache
ENV GRADLE_USER_HOME /cache
WORKDIR /app
COPY build.gradle settings.gradle ./
RUN gradle dependencies --no-daemon --stacktrace --info

FROM gradle:7.0.2-jdk11 AS build
WORKDIR /app
COPY --from=cache /cache /home/gradle/.gradle
COPY --from=cache /app /app
COPY src/ src/
RUN gradle bootJar --no-daemon --stacktrace --info

FROM openjdk:11.0.11-jre-buster
WORKDIR /app
RUN mkdir -p /var/www/blog/post
COPY src/main/resources/static/images/ /var/www/blog/images/
COPY src/main/resources/static/styles/ /var/www/blog/styles/
COPY --from=build /app/build/libs/blog4j.jar /blog4j.jar
CMD ["java", "-jar", "-Dspring.profiles.active=default", "/blog4j.jar"]
