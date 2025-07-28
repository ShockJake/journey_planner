FROM eclipse-temurin:21

RUN mkdir /app
COPY ./app/build/libs/app.jar /app/app.jar

EXPOSE 8080

ENTRYPOINT [ "java", "-jar", "/app/app.jar" ]
