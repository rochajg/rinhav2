FROM amazoncorretto:17.0.10 as RUNTIME

COPY build/libs/rinhav2-0.1-all.jar /rinha.jar

EXPOSE 8080

ENTRYPOINT [ "java","-jar", "./rinha.jar" ]
