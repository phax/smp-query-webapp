#!/bin/sh -e
mvn clean install
docker build --pull -t phelger/smpqwa .
docker login -u phelger
docker push phelger/smpqwa
