FROM openjdk:11
COPY . /opt/planner
CMD java -Dspring.profiles.active=docker -Dapp.home=/opt/planner-home -jar /opt/planner/planner.jar
