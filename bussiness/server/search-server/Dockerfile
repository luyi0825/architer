FROM openjdk:11.0.12
ADD target/${project.name}-${project.version}.jar /app.jar
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-Dspring.profiles.active=${SPRING_PROFILES_ACTIVE}","-jar","/app.jar"]
WORKDIR /
CMD ["java", "-jar", "app.jar"]
