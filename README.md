# Star wars api
It is a microservice to expose an API about the Star Wars world (Characters and Films) 
- Inspired by: https://swapi.dev/

## Requirements:
* [Docker](https://docs.docker.com/get-docker/)
* [Docker compose](https://docs.docker.com/compose/install/)

### How to run:

* Clone this repository;
* Run `./mvnw clean package -f .\pom.xml` on terminal;
* Wait for the end of the build process;
* Run `docker-compose up --build` on terminal;
* See the documentation to do the requests that you want;  

### Documentation

* http://localhost:9090/swagger-ui.html (profile = dev)

Features without authentication:

* Get: People, Films, Colors;
