@echo off
call mvn clean install
if errorlevel 1 goto error

docker build --pull -t phelger/smpqwa .
if errorlevel 1 goto error

docker login -u phelger
if errorlevel 1 goto error

docker push phelger/smpqwa
if errorlevel 1 goto error

goto end
:error
echo Something went wrong...
pause
:end
