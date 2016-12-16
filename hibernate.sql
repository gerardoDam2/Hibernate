/* Autores */
INSERT INTO autores(codAutor, nombreAutor) VALUES ('aaaa','paco');
INSERT INTO autores(codAutor, nombreAutor) VALUES ('bbbb','juan');
INSERT INTO autores(codAutor, nombreAutor) VALUES ('cccc','manolo');
INSERT INTO autores(codAutor, nombreAutor) VALUES ('dddd','pepe');
INSERT INTO autores(codAutor, nombreAutor) VALUES ('eeee','laura');
INSERT INTO autores(codAutor, nombreAutor) VALUES ('ffff','paula');

/* libros */
INSERT INTO libros
(ISBN, fechaintro, nombreLibro)
VALUES ('11-111-1111-a','1991-12-1','libro 1');

INSERT INTO libros
(ISBN, fechaintro, nombreLibro)
VALUES ('11-111-1111-a','1991-12-1','libro 2');

INSERT INTO libros
(ISBN, fechaintro, nombreLibro)
VALUES ('11-111-1111-a','1991-12-1','libro 3');

INSERT INTO libros
(ISBN, fechaintro, nombreLibro)
VALUES ('11-111-1111-a','1991-12-1','libro 4');

/* deposito legal */
INSERT INTO despositolegal(depositolegal, codLibroDeposito) VALUES ('deposito 1',1);
INSERT INTO despositolegal(depositolegal, codLibroDeposito) VALUES ('deposito 2',2);
INSERT INTO despositolegal(depositolegal, codLibroDeposito) VALUES ('deposito 3',3);


/* ejemplares */
INSERT INTO ejemplares( importe, moneda, codLibro)
VALUES (1,'euro',1);
INSERT INTO ejemplares( importe, moneda, codLibro)
VALUES (20,'euro',1);
INSERT INTO ejemplares( importe, moneda, codLibro)
VALUES (1,'dolar',2);
INSERT INTO ejemplares( importe, moneda, codLibro)
VALUES (20,'euro',3);
