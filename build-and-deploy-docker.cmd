@echo off
call mvn clean install
if errorlevel 1 goto error

docker login -u phelger
if errorlevel 1 goto error

docker buildx create --name smpqwa
if errorlevel 1 goto error

docker buildx build --platform=linux/amd64 --push --pull -t phelger/smpqwa .
if errorlevel 1 goto error

docker buildx build --platform=linux/arm64 --push --pull -t phelger/smpqwa-arm64 .
if errorlevel 1 goto error

goto end
:error
echo Something went wrong...
pause
:end
