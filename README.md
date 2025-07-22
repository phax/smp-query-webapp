# smp-query-webapp

Small web application that allows an arbitrary SMP query and returns JSON results
This is the API subset parts from https://peppol.helger.com for standalone usage.

This project is part of my Peppol solution stack. See https://github.com/phax/peppol for other components and libraries in that area.

## News and Noteworthy

2025-07-21
* Extended the truststore to contain Peppol PKI G3 CAs as well

## Supported query APIs

* SMP query all document types of a participant
* SMP query all endpoints of a participant for a certain document type
* SMP query the Business Card of a participant

See https://peppol.helger.com/public/locale-en_US/menuitem-tools-rest-api for the full API description.

## Building from source

```
mvn clean install
```

Output: `target/*.war`

Afterwards build Docker image:

```
docker build --pull -t phelger/smpqwa .
```

## Running

Running the Docker image (deployed to Docker Hub):

```
docker run -d --name smpqwa -p 8080:8080 phelger/smpqwa
```

## Internal notes to myself

Deployment:

```
docker login -u phelger
docker push phelger/smpqwa
```

---

My personal [Coding Styleguide](https://github.com/phax/meta/blob/master/CodingStyleguide.md) |
It is appreciated if you star the GitHub project if you like it.