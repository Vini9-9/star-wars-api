/* INSERT INTO colors (name) VALUES 
('branco'),('preto'),('amarelo'),('vermelho'),('verde'),('azul'),('loiro'),('bege claro'),('n/a'),
('dourado'),('roxo');

INSERT INTO gender (name) VALUES 
('masculino'), ('feminino'), ('n/a');

INSERT INTO people (name, height, mass, birth_year, gender_id, created) VALUES 
('Luke Skywalker', '172', '77' ,'19BBY', '1', CURRENT_TIMESTAMP),
('R2-D2', '96', '32', '41.9BBY', '3', CURRENT_TIMESTAMP),
('C-3PO', '167', '75', '112BBY', '3', CURRENT_TIMESTAMP),
('Darth Vader', '202','136','41.9BBY', '1', CURRENT_TIMESTAMP),
('Leia Organa', '150','49', '19BBY', '2', CURRENT_TIMESTAMP);

INSERT INTO films (episode_id, title, opening_crawl, director, producer, release_date, created) VALUES 
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
 */