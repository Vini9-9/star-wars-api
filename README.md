# Star wars api
Trata-se de um microsserviço para expor uma API sobre o mundo Star Wars (Personagens e Filmes) 
- Inspiração: https://swapi.dev/


### Tecnologias utilizadas:
* Spring boot
* Spring data JPA
* Banco de dados relacional (Mysql)

## Tasks

 - [X] CRUD Films
 - [X] CRUD People
 - [ ] Relacionamento People e Films 

### MER

 - [People](https://raw.githubusercontent.com/Vini9-9/star-wars-api/master/MER/Films_People.png)

# Cadastro de Personagens

**Requisição:** localhost:8080/api/people

**Atributos:**

| atributo | tipo | exemplo |
|--|--|--|
|name   | String | Luke Skywalker |
|height    | Integer | 172|
|mass | Integer | 77|
|birth_year| String |19BBY |
|gender | String | male|
|eye_color | Array of String| ["blue"]|
|hair_color  | Array of String|["blond"] |
|skin_color | Array of String|["fair"] |


# Cadastro de Filmes

**Requisição:** localhost:8080/api/films

**Atributos:**
| atributo | tipo | exemplo |
|--|--|--|
|episode_id   | Integer | 4|
|title   | String | A New Hope |
|opening_crawl| String | It is a period of civil war... |
|director   | String | George Lucas |
|producer| String | Gary Kurtz, Rick McCallum |
|release_date| String |1977-05-25 |



