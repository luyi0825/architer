FROM openjdk:openjdk11
ADD target/${project.name}-${project.version}.jar /app.jar
WORKDIR /
CMD ["java", "-jar", "app.jar"]
