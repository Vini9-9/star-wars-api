-- SEGURANÇA
-- as senhas dos usuários são iguais aos respectivos 'names'
REPLACE INTO profile (id, name) VALUES 
(1 , 'ROLE_ADMIN'),
(2 , 'ROLE_MODERADOR'),
(3 , 'ROLE_USUARIO');

REPLACE INTO user (id, email, password, name) VALUES 
(1 , 'admin@email.com', '$2a$10$b3SAsvh31R4qEzqVGTQVTe0NO9KRrUizlk6hDEV97TSeniCm6Gdkq', 'admin'),
(2 , 'moderador@email.com', '$2a$10$q8WNPPn.6drMLuhWJZ07LeJ11eM2Zmtt6pvR5BtPGybNnw7QR7jZy', 'moderador'),
(3 , 'usuario@email.com', '$2a$10$hSqqtl7B5wOLwKuyasELJ.ZAv3tZ/rxsoskjK3jhehCO6H9CF1yfa', 'usuario');
REPLACE INTO user_profiles (user_id, profiles_id) VALUES 
(1 , 1),
(2 , 2),
(3 , 3);

-- DADOS PRINCIPAIS
REPLACE INTO colors (name) VALUES 
('branco'),('preto'),('amarelo'),('vermelho'),('verde'),('azul'),('loiro'),('bege claro'),('n/a'),
('dourado'),('roxo');

REPLACE INTO gender (name) VALUES 
('masculino'), ('feminino'), ('n/a');

REPLACE INTO people (id, name, height, mass, birth_year, gender_id, created) VALUES 
(1, 'Luke Skywalker', '172', '77' ,'19BBY', '1', CURRENT_TIMESTAMP),
(2, 'R2-D2', '96', '32', '41.9BBY', '3', CURRENT_TIMESTAMP),
(4, 'Darth Vader', '202','136','41.9BBY', '1', CURRENT_TIMESTAMP),
(5, 'Leia Organa', '150','49', '19BBY', '2', CURRENT_TIMESTAMP),
(6, 'C-3PO', '167', '75', '112BBY', '3', CURRENT_TIMESTAMP);

REPLACE INTO eye_color (colors_id, people_id) VALUES (3,6),(3,4),(6,1),(4,2);
REPLACE INTO hair_color (colors_id, people_id) VALUES (9,6),(9,4),(7,1),(9,2);
REPLACE INTO skin_color (colors_id, people_id) VALUES (10,6),(2,6),(1,4),(8,1),(6,2);

REPLACE INTO films (episode_id, title, opening_crawl, director, producer, release_date, created) VALUES 
('4', 'Uma Nova Esperança', 
'É um período de guerra civil',
'George Lucas',
'Gary Kurtz, Rick McCallum',
'1977-05-25',
CURRENT_TIMESTAMP),
('5', 'O império Contra-ataca', 
'É um momento sombrio para a Rebelião',
'Irvin Kershner',
'Gary Kurtz, Rick McCallum',
'1980-05-17',
CURRENT_TIMESTAMP),
('6', 'O retorno de Jedi', 
'Luke Skywalker voltou...',
'Richard Marquand',
'Howard G. Kazanjian, George Lucas, Rick McCallum',
'1983-05-25',
CURRENT_TIMESTAMP),
('1', 'A Ameaça Fantasma', 
'A turbulência tomou conta da República Galáctica...',
'George Lucas',
'Rick McCallum',
'1999-05-19',
CURRENT_TIMESTAMP),
('2', 'Ataque dos Clones', 
'Há agitação no Senado Galáctico...',
'George Lucas',
'Rick McCallum',
'2002-05-16',
CURRENT_TIMESTAMP),
('3', 'A Vingança dos Sith', 
'Guerra! A República está desmoronando sob os ataques do implacável Lorde Sith...',
'George Lucas',
'Rick McCallum',
'2005-05-19',
CURRENT_TIMESTAMP);

-- RELAÇÃO PERSONAGENS E FILMES
REPLACE INTO films_people (people_id, films_id) VALUES 
(1,1),(1,2),(1,3),(1,6),
(2,1),(2,2),(2,3),(2,4),(2,5),(2,6),
(4,1),(4,2),(4,3),(4,6),
(5,1),(5,2),(5,3),(5,6),
(6,1),(6,2),(6,3),(6,4),(6,5),(6,6);