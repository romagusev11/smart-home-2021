FROM adoptopenjdk/openjdk11
ADD target/smart-home-1.0-SNAPSHOT-jar-with-dependencies.jar smart-home.jar
ADD smart-home-1.js .
ENTRYPOINT ["java"]
CMD ["-jar", "/smart-home.jar"]