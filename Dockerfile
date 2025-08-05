FROM amazoncorretto:24

# Configured in build.gradle
COPY build/libs/hellozai-weather.jar .
EXPOSE 8080

CMD ["java", "-XX:+PrintFlagsFinal", "-XX:+UseContainerSupport", "-jar", "hellozai-weather.jar"]