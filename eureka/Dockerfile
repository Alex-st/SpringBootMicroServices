FROM java:8-jre

ADD ./target/eureka.jar /app/
CMD ["java", "-Xmx200m", "-jar", "/app/eureka.jar"]

EXPOSE 8761