FROM --platform=linux/amd64 openjdk:17-alpine
ARG JAR_FILE=build/libs/*.jar
COPY ${JAR_FILE} app.jar
ENV USE_PROFILE real
ENV JAVA_OPTS ""
CMD java $JAVA_OPTS -Dspring.profiles.active=$USE_PROFILE -server -jar app.jar
EXPOSE 8080