FROM openjdk:18
ARG JAR_PATH
COPY $JAR_PATH app.jar
ENTRYPOINT ["java", "-jar" , "app.jar"]