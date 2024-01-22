FROM eclipse-temurin:17-jre-jammy
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} app.jar
ENTRYPOINT [ "java", "-jar", "app.jar" ]

# docker run -p 8080:8080 --name proyectospring -d -t junhongiticbcn/proyectospring:1.0  

