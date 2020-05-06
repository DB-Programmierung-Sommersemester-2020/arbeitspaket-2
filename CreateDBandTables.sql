-- Data seeding --

DROP TABLE IF EXISTS Adresse;
DROP TABLE IF EXISTS Verlag;
DROP TABLE IF EXISTS Buch;
DROP TABLE IF EXISTS BuchAutoren;
DROP TABLE IF EXISTS Autor;

CREATE TABLE Adresse     (id INT  PRIMARY KEY AUTO_INCREMENT, ort VARCHAR(50) );
CREATE TABLE Verlag      (id INT  PRIMARY KEY AUTO_INCREMENT, name VARCHAR(50), adrId INT );
CREATE TABLE Buch        (id INT  PRIMARY KEY AUTO_INCREMENT, titel VARCHAR(50), bestand INT, verlagId INT );
CREATE TABLE BuchAutoren (id INT  PRIMARY KEY AUTO_INCREMENT, buchId INT, autorId INT);
CREATE TABLE Autor       (id INT  PRIMARY KEY AUTO_INCREMENT, vorname VARCHAR(20), nachname VARCHAR(20) );



INSERT INTO Adresse VALUES (1, 'Braunschweig/Wiesbaden');
INSERT INTO Adresse VALUES (2, 'New Jersey');
INSERT INTO Adresse VALUES (3, 'Farnham');
INSERT INTO Adresse VALUES (4, 'Reading, Massachusetts');
INSERT INTO Adresse VALUES (5, 'Landsberg');
INSERT INTO Adresse VALUES (6, 'New York');
INSERT INTO Adresse VALUES (7, 'San Francisco');
INSERT INTO Adresse VALUES (8, 'Berlin');
INSERT INTO Adresse VALUES (9, 'München');



INSERT INTO Verlag VALUES (1, 'Vieweg', 1);
INSERT INTO Verlag VALUES (2, 'Prentice Hall', 2);
INSERT INTO Verlag VALUES (3, 'OReilly', 3);
INSERT INTO Verlag VALUES (4, 'Addison-Wesley', 4);
INSERT INTO Verlag VALUES (5, 'mitp', 5);
INSERT INTO Verlag VALUES (6, 'Wiley', 6);
INSERT INTO Verlag VALUES (7, 'Morgan Kaufmann', 7);



INSERT INTO Buch VALUES (1, 'Distributed Systems', 5, 2);
INSERT INTO Buch VALUES (2, 'Verteilte Systeme', 5, 1);
INSERT INTO Buch VALUES (3, 'The Essential Client/Server Survival Guide', 5, 6);
INSERT INTO Buch VALUES (4, 'Datenbanken: Konzepte und Sprachen', 5, 5);
INSERT INTO Buch VALUES (5, 'Datenbanken: Implementierungstechniken', 5, 5);
INSERT INTO Buch VALUES (6, 'Enterprise JavaBeans', 5, 3);
INSERT INTO Buch VALUES (7, 'Java Message Service', 5, 3);
INSERT INTO Buch VALUES (8, 'Java Servlet Programming', 5, 3);
INSERT INTO Buch VALUES (9, 'Concurrent Programming in Java', 5, 4);
INSERT INTO Buch VALUES (10, 'Principles of Transaction Processing', 5, 7);
INSERT INTO Buch VALUES (11, 'Modern Operating Systems', 5, 2);
INSERT INTO Buch VALUES (12, 'Objektorientierte Analyse und Design', 5, 4);
INSERT INTO Buch VALUES (13, 'The Essential Distributed Objects Survival Guide', 5, 6);
INSERT INTO Buch VALUES (14, 'Refactoring', 5, 4);
INSERT INTO Buch VALUES (15, 'UML Distilled', 5, 4);
INSERT INTO Buch VALUES (16, 'Analysis Patterns', 5, 4);
INSERT INTO Buch VALUES (17, 'Design Patterns', 5, 4);
INSERT INTO Buch VALUES (18, 'Component Software', 5, 4);
INSERT INTO Buch VALUES (19, 'Mastering Enterprise JavaBeans', 5, 6);
INSERT INTO Buch VALUES (20, 'EJB Design Patterns', 5, 6);


INSERT INTO Autor VALUES (1, 'Andrew S.', 'Tanenbaum');
INSERT INTO Autor VALUES (2, 'Marten', 'van Steen');
INSERT INTO Autor VALUES (3, 'Günther', 'Bengel');
INSERT INTO Autor VALUES (4, 'Richard', 'Monson-Haefel');
INSERT INTO Autor VALUES (5, 'Jason', 'Hunter');
INSERT INTO Autor VALUES (6, 'William', 'Crawford');
INSERT INTO Autor VALUES (7, 'Doug', 'Lea');
INSERT INTO Autor VALUES (8, 'Phillip A.', 'Bernstein');
INSERT INTO Autor VALUES (9, 'Eric', 'Newcomer');
INSERT INTO Autor VALUES (10, 'Martin', 'Fowler');
INSERT INTO Autor VALUES (11, 'Clemens', 'Szyperski');
INSERT INTO Autor VALUES (12, 'Erich', 'Gamma');
INSERT INTO Autor VALUES (13, 'Richard', 'Helm');
INSERT INTO Autor VALUES (14, 'Ralph', 'Johnson');
INSERT INTO Autor VALUES (15, 'John', 'Vlissides');
INSERT INTO Autor VALUES (16, 'Andreas', 'Heuer');
INSERT INTO Autor VALUES (17, 'Gunter', 'Saake');
INSERT INTO Autor VALUES (18, 'Robert', 'Orfali');
INSERT INTO Autor VALUES (19, 'Dan', 'Harkey');
INSERT INTO Autor VALUES (20, 'Jeri', 'Edwards');
INSERT INTO Autor VALUES (21, 'Grady', 'Booch');
INSERT INTO Autor VALUES (22, 'David A.', 'Chappell');
INSERT INTO Autor VALUES (23, 'Ed', 'Roman');
INSERT INTO Autor VALUES (24, 'Scott', 'Ambler');
INSERT INTO Autor VALUES (25, 'Tyler', 'Jewell');
INSERT INTO Autor VALUES (26, 'Floyed', 'Marinescu');


INSERT INTO BuchAutoren (buchId, autorId) VALUES (1, 1 );
INSERT INTO BuchAutoren (buchId, autorId) VALUES (1, 2 );
		
INSERT INTO BuchAutoren (buchId, autorId) VALUES (2, 3 );
		
INSERT INTO BuchAutoren (buchId, autorId) VALUES (3, 18 );
INSERT INTO BuchAutoren (buchId, autorId) VALUES (3, 19 );
INSERT INTO BuchAutoren (buchId, autorId) VALUES (3, 20 );
		
INSERT INTO BuchAutoren (buchId, autorId) VALUES (4, 16 );
INSERT INTO BuchAutoren (buchId, autorId) VALUES (4, 17 );
		
INSERT INTO BuchAutoren (buchId, autorId) VALUES (5, 16 );
INSERT INTO BuchAutoren (buchId, autorId) VALUES (5, 17 );
		
INSERT INTO BuchAutoren (buchId, autorId) VALUES (6, 4 );
		
INSERT INTO BuchAutoren (buchId, autorId) VALUES (7, 4 );
INSERT INTO BuchAutoren (buchId, autorId) VALUES (7, 22 );
		
INSERT INTO BuchAutoren (buchId, autorId) VALUES (8, 5 );
INSERT INTO BuchAutoren (buchId, autorId) VALUES (8, 6 );
		
INSERT INTO BuchAutoren (buchId, autorId) VALUES (9, 7 );
		
INSERT INTO BuchAutoren (buchId, autorId) VALUES (10, 8 );
INSERT INTO BuchAutoren (buchId, autorId) VALUES (10, 9 );
		
INSERT INTO BuchAutoren (buchId, autorId) VALUES (11, 1 );
		
INSERT INTO BuchAutoren (buchId, autorId) VALUES (12, 21 );		

INSERT INTO BuchAutoren (buchId, autorId) VALUES (13, 18 );
INSERT INTO BuchAutoren (buchId, autorId) VALUES (13, 19 );
INSERT INTO BuchAutoren (buchId, autorId) VALUES (13, 20 );

INSERT INTO BuchAutoren (buchId, autorId) VALUES (14, 10 );		
INSERT INTO BuchAutoren (buchId, autorId) VALUES (15, 10 );		
INSERT INTO BuchAutoren (buchId, autorId) VALUES (16, 10 );		

INSERT INTO BuchAutoren (buchId, autorId) VALUES (17, 12 );
INSERT INTO BuchAutoren (buchId, autorId) VALUES (17, 13 );
INSERT INTO BuchAutoren (buchId, autorId) VALUES (17, 14 );
INSERT INTO BuchAutoren (buchId, autorId) VALUES (17, 15 );
		
INSERT INTO BuchAutoren (buchId, autorId) VALUES (18, 11 );
		
INSERT INTO BuchAutoren (buchId, autorId) VALUES (19, 23 );		
INSERT INTO BuchAutoren (buchId, autorId) VALUES (19, 24 );		
INSERT INTO BuchAutoren (buchId, autorId) VALUES (19, 25 );
		
INSERT INTO BuchAutoren (buchId, autorId) VALUES (20, 26 );	

