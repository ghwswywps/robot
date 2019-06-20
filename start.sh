#!/bin/bash
kill -9 $(lsof -i tcp:10924 -t)
git pull
mvn clean package
nohup java -jar target/dingcb.jar -Xms512m -Xmx512m &
tail -f nohup.out