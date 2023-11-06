FROM openjdk:17-jdk-alpine
ADD target/*.jar app.jar

ENV JAVA_OPTS="-Xdebug -Xrunjdwp:server=y,transport=dt_socket,address=8787,suspend=n"

EXPOSE 8085 8787

ENTRYPOINT [ "sh", "-c", "java $JAVA_OPTS -jar /app.jar" ]