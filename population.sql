INSERT INTO Refuge VALUES('refugedes2alpes@gmail.com', 'Que du bon!', 'Les 2 Aples', TO_DATE('2023-12-01', 'YYYY-MM-DD'), TO_DATE('2024-04-01', 'YYYY-MM-DD'), 35, 35, 'Refuge au pied des piste, bon pour skier toutes vos vacances.', 39);
INSERT INTO Refuge VALUES('chamonix.le.refuge@domaine.fr', 'Le Chamoix', 'Chamonix', TO_DATE('2023-12-04', 'YYYY-MM-DD'), TO_DATE('2024-04-08', 'YYYY-MM-DD'), 17, 20, 'Petit chalet très kitch mais cosie, vous vous sentirez bien.', 32);
INSERT INTO Refuge VALUES('au-coin-du-feu@chamrousse.fr', 'Au coin du feu', 'Chamrousse', TO_DATE('2023-11-24', 'YYYY-MM-DD'), TO_DATE('2024-05-01', 'YYYY-MM-DD'), 47, 40, 'Grand refuge chaleureux. Nous accueillons aussi bien les familles de les groupes.', 49);

INSERT INTO Refuge_telephone VALUES('refugedes2alpes@gmail.com', '0476124598');
INSERT INTO Refuge_telephone VALUES('chamonix.le.refuge@domaine.fr', '0435956781');
INSERT INTO Refuge_telephone VALUES('au-coin-du-feu@chamrousse.fr', '0467890152');

INSERT INTO Refuge_prix_repas VALUES('refugedes2alpes@gmail.com', 'dejeuner', 8);
INSERT INTO Refuge_prix_repas VALUES('refugedes2alpes@gmail.com', 'casse-croute', 5);
INSERT INTO Refuge_prix_repas VALUES('refugedes2alpes@gmail.com', 'diner', 7);
INSERT INTO Refuge_prix_repas VALUES('refugedes2alpes@gmail.com', 'souper', 4);
INSERT INTO Refuge_prix_repas VALUES('chamonix.le.refuge@domaine.fr', 'dejeuner', 10);
INSERT INTO Refuge_prix_repas VALUES('chamonix.le.refuge@domaine.fr', 'casse-croute', 8);
INSERT INTO Refuge_prix_repas VALUES('chamonix.le.refuge@domaine.fr', 'diner', 7);
INSERT INTO Refuge_prix_repas VALUES('chamonix.le.refuge@domaine.fr', 'souper', 3);
INSERT INTO Refuge_prix_repas VALUES('au-coin-du-feu@chamrousse.fr', 'dejeuner', 9);
INSERT INTO Refuge_prix_repas VALUES('au-coin-du-feu@chamrousse.fr', 'casse-croute', 7);
INSERT INTO Refuge_prix_repas VALUES('au-coin-du-feu@chamrousse.fr', 'diner', 12);
INSERT INTO Refuge_prix_repas VALUES('au-coin-du-feu@chamrousse.fr', 'souper', 6);

INSERT INTO Refuge_paiement VALUES('refugedes2alpes@gmail.com', 'carte bancaire');
INSERT INTO Refuge_paiement VALUES('refugedes2alpes@gmail.com', 'espece');
INSERT INTO Refuge_paiement VALUES('chamonix.le.refuge@domaine.fr', 'carte bancaire');
INSERT INTO Refuge_paiement VALUES('chamonix.le.refuge@domaine.fr', 'cheque');
INSERT INTO Refuge_paiement VALUES('au-coin-du-feu@chamrousse.fr', 'cheque');

INSERT INTO Activite VALUES('escalade');
INSERT INTO Activite VALUES('ski de rando');
INSERT INTO Activite VALUES('randonnee');
INSERT INTO Activite VALUES('alpinisme');
INSERT INTO Activite VALUES('spéléologie');
INSERT INTO Activite VALUES('cascade de glace');

INSERT INTO FORMATION VALUES('2023-id:0000001', 'formation gestion de groupe', 'Si vous voulez devenir guide, accompagnant de haute montagne ou professeur de sport de montagne, cette formation est faite pour vous.', TO_DATE('2023-01-06', 'YYYY-MM-DD'), 2, 25, 190);
INSERT INTO FORMATION VALUES('2023-id:0000002', 'devenir professeur de ski alpin', 'formation nécessaire pour devenir professeur à l ESF', TO_DATE('2023-01-19', 'YYYY-MM-DD'), 20, 15, 345);
INSERT INTO FORMATION VALUES('2023-id:0000003', 'pilote d helicoptere', 'formation pour sauver des vies', TO_DATE('2023-02-01', 'YYYY-MM-DD'), 30, 10, 200);
INSERT INTO FORMATION VALUES('2023-id:0000004', 'encadrer en spéléo', 'formation pour devenir guide de speleologie', TO_DATE('2023-03-01', 'YYYY-MM-DD'), 14, 35, 346);

INSERT INTO Formation_activites VALUES('2023-id:0000001', 'escalade');
INSERT INTO Formation_activites VALUES('2023-id:0000001', 'ski de rando');
INSERT INTO Formation_activites VALUES('2023-id:0000001', 'randonnee');
INSERT INTO Formation_activites VALUES('2023-id:0000001', 'alpinisme');
INSERT INTO Formation_activites VALUES('2023-id:0000001', 'spéléologie');
INSERT INTO Formation_activites VALUES('2023-id:0000001', 'cascade de glace');
INSERT INTO Formation_activites VALUES('2023-id:0000002', 'ski de rando');
INSERT INTO Formation_activites VALUES('2023-id:0000002', 'alpinisme');
INSERT INTO Formation_activites VALUES('2023-id:0000003', 'cascade de glace');
INSERT INTO Formation_activites VALUES('2023-id:0000004', 'spéléologie');
INSERT INTO Formation_activites VALUES('2023-id:0000004', 'escalade');

INSERT INTO Arbre_categories VALUES('Sport montagne', 'Sport montagne');
INSERT INTO Arbre_categories VALUES('escalade', 'Sport montagne');
INSERT INTO Arbre_categories VALUES('ski', 'Sport montagne');
INSERT INTO Arbre_categories VALUES('ski alpin', 'ski');
INSERT INTO Arbre_categories VALUES('ski nordique', 'ski');
INSERT INTO Arbre_categories VALUES('skis vitesse', 'ski alpin');
INSERT INTO Arbre_categories VALUES('batons', 'ski alpin');
INSERT INTO Arbre_categories VALUES('casque', 'ski alpin');
INSERT INTO Arbre_categories VALUES('gants', 'ski alpin');
INSERT INTO Arbre_categories VALUES('chaussures', 'ski alpin');
INSERT INTO Arbre_categories VALUES('skis fins', 'ski nordique');
INSERT INTO Arbre_categories VALUES('mouffles', 'ski nordique');
INSERT INTO Arbre_categories VALUES('bodrier', 'escalade');
INSERT INTO Arbre_categories VALUES('mousqueton', 'escalade');
INSERT INTO Arbre_categories VALUES('corde', 'escalade');
INSERT INTO Arbre_categories VALUES('dégaine', 'escalade');
INSERT INTO Arbre_categories VALUES('à vis', 'mousqueton');
INSERT INTO Arbre_categories VALUES('à clou', 'mousqueton');

INSERT INTO Materiel VALUES('nike', 'skis', TO_DATE('2020-01-01', 'YYYY-MM-DD'), 50, 125, 'skis vitesse');
INSERT INTO Materiel VALUES('rossignol', 'casque de ski', TO_DATE('2017-06-01', 'YYYY-MM-DD'), 20, 53, 'casque');
INSERT INTO Materiel VALUES('kappa', 'bodrier d escalade', TO_DATE('2022-05-01', 'YYYY-MM-DD'), 30, 39, 'bodrier');

INSERT INTO Materiel_description VALUES('nike', 'skis', TO_DATE('2020-01-01', 'YYYY-MM-DD'), 'skis niveau intermediaire');
INSERT INTO Materiel_description VALUES('rossignol', 'casque de ski', TO_DATE('2017-06-01', 'YYYY-MM-DD'), 'Les casques de skis sont devenus obligatoires.');
INSERT INTO Materiel_description VALUES('kappa', 'bodrier d escalade', TO_DATE('2022-05-01', 'YYYY-MM-DD'), 'materiel d escalade fait pour grimpper en falaise.');

INSERT INTO Materiel_peremption VALUES('nike', 'skis', TO_DATE('2020-01-01', 'YYYY-MM-DD'), TO_DATE('2030-01-01', 'YYYY-MM-DD'));
INSERT INTO Materiel_peremption VALUES('rossignol', 'casque de ski', TO_DATE('2017-06-01', 'YYYY-MM-DD'), TO_DATE('2027-06-01', 'YYYY-MM-DD'));

INSERT INTO Materiel_activites VALUES('nike', 'skis', TO_DATE('2020-01-01', 'YYYY-MM-DD'), 'ski de rando');
INSERT INTO Materiel_activites VALUES('rossignol', 'casque de ski', TO_DATE('2017-06-01', 'YYYY-MM-DD'), 'ski de rando');
INSERT INTO Materiel_activites VALUES('kappa', 'bodrier d escalade', TO_DATE('2022-05-01', 'YYYY-MM-DD'), 'escalade');


INSERT INTO Comptes_utilisateur VALUES (seq_utilisateur.NEXTVAL, 0, 0);
INSERT INTO Membre VALUES (seq_utilisateur.CURRVAL, 'mathieu.beaugosse@grandcercle.fr', 'BdDGrosLove', 'Meunier', 'Mathieu', '25 grand rue, 38000, ChicaGre');
INSERT INTO Adherents VALUES (seq_adherent.NEXTVAL);
INSERT INTO Jointure_Adherents VALUES (seq_utilisateur.CURRVAL, seq_adherent.CURRVAL);

INSERT INTO Comptes_utilisateur VALUES (seq_utilisateur.NEXTVAL, 0, 0);
INSERT INTO Membre VALUES (seq_utilisateur.CURRVAL, 'steve.java@imag.fr', 'SQLPlusPlus', 'Lening Talaupa', 'Steve', '16 boulevard des Lumieres, 38400, Saint-Martin-Heres');

INSERT INTO Comptes_utilisateur VALUES (seq_utilisateur.NEXTVAL, 0, 0);
INSERT INTO Membre VALUES (seq_utilisateur.CURRVAL, 'anais.low@bde.com', 'CommeCestBizarre', 'Douet', 'Anaïs', '8 rue Hubert Dubedout, 38000, Grenoble');
INSERT INTO Adherents VALUES (seq_adherent.NEXTVAL);
INSERT INTO Jointure_Adherents VALUES (seq_utilisateur.CURRVAL, seq_adherent.CURRVAL);

INSERT INTO Comptes_utilisateur VALUES (seq_utilisateur.NEXTVAL, 0, 0);
INSERT INTO Membre VALUES (seq_utilisateur.CURRVAL, 'pierre.qualite@nsigma.fr', 'PasDeSommeil', 'Poinas', 'Pierre', '6 rue Alexandre Vrecko, 38400, Saint-Martin-Heres');

INSERT INTO Comptes_utilisateur VALUES (seq_utilisateur.NEXTVAL, 0, 0);
INSERT INTO Membre VALUES (seq_utilisateur.CURRVAL, 'adam.bouffe@bds.com', 'JeRangeMaChambre', 'Benoit', 'Adam', '4 rue du Palace, 38002, Casserne de Bonne');
INSERT INTO Adherents VALUES (seq_adherent.NEXTVAL);
INSERT INTO Jointure_Adherents VALUES (seq_utilisateur.CURRVAL, seq_adherent.CURRVAL);


INSERT INTO Comptes_utilisateur VALUES(seq_utilisateur.NEXTVAL, 89, 76);
INSERT INTO Membre VALUES(seq_utilisateur.CURRVAL, 'gerard@gmail.com', 'Grerarddu83', 'Flambi', 'Gérard', '567 rue des poules, 83000 Toulon');
INSERT INTO Adherents VALUES(seq_adherent.NEXTVAL);
INSERT INTO Jointure_Adherents VALUES(seq_utilisateur.CURRVAL, seq_adherent.CURRVAL);
INSERT INTO Refuge_reservation VALUES('refugedes2alpes@gmail.com', seq_id_reservation_ref.NEXTVAL, TO_DATE('2024-02-01', 'YYYY-MM-DD'), 11, seq_utilisateur.CURRVAL, 4, 1, 246);
INSERT INTO Refuge_reservation VALUES('au-coin-du-feu@chamrousse.fr', seq_id_reservation_ref.CURRVAL, TO_DATE('2024-03-10', 'YYYY-MM-DD'), 12, seq_utilisateur.CURRVAL, 3, 7, 279);
INSERT INTO Repas_reserves VALUES('refugedes2alpes@gmail.com', seq_id_reservation_ref.CURRVAL, 'dejeuner', 1);
INSERT INTO Repas_reserves VALUES('au-coin-du-feu@chamrousse.fr', seq_id_reservation_ref.CURRVAL, 'souper', 1);
INSERT INTO Repas_reserves VALUES('au-coin-du-feu@chamrousse.fr', seq_id_reservation_ref.CURRVAL, 'casse-croute', 1);
INSERT INTO Repas_reserves VALUES('au-coin-du-feu@chamrousse.fr', seq_id_reservation_ref.CURRVAL, 'dejeuner', 2);
INSERT INTO Repas_reserves VALUES('au-coin-du-feu@chamrousse.fr', seq_id_reservation_ref.CURRVAL, 'souper', 2);
INSERT INTO Repas_reserves VALUES('au-coin-du-feu@chamrousse.fr', seq_id_reservation_ref.CURRVAL, 'diner', 2);
INSERT INTO Repas_reserves VALUES('au-coin-du-feu@chamrousse.fr', seq_id_reservation_ref.CURRVAL, 'dejeuner', 3);
INSERT INTO Repas_reserves VALUES('au-coin-du-feu@chamrousse.fr', seq_id_reservation_ref.CURRVAL, 'casse-croute', 3);
INSERT INTO Formation_reservation VALUES('2023-id:0000001', seq_adherent.CURRVAL, 0);
INSERT INTO Formation_reservation VALUES('2023-id:0000003', seq_adherent.CURRVAL, 0);
INSERT INTO Materiel_reservation VALUES('nike', 'skis', TO_DATE('2020-01-01', 'YYYY-MM-DD'), seq_id_resa_materiel.NEXTVAL, seq_adherent.CURRVAL, 6, TO_DATE('2023-02-01', 'YYYY-MM-DD'), TO_DATE('2023-02-05', 'YYYY-MM-DD'));
INSERT INTO Materiel_reservation VALUES('rossignol', 'casque de ski', TO_DATE('2017-06-01', 'YYYY-MM-DD'), seq_id_resa_materiel.NEXTVAL, seq_adherent.CURRVAL, 6, TO_DATE('2023-02-01', 'YYYY-MM-DD'), TO_DATE('2023-02-05', 'YYYY-MM-DD'));
INSERT INTO Materiel_casse VALUES(seq_id_resa_materiel.CURRVAL, 2);


INSERT INTO Comptes_utilisateur VALUES(seq_utilisateur.NEXTVAL, 65, 157);
INSERT INTO Membre VALUES(seq_utilisateur.CURRVAL, 'pascal@hotmail.com', 'JaimeLaVieEtLeChoco', 'Otch', 'Pascal', '4 rue François Damiens, 26000 Valence');
INSERT INTO Refuge_reservation VALUES('chamonix.le.refuge@domaine.fr', seq_id_reservation_ref.NEXTVAL, TO_DATE('2024-03-06', 'YYYY-MM-DD'), 9, seq_utilisateur.CURRVAL, 7, 20, 678);
INSERT INTO Repas_reserves VALUES('chamonix.le.refuge@domaine.fr', seq_id_reservation_ref.CURRVAL, 'dejeuner', 1);
INSERT INTO Repas_reserves VALUES('chamonix.le.refuge@domaine.fr', seq_id_reservation_ref.CURRVAL, 'dejeuner', 2);
INSERT INTO Repas_reserves VALUES('chamonix.le.refuge@domaine.fr', seq_id_reservation_ref.CURRVAL, 'dejeuner', 3);
INSERT INTO Repas_reserves VALUES('chamonix.le.refuge@domaine.fr', seq_id_reservation_ref.CURRVAL, 'dejeuner', 4);
INSERT INTO Repas_reserves VALUES('chamonix.le.refuge@domaine.fr', seq_id_reservation_ref.CURRVAL, 'dejeuner', 5);
INSERT INTO Repas_reserves VALUES('chamonix.le.refuge@domaine.fr', seq_id_reservation_ref.CURRVAL, 'dejeuner', 6);
INSERT INTO Repas_reserves VALUES('chamonix.le.refuge@domaine.fr', seq_id_reservation_ref.CURRVAL, 'dejeuner', 7);
INSERT INTO Repas_reserves VALUES('chamonix.le.refuge@domaine.fr', seq_id_reservation_ref.CURRVAL, 'casse-croute', 1);
INSERT INTO Repas_reserves VALUES('chamonix.le.refuge@domaine.fr', seq_id_reservation_ref.CURRVAL, 'casse-croute', 2);
INSERT INTO Repas_reserves VALUES('chamonix.le.refuge@domaine.fr', seq_id_reservation_ref.CURRVAL, 'casse-croute', 3);
INSERT INTO Repas_reserves VALUES('chamonix.le.refuge@domaine.fr', seq_id_reservation_ref.CURRVAL, 'casse-croute', 4);
INSERT INTO Repas_reserves VALUES('chamonix.le.refuge@domaine.fr', seq_id_reservation_ref.CURRVAL, 'casse-croute', 5);
INSERT INTO Repas_reserves VALUES('chamonix.le.refuge@domaine.fr', seq_id_reservation_ref.CURRVAL, 'casse-croute', 6);
INSERT INTO Repas_reserves VALUES('chamonix.le.refuge@domaine.fr', seq_id_reservation_ref.CURRVAL, 'casse-croute', 7);
INSERT INTO Repas_reserves VALUES('chamonix.le.refuge@domaine.fr', seq_id_reservation_ref.CURRVAL, 'souper', 1);
INSERT INTO Repas_reserves VALUES('chamonix.le.refuge@domaine.fr', seq_id_reservation_ref.CURRVAL, 'souper', 2);
INSERT INTO Repas_reserves VALUES('chamonix.le.refuge@domaine.fr', seq_id_reservation_ref.CURRVAL, 'souper', 3);
INSERT INTO Repas_reserves VALUES('chamonix.le.refuge@domaine.fr', seq_id_reservation_ref.CURRVAL, 'souper', 4);
INSERT INTO Repas_reserves VALUES('chamonix.le.refuge@domaine.fr', seq_id_reservation_ref.CURRVAL, 'souper', 5);
INSERT INTO Repas_reserves VALUES('chamonix.le.refuge@domaine.fr', seq_id_reservation_ref.CURRVAL, 'souper', 6);


INSERT INTO Comptes_utilisateur VALUES(seq_utilisateur.NEXTVAL, 0, 0);
INSERT INTO Membre VALUES(seq_utilisateur.CURRVAL, 'admin', 'admin', 'admin', 'admin', 'admin');
INSERT INTO Adherents VALUES(seq_adherent.NEXTVAL);
INSERT INTO Jointure_Adherents VALUES(seq_utilisateur.CURRVAL, seq_adherent.CURRVAL);
INSERT INTO Refuge_reservation VALUES('au-coin-du-feu@chamrousse.fr', seq_id_reservation_ref.NEXTVAL, TO_DATE('2023-01-01', 'YYYY-MM-DD'), 15, seq_utilisateur.CURRVAL, 1, 2, 162);
INSERT INTO Repas_reserves VALUES('au-coin-du-feu@chamrousse.fr', seq_id_reservation_ref.CURRVAL, 'souper', 1);
INSERT INTO Repas_reserves VALUES('au-coin-du-feu@chamrousse.fr', seq_id_reservation_ref.CURRVAL, 'dejeuner', 2);
INSERT INTO Formation_reservation VALUES('2023-id:0000002', seq_adherent.CURRVAL, 0);
INSERT INTO Formation_reservation VALUES('2023-id:0000004', seq_adherent.CURRVAL, 0);
INSERT INTO Materiel_reservation VALUES('kappa', 'bodrier d escalade', TO_DATE('2022-05-01', 'YYYY-MM-DD'), seq_id_resa_materiel.NEXTVAL, seq_adherent.CURRVAL, 1, TO_DATE('2023-01-02', 'YYYY-MM-DD'), TO_DATE('2023-01-02', 'YYYY-MM-DD'));

