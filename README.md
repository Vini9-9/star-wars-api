# Star wars api
It is a microservice to expose an API about the Star Wars world (Characters and Films) 
- Inspired by: https://swapi.dev/
- To run via docker access the branch [docker](https://github.com/Vini9-9/star-wars-api/tree/docker) 

### Technologies used:
* Spring boot;
* Spring data JPA;
* Database: Mysql e H2;
* Authentication via JWT;
* Tests using JUnit 5;
* Mocks using Mockito;

## Tasks

 - [X] CRUD Films
 - [X] CRUD People
 - [X] Relationship People and Films 
 - [X] Authentication via JWT
 - [X] Documentation with Swagger 
 - [X] CRUD Colors
 - [X] Users control
 - [X] Access control by profile
 - [X] Environmental segregation
 - [X] Integration tests
 - [X] Validation of required attributes
 - [X] Unit tests - Colors
 - [X] Unit tests - Films
 - [ ] Unit tests - People

### ERM - Entity-relationship model

 - [Films_People](https://raw.githubusercontent.com/Vini9-9/star-wars-api/master/MER/Films_People.png)

### Documentation

* http://localhost:8080/swagger-ui.html (profile = dev)

features without authentication:

* Get: People, Films, Colors;
