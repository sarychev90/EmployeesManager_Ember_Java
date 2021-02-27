FROM adoptopenjdk:hotspot
ARG JAR_FILE=*.jar
COPY target/${JAR_FILE} application.jar
EXPOSE 8080
CMD ["java", "-jar", "application.jar"]