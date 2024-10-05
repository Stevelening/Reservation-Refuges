# Modèle logique

Refuge (***mail_refuge***, nom_refuge, secteur_geograp, date_ouverture, date_fermeture, places_repas, places_nuit, presentation, prix_nuitee)

Refuge_paiement (***mail_refuge*** (Refuge), ***type_moyen_paiement***)

Refuge_telephone (***mail_refuge*** (Refuge), telephone_refuge)

Comptes_utilisateur (***id_utilisateur***, somme_due, somme_remboursee)

Membres (***mail_membre***, mot_de_passe, nom_membre, prenom_membre, adresse_membre, ID_utilisateur (Comptes_utilisateur))

Adherents (***ID_adherent***)

JointureAdherents (***id_utilisateur*** (Comptes_utilisateur), id_adherent (Adherents))

Refuge_reservation (***mail_refuge*** (Refuge), ***id_reservation***, date_reservation, heure_reservation, nb_nuit, nb_repas, prix_total, ID_utilisateur(Comptes_utilisateur))

Refuge_repas (***mail_refuge*** (Refuge), ***type_repas***, prix_repas)

Repas_reserve (***mail_refuge*** (Refuge_reservation), ***id_reservation*** (Refuge_reservation), ***jour***, ***type_repas***)

Formation (***ID_Formation***, nom_formation, description_formation, date_formation, duree_formation, nb_places, prix)

Formation_reservation (***ID_adherent*** (Adherents), ***ID_formation*** (Formation), rang_attente)

Categories (***categorie***, categorie_parente (Categories))

Materiel (***marque***, ***modele***, ***date_achat***, nb_pieces, prix_casse, categorie (Categories))

Materiel_description(description, ***marque*** (Materiel), ***modele*** (Materiel), ***date_achat*** (Materiel))

Materiel_peremption(date_peremption, ***marque*** (Materiel), ***modele*** (Materiel), ***date_achat*** (Materiel))

Activites (***type_activite***)

Formation_activites (***type_activite*** (Activites), ***ID_formation*** (Formation))

Materiel_activites (type_activites (Activites), ***marque*** (Materiel), ***modele*** (Materiel), ***date_achat*** (Materiel))

Materiel_reservation (***ID_reservation_materiel***, nb_pieces, date_recup, date_rendu, marque (Materiel), modele (Materiel), date_achat (Materiel), ID_adherent (Adherents))

Materiel_casse (***ID_reservation_materiel*** (Materiel_reservation), nb_casse)