#!/bin/bash
kill -9 $(lsof -i tcp:8080 -t)
git pull
mvn clean package
nohup java -jar target/testSpringBoot-0.0.1-SNAPSHOT.jar -Xms512m -Xmx512m &
tailf nohup.out