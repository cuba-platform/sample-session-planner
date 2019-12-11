FROM openjdk:11

COPY . /opt/planner

CMD java -Dspring.active.profile=dev -Dapp.home=/opt/planner-home -jar /opt/planner/planner.jar
