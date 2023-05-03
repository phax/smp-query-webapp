# smp-query-webapp

Small web application that allows an arbitrary SMP query and returns JSON results
This is the API subset parts from https://peppol.helger.com for standalone usage.

## Building from source

```
mvn clean install
```

Output: `target/*.war`

Afterwars build Docker image:

```
docker build --pull -t phelger/smpqwa .
```

## Running

Running the Docker image:

```
docker run -d --name smpqwa -p 8080:8080 phelger/smpqwa
```

## Internal notes to myself

Deployment:

```
docker login -u phelger
docker push phelger/smpqwa
```
