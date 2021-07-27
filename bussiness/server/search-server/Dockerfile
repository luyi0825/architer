FROM openjdk:11.0.12
ADD target/${project.name}-${project.version}.jar /app.jar
WORKDIR /
CMD ["java", "-jar", "app.jar"]
