CREATE TABLE Refuge(
    mail_refuge varchar(100) NOT NULL,  
    nom_refuge varchar(100) NOT NULL,  
    secteur_geograp varchar(255) NOT NULL,  
    date_ouverture date NOT NULL, 
    date_fermeture date NOT NULL, 
    places_repas int NOT NULL,
    places_nuit int NOT NULL,
    presentation varchar(1000) NOT NULL,  
    prix_nuitee int NOT NULL,
    PRIMARY KEY(mail_refuge)
);

CREATE TABLE Refuge_telephone(
    mail_refuge varchar(100) NOT NULL,
    telephone_refuge char(10) NOT NULL, 
    PRIMARY KEY(mail_refuge),
    FOREIGN KEY(mail_refuge) REFERENCES Refuge(mail_refuge)
);

CREATE TABLE Refuge_prix_repas(
    mail_refuge varchar(100) NOT NULL,  
    type_repas varchar(30) NOT NULL CHECK(type_repas IN ('dejeuner', 'casse-croute', 'diner', 'souper')),
    prix_repas int NOT NULL,
    PRIMARY KEY(mail_refuge, type_repas),
    FOREIGN KEY(mail_refuge) REFERENCES Refuge(mail_refuge)
);
 
CREATE TABLE Refuge_paiement(
    mail_refuge varchar(100) NOT NULL,
    type_moyen_paiement varchar(30) NOT NULL CHECK(type_moyen_paiement IN ('cheque', 'espece', 'carte bancaire')),
    PRIMARY KEY(mail_refuge, type_moyen_paiement),
    FOREIGN KEY(mail_refuge) REFERENCES Refuge(mail_refuge)
);

CREATE TABLE Comptes_utilisateur(
    id_utilisateur int NOT NULL,   
    somme_due int NOT NULL CHECK(somme_due >= 0),
    somme_remboursee int NOT NULL CHECK(somme_remboursee >= 0),
    PRIMARY KEY(id_utilisateur)
);

CREATE TABLE Membre(
    id_utilisateur int NOT NULL,
    mail_compte varchar(100) NOT NULL,
    mot_de_passe varchar(40) NOT NULL,
    nom_compte varchar(100) NOT NULL,
    prenom_compte varchar(100) NOT NULL,
    adresse_compte varchar(255) NOT NULL,
    PRIMARY KEY(mail_compte),
    FOREIGN KEY(id_utilisateur) REFERENCES Comptes_utilisateur(id_utilisateur)
);

CREATE TABLE Adherents(
    id_adherent int NOT NULL,
    PRIMARY KEY(id_adherent)
);

CREATE TABLE Jointure_Adherents(
    id_utilisateur int NOT NULL,
    id_adherent int NOT NULL,
    PRIMARY KEY(id_utilisateur),
    FOREIGN KEY(id_utilisateur) REFERENCES Comptes_utilisateur(id_utilisateur),
    FOREIGN KEY(id_adherent) REFERENCES Adherents(id_adherent)
);

CREATE TABLE Refuge_reservation(
    mail_refuge varchar(100) NOT NULL,
    id_reservation_ref int NOT NULL,
    date_reservation_ref date NOT NULL,
    heure_reservation_ref int NOT NULL,
    id_utilisateur int NOT NULL,
    nb_nuits int NOT NULL,
    nb_repas int NOT NULL,
    prix_total_reservation int NOT NULL CHECK(prix_total_reservation >= 0),
    PRIMARY KEY(mail_refuge, id_reservation_ref),
    FOREIGN KEY(mail_refuge) REFERENCES Refuge(mail_refuge),
    FOREIGN KEY(id_utilisateur) REFERENCES Comptes_utilisateur(id_utilisateur)
);

CREATE TABLE Repas_reserves(
    mail_refuge varchar(100) NOT NULL,
    id_reservation_ref int NOT NULL,
    type_repas varchar(30) NOT NULL CHECK(type_repas IN ('dejeuner', 'casse-croute', 'diner', 'souper')),
    jour int NOT NULL,
    PRIMARY KEY(mail_refuge, id_reservation_ref, type_repas, jour),
    FOREIGN KEY(mail_refuge, id_reservation_ref) REFERENCES Refuge_reservation(mail_refuge, id_reservation_ref)
);

CREATE TABLE Activite(
    nom_act varchar(30) NOT NULL,
    PRIMARY KEY(nom_act)
);

CREATE TABLE Formation(
    id_formation char(15) NOT NULL, 
    nom_formation varchar(100) NOT NULL,  
    description_formation varchar(1000) NOT NULL, 
    date_formation date NOT NULL,
    duree_formation int NOT NULL, 
    places_formation int NOT NULL CHECK(places_formation >= 0),
    prix_formation int NOT NULL,
    PRIMARY KEY(id_formation)
);

CREATE TABLE Formation_activites(
    id_formation char(15) NOT NULL,
    nom_act varchar(30) NOT NULL,
    PRIMARY KEY(id_formation, nom_act),
    FOREIGN KEY(id_formation) REFERENCES Formation(id_formation),
    FOREIGN KEY(nom_act) REFERENCES Activite(nom_act)
);
 
CREATE TABLE Formation_reservation(
    id_formation char(15) NOT NULL,
    id_adherent int NOT NULL,
    rang_attente int NOT NULL CHECK(rang_attente >= 0), 
    PRIMARY KEY(id_formation, id_adherent),
    FOREIGN KEY(id_formation) REFERENCES Formation(id_formation),
    FOREIGN KEY(id_adherent) REFERENCES Adherents(id_adherent)
);

CREATE TABLE Arbre_categories(
    categorie varchar(30) NOT NULL,
    categorie_parente varchar(30) NOT NULL,
    PRIMARY KEY(categorie),
    FOREIGN KEY(categorie_parente) REFERENCES Arbre_categories(categorie)
);

CREATE TABLE Materiel(
    marque varchar(50) NOT NULL,
    modele varchar(100) NOT NULL,
    date_achat date NOT NULL,
    nb_pieces int NOT NULL CHECK(nb_pieces >= 0),
    prix_casse int NOT NULL,
    categorie varchar(30) NOT NULL,
    PRIMARY KEY(marque, modele, date_achat),
    FOREIGN KEY(categorie) REFERENCES Arbre_categories(categorie)
);

CREATE TABLE Materiel_description(
    marque varchar(50) NOT NULL,
    modele varchar(100) NOT NULL,
    date_achat date NOT NULL,
    description_materiel varchar(1000) NOT NULL,
    PRIMARY KEY(marque, modele, date_achat),
    FOREIGN KEY(marque, modele, date_achat) REFERENCES Materiel(marque, modele, date_achat)
);

CREATE TABLE Materiel_peremption(
    marque varchar(50) NOT NULL,
    modele varchar(100) NOT NULL,
    date_achat date NOT NULL,
    date_peremption date NOT NULL,
    PRIMARY KEY(marque, modele, date_achat),
    FOREIGN KEY(marque, modele, date_achat) REFERENCES Materiel(marque, modele, date_achat)
);

CREATE TABLE Materiel_activites(
    marque varchar(50) NOT NULL,
    modele varchar(100) NOT NULL,
    date_achat date NOT NULL,
    nom_act varchar(30) NOT NULL,
    PRIMARY KEY(marque, modele, date_achat, nom_act),
    FOREIGN KEY(marque, modele, date_achat) REFERENCES Materiel(marque, modele, date_achat),
    FOREIGN KEY(nom_act) REFERENCES Activite(nom_act)
); 
 
CREATE TABLE Materiel_reservation(
    marque varchar(50) NOT NULL,
    modele varchar(100) NOT NULL,
    date_achat date NOT NULL,
    id_resa_materiel int NOT NULL,
    id_adherent int NOT NULL,
    nb_pieces int NOT NULL CHECK(nb_pieces >= 0),
    date_recup date NOT NULL,
    date_rendu date NOT NULL,
    PRIMARY KEY(id_resa_materiel),
    FOREIGN KEY(marque, modele, date_achat) REFERENCES Materiel(marque, modele, date_achat),
    FOREIGN KEY(id_adherent) REFERENCES Adherents(id_adherent)
);

CREATE TABLE Materiel_casse(
    id_resa_materiel int NOT NULL,
    nb_casse int NOT NULL,
    PRIMARY KEY(id_resa_materiel),
    FOREIGN KEY(id_resa_materiel) REFERENCES Materiel_reservation(id_resa_materiel)
);


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

CREATE SEQUENCE seq_id_reservation_ref
MINVALUE 1
START WITH 1
INCREMENT BY 1
CACHE 10;

CREATE SEQUENCE seq_id_formation
MINVALUE 1
START WITH 1
INCREMENT BY 1
CACHE 10;

CREATE SEQUENCE seq_id_resa_materiel
MINVALUE 1
START WITH 1
INCREMENT BY 1
CACHE 10;