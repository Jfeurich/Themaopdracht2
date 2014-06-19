DROP TABLE IF EXISTS GebruiktProduct;
DROP TABLE IF EXISTS Factuur;
DROP TABLE IF EXISTS Herinneringsbrief;
DROP TABLE IF EXISTS Klus;
DROP TABLE IF EXISTS Reservering;
DROP TABLE IF EXISTS Auto;
DROP TABLE IF EXISTS BesteldProduct;
DROP TABLE IF EXISTS Bestelling;
DROP TABLE IF EXISTS Product;
DROP TABLE IF EXISTS User;
DROP TABLE IF EXISTS Klant;
    
/* Klant */

CREATE TABLE Klant (
klantid			INTEGER 	NOT NULL PRIMARY KEY AUTO_INCREMENT,
naam 			VARCHAR(30) NOT NULL,
adres 			VARCHAR(50)	NOT NULL,
plaats			VARCHAR(30)	NOT NULL,
rekeningnummer	VARCHAR(20)	NOT NULL,
telefoonnummer	INTEGER		NOT NULL,
actief			CHAR(1)		NOT NULL
);

INSERT INTO Klant (naam, adres, plaats, telefoonnummer, rekeningnummer, actief) VALUES
('Sandrilene', 'Padualaan 1', 'Utrecht', 0301111111, 'rekeningSandri', 't'),
('Trisana', 'Herengracht 50', 'Amsterdam', 0202222222, 'rekeningTris', 't'),
('Daja', 'Hoofdstraat 20', 'Den Haag', 0109999999, 'rekeningDaja', 't')
;

/* User */

CREATE TABLE User (
userid 			INTEGER 	NOT NULL PRIMARY KEY AUTO_INCREMENT,
gebruikersnaam	VARCHAR(30)	NOT NULL,
wachtwoord		VARCHAR(30)	NOT NULL,
email			VARCHAR(50) NOT NULL,
type			INTEGER		NOT NULL,
naam			VARCHAR(50)	,
klantid			INTEGER		,
actief			CHAR(1)		NOT NULL,
FOREIGN KEY (klantid) REFERENCES Klant(klantid)
);

INSERT INTO User (gebruikersnaam, wachtwoord, type, klantid, email, actief) VALUES
('sandri', 'sww', 3, 1, 'sari@gmail.com', 't'),
('tris', 'tww', 3, 2, 'tris@gmail.com', 't'),
('daj', 'dww', 3, 3, 'daja@gmail.com', 't')
;
INSERT INTO User (gebruikersnaam, wachtwoord, type, email, naam, actief) VALUES
('henk', 'hww', 0, 'henk@atb.nl', 'Henk Paladijn', 't'),
('mike', 'mww', 1, 'mike@atb.nl', 'Mike Monteur', 't'),
('jopie', 'jww', 2, 'jopie@atb.nl', 'Jopie Garagebeheerder', 't')
;

/* Product */

CREATE TABLE Product (
productid		INTEGER 	NOT NULL PRIMARY KEY AUTO_INCREMENT,
naam 			VARCHAR(30)	NOT NULL,
minimumAanwezig INTEGER		NOT NULL,
eenheid 		VARCHAR(30)	NOT NULL,
prijsPerStuk	DOUBLE		NOT NULL,
aantal 			INTEGER		NOT NULL,
actief			CHAR(1)		NOT NULL
);

INSERT INTO Product (naam, minimumAanwezig, eenheid, prijsPerStuk, aantal, actief) VALUES
('Bandtype A', 10, 'stuk', 40.00, 0, 't'),
('Moertje type 2', 50, 'stuk', 0.50, 0, 't'),
('Brandstof type Diesel', 50, 'liter', 4.00, 0, 't')
;

/* Bestelling */

CREATE TABLE Bestelling (
bestellingid	INTEGER 	NOT NULL PRIMARY KEY AUTO_INCREMENT,
datum			DATE		,
isGeleverd		CHAR(1)		NOT NULL	
);

INSERT INTO Bestelling (datum, isGeleverd) VALUES
('2011-04-17', 'f'),
('2007-11-20', 'f'),
('2013-12-14', 'f')
;

/* BesteldProduct*/

CREATE TABLE BesteldProduct (
besteldproductid 	INTEGER 	NOT NULL PRIMARY KEY AUTO_INCREMENT,
hoeveelheid	 		INTEGER 	NOT NULL,
productid	 		INTEGER 	NOT NULL,
bestellingid		INTEGER 	NOT NULL,
FOREIGN KEY (productid) REFERENCES Product(productid),
FOREIGN KEY (bestellingid) REFERENCES Bestelling(bestellingid) 
);

INSERT INTO BesteldProduct (productid, hoeveelheid, bestellingid) VALUES
(1, 20, 2),
(2, 50, 3),
(3, 200, 1)
;

/* Auto */

CREATE TABLE Auto (
autoid 	 INTEGER 		NOT NULL PRIMARY KEY AUTO_INCREMENT,
kenteken VARCHAR(20)	NOT NULL,
merk	 VARCHAR(20)	NOT NULL,
type	 VARCHAR(20)	NOT NULL,
klantid	 INTEGER 		NOT NULL,
actief	 CHAR(1)		NOT NULL,
FOREIGN KEY (klantid) REFERENCES Klant(klantid) 
);

INSERT INTO Auto (kenteken, merk, type, klantid, actief) VALUES
('ABC', 'Ford', 'Fiesta', 1, 't'),
('DEF', 'Volkswagen', 'Polo', 2, 't'),
('GHI', 'Fiat', 'Panda', 3, 't')
;

/* Klus */

CREATE TABLE Klus (
klusid 			INTEGER 		NOT NULL PRIMARY KEY AUTO_INCREMENT,	
datum 			DATE	 		NOT NULL,
beschrijving 	VARCHAR(100)	NOT NULL,
manuren 		INTEGER			NOT NULL,
status 			VARCHAR(25)		NOT NULL,
soort 			VARCHAR(20)		NOT NULL,
autoid			INTEGER 		NOT NULL,
actief			CHAR(1)			NOT NULL,
FOREIGN KEY (autoid) REFERENCES Auto(autoid) 
);

INSERT INTO Klus (datum, beschrijving, soort, autoid, status, manuren, actief) VALUES 
('2010-04-25', 'Band vervangen', 'onderhoudsbeurt', 1, 'Nog niet begonnen', 0, 't'),
('2012-12-12', 'Einde van de wereld voorkomen', 'reparatie', 2, 'Nog niet begonnen', 0, 't'),
('2006-06-06', 'Day of the Beast', 'reparatie', 3, 'Nog niet begonnen', 0, 't')
;

/* Factuur */

CREATE TABLE Factuur (
factuurid			INTEGER 	NOT NULL PRIMARY KEY AUTO_INCREMENT,	
aanmaakDatum		DATE		NOT NULL,
betaalDatum			DATE		,
betalingswijze 		VARCHAR(30)	,
kortingsPercentage	INTEGER		,
isBetaald			CHAR(1)		NOT NULL,
klusid				INTEGER 	NOT NULL,
FOREIGN KEY (klusid) REFERENCES Klus(klusid) 
);

INSERT INTO Factuur (aanmaakDatum, klusid, isBetaald) VALUES
('2010-04-25', 1, 'f'),
('2012-12-12', 2, 'f'),
('2006-06-06', 3, 'f')
;

/* GebruiktProduct */

CREATE TABLE GebruiktProduct (
gebruiktproductid 	INTEGER 	NOT NULL PRIMARY KEY AUTO_INCREMENT,
aantal 				INTEGER		NOT NULL,
klusid				INTEGER 	NOT NULL,
productid			INTEGER 	NOT NULL,
FOREIGN KEY (productid) REFERENCES Product(productid), 
FOREIGN KEY (klusid) REFERENCES Klus(klusid) 
);	 

INSERT INTO GebruiktProduct (aantal, productid, klusid) VALUES
(1, 1, 1),
(10, 3, 2),
(5, 2, 3)
;

/* Herinneringsbrief */

CREATE TABLE Herinneringsbrief (
herinneringsbriefid	INTEGER 		NOT NULL PRIMARY KEY AUTO_INCREMENT,
reden				VARCHAR(9001)	NOT NULL,
datum				DATE			NOT NULL,
klantid				INTEGER 		NOT NULL,
FOREIGN KEY (klantid) REFERENCES Klant(klantid) 
);

INSERT INTO Herinneringsbrief (klantid, reden, datum) VALUES
(1, 'Betalen!', '2014-01-02'),
(2, 'Kom nog eens langs!', '2007-02-27'),
(3, 'Geef mij al je geld', '2012-01-02')
;

/* Reservering */

CREATE TABLE Reservering (
reserveringid 	INTEGER NOT NULL PRIMARY KEY AUTO_INCREMENT,
beginDat		DATE	NOT NULL,
eindDat			DATE	NOT NULL,
deParkeerplek	INTEGER	NOT NULL,
autoid			INTEGER NOT NULL,
isGeweest		CHAR(1) NOT NULL,
FOREIGN KEY (autoid) REFERENCES Auto(autoid)
);

INSERT INTO Reservering (beginDat, eindDat, autoid, deParkeerplek, isGeweest) VALUES
('2012-01-01', '2012-01-02', 1, 1, 'f'),
('2013-02-02', '2013-04-01', 2, 5, 'f'),
('2014-01-15', '2014-02-05', 3, 15, 'f')
;