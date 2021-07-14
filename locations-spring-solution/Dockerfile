FROM adoptopenjdk:16-jre-hotspot
RUN mkdir /opt/app
COPY target/locations-spring-solution-0.0.1-SNAPSHOT.jar /opt/app/locations-spring-solution.jar
CMD ["java", "-jar", "/opt/app/locations-spring-solution.jar"]