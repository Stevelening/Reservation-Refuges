DROP SEQUENCE seq_utilisateur;
DROP SEQUENCE seq_adherent;

CREATE SEQUENCE seq_utilisateur
MINVALUE 1
START WITH 1
INCREMENT BY 1
CACHE 10;

CREATE SEQUENCE seq_adherent
MINVALUE 1
START WITH 1
INCREMENT BY 1
CACHE 10;


-- Utilisateur n°1
INSERT INTO Comptes_utilisateur VALUES (seq_utilisateur.NEXTVAL, 0, 0);
INSERT INTO Membre VALUES (seq_utilisateur.CURRVAL, 'mathieu.beaugosse@grandcercle.fr', 'BdDGrosLove', 'Meunier', 'Mathieu', '25 grand rue, 38000, ChicaGre');
INSERT INTO Adherents VALUES (seq_adherent.NEXTVAL);
INSERT INTO Jointure_Adherents VALUES (seq_utilisateur.CURRVAL, seq_adherent.CURRVAL);

-- Utilisateur n°2
INSERT INTO Comptes_utilisateur VALUES (seq_utilisateur.NEXTVAL, 0, 0);
INSERT INTO Membre VALUES (seq_utilisateur.CURRVAL, 'steve.java@imag.fr', 'SQLPlusPlus', 'Lening Talaupa', 'Steve', '16 boulevard des Lumieres, 38400, Saint-Martin-Heres');

-- Utilisateur n°3
INSERT INTO Comptes_utilisateur VALUES (seq_utilisateur.NEXTVAL, 0, 0);
INSERT INTO Membre VALUES (seq_utilisateur.CURRVAL, 'anais.low@bde.com', 'CommeCestBizarre', 'Douet', 'Anaïs', '8 rue Hubert Dubedout, 38000, Grenoble');
INSERT INTO Adherents VALUES (seq_adherent.NEXTVAL);
INSERT INTO Jointure_Adherents VALUES (seq_utilisateur.CURRVAL, seq_adherent.CURRVAL);

-- Utilisateur n°4
INSERT INTO Comptes_utilisateur VALUES (seq_utilisateur.NEXTVAL, 0, 0);
INSERT INTO Membre VALUES (seq_utilisateur.CURRVAL, 'pierre.qualite@nsigma.fr', 'PasDeSommeil', 'Poinas', 'Pierre', '6 rue Alexandre Vrecko, 38400, Saint-Martin-Heres');

-- Utilisateur n°5
INSERT INTO Comptes_utilisateur VALUES (seq_utilisateur.NEXTVAL, 0, 0);
INSERT INTO Membre VALUES (seq_utilisateur.CURRVAL, 'adam.bouffe@bds.com', 'JeRangeMaChambre', 'Benoit', 'Adam', '4 rue du Palace, 38002, Casserne de Bonne');
INSERT INTO Adherents VALUES (seq_adherent.NEXTVAL);
INSERT INTO Jointure_Adherents VALUES (seq_utilisateur.CURRVAL, seq_adherent.CURRVAL);

