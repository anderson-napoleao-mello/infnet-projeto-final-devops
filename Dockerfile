FROM openjdk:11
MAINTAINER andersonnapoleao
COPY target/movie-catalog-0.0.1-SNAPSHOT.jar movie-catalog-0.0.1-SNAPSHOT.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","/movie-catalog-0.0.1-SNAPSHOT.jar"]
