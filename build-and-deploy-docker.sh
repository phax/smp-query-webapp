#!/bin/sh -e
mvn clean install

echo Docker login
docker login -u phelger

echo Starting buildx
##docker buildx create --name smpqwa --append

docker buildx build --platform=linux/amd64 --push --pull -t phelger/smpqwa .
docker buildx build --platform=linux/arm64 --push --pull -t phelger/smpqwa-arm64 .
