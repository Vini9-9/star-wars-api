# Star wars api
Trata-se de um microsserviço para expor uma API sobre o mundo Star Wars (Personagens e Filmes) 
- Inspiração: https://swapi.dev/


### Tecnologias utilizadas:
* Spring boot;
* Spring data JPA;
* Banco de dados: Mysql e H2;
* Autenticação via JWT;
* Testes com JUnit 5;
* Mocks com Mockito;

## Tasks

 - [X] CRUD Films
 - [X] CRUD People
 - [X] Relacionamento People e Films 
 - [X] Autenticação via JWT
 - [X] Documentação com Swagger 
 - [X] CRUD Colors
 - [X] Controle de usuários
 - [X] Controle de acesso por perfis
 - [X] Segregação de ambientes
 - [X] Testes de integração
 - [X] Validações de atributos obrigatórios
 - [X] Testes de unidade - Colors
 - [X] Testes de unidade - Films
 - [ ] Testes de unidade - People

### MER

 - [Films_People](https://raw.githubusercontent.com/Vini9-9/star-wars-api/master/MER/Films_People.png)

### Documentação

* http://localhost:8080/swagger-ui.html (profile = dev)

Funcionalidades sem autenticação:

* Get People, Films, Colors;
