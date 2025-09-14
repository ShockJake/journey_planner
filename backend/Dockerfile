FROM eclipse-temurin:24

RUN mkdir /app
COPY ./app/build/libs/app.jar /app/app.jar

EXPOSE 8080

ENTRYPOINT [ "java", "--add-modules", "jdk.incubator.vector", "-jar", "/app/app.jar" ]
