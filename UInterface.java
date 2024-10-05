import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.temporal.ChronoField;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.LinkedList;
import java.util.Scanner;
import java.util.regex.Pattern;



public class UInterface{
    private static int currentUser = -1; // id de l'utilisateur courant
    private static boolean isAdherent = false;
    public static void main(String[] args) {
        try{
            DriverManager.registerDriver(new oracle.jdbc.driver.OracleDriver());
        }catch(SQLException e){
            e.printStackTrace();
        }
        boolean isConnect = false;
        Scanner scanner = new Scanner(System.in);
        Connection connection;

        while(!isConnect){
            System.out.println("Veuillez vous connecter s'il vous plait");

            String usr = "";
            boolean conformEmail = false;
            while(!conformEmail){
                System.out.println("Entrez votre email :");
                usr = scanner.nextLine();
                if(isConformEmail(usr) || usr.equals("admin")){
                    conformEmail = true;
                }
                else{
                    System.out.println("Veuillez entrer un email valide");
                }
            }

            String mdp = "";
            boolean conformPassword = false;
            while(!conformPassword){
                System.out.println("Entrez votre mot de passe :");
                mdp = scanner.nextLine();
                if(isConformPassword(mdp) || mdp.equals("admin")){
                    conformPassword = true;
                }
                else{
                    System.out.println("Le mot de passe doit avoir au moins 8 caracteres");
                }
            }

            try{
                connection = dbConnect();
                isConnect = usrConnect(usr, mdp);
                if(!isConnect){
                    System.out.println("Mot de passe ou nom d'utilisateur erroné, veuillez réessayer");
                }
            }catch(SQLException e){
                System.out.println("SLQExecption sortie du programme");
                System.exit(0);
            }
        }
        boolean running = true;

        while(running){
            System.out.println("Veuillez sélectionner une action : \n1.Parcourir les services\n" + //
                    "2.Détails du compte\n3.Sortir de l'application");
            Scanner scanner2 = new Scanner(System.in);
            int choix = scanner2.nextInt();

            switch(choix){
                case 1:
                    Services();
                    break;
                case 2:
                    running = account();
                    break;
                case 3:
                    running = false;
                    break;
                default:
                    System.out.println("Veuillez choisir une option valide");
                    break;
            }
        }
    }

    private static boolean account(){
        try{
            boolean running = true;
            while(running){
                Connection connection = dbConnect();
                PreparedStatement stmt = connection.prepareStatement("SELECT * FROM COMPTES_UTILISATEUR WHERE id_utilisateur=?");
                stmt.setInt(1, currentUser);
                ResultSet rset = stmt.executeQuery();
                if(!rset.next()){
                    return true;
                }
                int due = rset.getInt(2);
                int rembour = rset.getInt(3);
                stmt = connection.prepareStatement("SELECT * FROM Membre Where id_utilisateur=?");
                stmt.setInt(1, currentUser);
                rset = stmt.executeQuery();
                if(!rset.next()){
                    return true;
                }


                System.out.println("-----------------------------------------------\n"+String.format("%32s",rset.getString(4)+" "+rset.getString(5)+"\n"));

                System.out.println("Email : "+rset.getString(2)+"\n");
                System.out.println("Adresse : "+rset.getString(6)+"\n");
                System.out.println("Somme due : "+due+"     somme remboursé : "+rembour+"\n");

                if(isAdherent){
                    System.out.println("Vous êtes adhérent du site");
                }else{
                    System.out.println("Vous n'êtes pas adhérent du site");
                }

                System.out.println("--------------------------------------------------");

                System.out.println("1.Rembourser ses dettes\n2.Supprimer son compte\n3.retour\n ");

                connection.close();

                Scanner scanner = new Scanner(System.in);
            
                int choix = scanner.nextInt();

                if(choix==1){
                    connection = dbConnect();
                    stmt = connection.prepareStatement("UPDATE COMPTES_UTILISATEUR SET SOMME_REMBOURSEE=SOMME_DUE WHERE ID_UTILISATEUR=?");
                    stmt.setInt(1,currentUser);
                    rset = stmt.executeQuery();
                    if(!rset.next()){
                        System.out.println("Erreur");
                    }
                    connection.commit();
                    connection.close();
                    System.out.println("Dettes remboursées!");
                    
                }else if(choix==2){
                    if(due!=rembour){
                        System.out.println("Vous n'avez pas remboursé toutes vos dettes vous ne pouvez pas remboursé votre dette");
                    }else{
                        connection = dbConnect();
                        stmt = connection.prepareStatement("DELETE FROM Membre WHERE id_utilisateur=?");
                        stmt.setInt(1,currentUser);
                        rset = stmt.executeQuery();
                        if(!rset.next()){
                            System.out.println("Erreur");
                        }
                        connection.commit();
                        connection.close();
                        return false;
                    }

                }else if(choix==3){
                    return true;
                }else{
                    System.out.println("Mauvaise commande");
                }
            }
            


        }catch(SQLException e){
            e.printStackTrace();
            return false;
        }
        return true;
    

    }


    private static void dumpResultSet(ResultSet rset) throws SQLException {
        ResultSetMetaData rsetmd = rset.getMetaData();
        int i = rsetmd.getColumnCount();
        for (int k=1;k<=i;k++)
            System.out.print(rsetmd.getColumnName(k) + "\t");
            System.out.println();
            while (rset.next()){
                for (int j = 1; j <= i; j++) {
                    System.out.print(rset.getString(j) + "\t");
                }
                System.out.println();
            }
        }
    
    private static Connection dbConnect() throws SQLException{
        try{
            String url = "jdbc:oracle:thin:@oracle1.ensimag.fr:1521:oracle1";
            String user = "benoitad";
            String passwrd = "benoitad";
            Connection connection = DriverManager.getConnection(url, user, passwrd);
            connection.setAutoCommit(false);
            return connection;
        }catch(SQLException e){
            throw e;
        }
    }

    private static boolean usrConnect(String usr,String pass){
        try{
            Connection connection = dbConnect();
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM Membre WHERE mail_compte=? AND mot_de_passe=?");
            statement.setString(1,usr);
            statement.setString(2, pass);
            ResultSet rset = statement.executeQuery();
            if(rset.next()){
                currentUser = rset.getInt(1);
                statement= connection.prepareStatement("SELECT * FROM Jointure_adherents WHERE id_utilisateur=?");
                statement.setInt(1,currentUser);
                rset = statement.executeQuery();
                if(rset.next()){
                    isAdherent = true;
                }
                statement.close();
                return true;
            }else{
                statement.close();
                return false;
            }
        }catch(SQLException e){
            e.printStackTrace();
            return false;
        }
    }

    private static void Services(){
        boolean running = true;
        Scanner scanner = new Scanner(System.in);
        while(running){
            int choix;
            if(isAdherent){
                System.out.println("Veuillez sélectionner une action : \n1.Parcourir les refuges\n2.Parcourir les formations\n" + //
                    "3.Parcourir le matériel de location\n4.Retour");
                choix = scanner.nextInt();
            }else{
                System.out.println("Veuillez sélectionner une action : \n1.Parcourir les refuges\n" + //
                    "2.Parcourir le matériel de location\n3.Retour");
                choix = scanner.nextInt();
                if(choix>1){
                    choix++;
                }
            }
            

            switch(choix){
                case 1:
                    dumpRefuge();
                    break;
                case 2:
                    dumpFormation(0);
                    break;
                case 3:
                    dumpMateriel(0);
                    break;
                case 4: 
                    running=false;
                    break;
                default:
                    System.out.println("Veuillez choisir une option valide");
                    break;
            }
        }  
    }

    private static void dumpRefuge(){
        try{
            ArrayList<String> refuges = new ArrayList<String>();
            boolean running = true;
                while(running){
                Connection connection = dbConnect();
                Statement stmt = connection.createStatement();
                ResultSet rset = stmt.executeQuery("SELECT nom_refuge,places_nuit,date_ouverture,date_fermeture,mail_refuge FROM Refuge ORDER BY places_nuit ASC, date_ouverture ASC");
                String[] columnsNames = {"Nom","Places disponibles","Ouverture","Fermeture"};
                System.out.println("0.Filtres");
                for (int k=0;k<4;k++){
                    System.out.print(String.format("%-32s",columnsNames[k]));
                }
                System.out.println();
                int line = 1;
                while (rset.next()){
                    System.out.print(String.format("%-32s",line+"."+rset.getString(1)));
                    refuges.add(rset.getString(5));
                    System.out.print(String.format("%-32s",rset.getInt(2)));
                    System.out.print(String.format("%-32s",rset.getDate(3)));
                    System.out.println(String.format("%-32s",rset.getDate(4)));
                    line++;
                }
                System.out.println(line+".Retour");

                connection.close();

                Scanner scanner = new Scanner(System.in);

            
                int choix = scanner.nextInt();
                if(choix == 0){

                }else if(choix<line){
                    refugeDetails(refuges.get(choix-1));
                }else if(choix==line){
                    running=false;
                }else{
                    System.out.println("Mauvaise commande");
                }
            }
        }catch(SQLException e){
            e.printStackTrace();
            System.exit(0);
        }
        
    }

    private static String getPhone(String refuge){
        try{
            Connection connection = dbConnect();
            PreparedStatement stmt = connection.prepareStatement("SELECT * FROM REFUGE_TELEPHONE WHERE mail_refuge=?");
            stmt.setString(1,refuge);
            ResultSet rset = stmt.executeQuery();
            if(rset.next()){
                String res = rset.getString(2);
                connection.close();
                return res;
            }else{
                connection.close();
                return null;
            }

        }catch(SQLException e){
            e.printStackTrace();
            System.exit(0);
        }
        return null;
        
    }

    private static LinkedList<String> getPaiment(String refuge){
        LinkedList<String> paiment = new LinkedList<String>();
        try{
            Connection connection = dbConnect();
            PreparedStatement stmt = connection.prepareStatement("SELECT * FROM REFUGE_PAIEMENT WHERE mail_refuge=?");
            stmt.setString(1,refuge);
            ResultSet rset = stmt.executeQuery();
            while(rset.next()){
                paiment.add(rset.getString(2));
            }
            connection.close();
            return paiment;

        }catch(SQLException e){
            e.printStackTrace();
            System.exit(0);
        }
        return paiment;

    }

    private static void refugeDetails(String refuge){
        try{
            boolean running = true;

            while(running){

                Connection connection = dbConnect();
                PreparedStatement stmt = connection.prepareStatement("SELECT * FROM Refuge WHERE mail_refuge=?");
                stmt.setString(1, refuge);
                ResultSet rset = stmt.executeQuery();
                if(!rset.next()){
                    return;
                }

                String phone = getPhone(refuge);
                if(phone==null){
                    phone="Nan";
                }

                LinkedList<String> paiment = getPaiment(refuge);

                System.out.println("-----------------------------------------------\n"+String.format("%32s",rset.getString(2)+"\n"));

                System.out.println("Description : "+rset.getString(8)+"\n");

                System.out.print(String.format("%-32s","Lieu"));
                System.out.print(String.format("%-32s","Ouverture"));
                System.out.println(String.format("%-32s","Fermeture"));

                System.out.print(String.format("%-32s",rset.getString(3)));
                System.out.print(String.format("%-32s",rset.getDate(4)));
                System.out.println(String.format("%-32s",rset.getDate(5)));
                System.out.println();

                System.out.print(String.format("%-16s","Places nuité"));
                System.out.print(String.format("%-16s","Prix nuité"));
                System.out.println(String.format("%-16s","Places repas"));

                int places = rset.getInt(6);
                System.out.print(String.format("%-16s",rset.getInt(6)));
                System.out.print(String.format("%-16s",rset.getInt(9)));
                System.out.println(String.format("%-16s",rset.getInt(7))+"\n");

                System.out.print("Moyen de paiment : ");
                if(paiment.isEmpty()){
                    System.out.println("NaN\n");
                }else{
                    for(String moyen : paiment){
                        System.out.print(moyen+"; ");
                    }
                    System.out.println("\n");
                }

                System.out.println("Contact : "+rset.getString(1)+"   Téléphone : "+phone+"\n--------------------------------------------------");

                System.out.println("1.Réserver une nuité\n2.retour\n ");

                connection.close();

                Scanner scanner = new Scanner(System.in);
                int choix = scanner.nextInt();

                if(choix==1){
                    makeRefugeReservation(refuge,places);
                }else if(choix==2){
                    running=false;
                }else{
                    System.out.println("Mauvaise commande");
                }
            }
        }catch(SQLException e){
            e.printStackTrace();
            System.exit(0);
        }

    }

    private static int getPlacesTaken(String refuge,java.util.Date date){
        try{
            Connection connection = dbConnect();
            Calendar cal = Calendar.getInstance();
            cal.setTime(date);
            Date sqlDate = new Date(date.getTime());
            
            //SELECT count(id_reservation_ref) FROM Refuge_reservation WHERE mail_refuge='refugedes2aples@gmail.com' AND EXTRACT(YEAR FROM date_reservation_ref)=2023 AND date_reservation_ref<to_date('02-02-2023','DD-MM-YYYY') AND (date_reservation_ref+nb_nuits)>to_date('02-02-2023','DD-MM-YYYY')
            PreparedStatement stmt = connection.prepareStatement("SELECT count(id_reservation_ref) FROM Refuge_reservation WHERE mail_refuge=? AND EXTRACT(YEAR FROM date_reservation_ref)=? AND date_reservation_ref<? AND (date_reservation_ref+nb_nuits)>?");
            stmt.setString(1,refuge);
            stmt.setInt(2,cal.get(Calendar.YEAR));
            stmt.setDate(3,sqlDate);
            stmt.setDate(4,sqlDate);
            ResultSet rset = stmt.executeQuery();
            if(rset.next()){
                int res =rset.getInt(1);
                connection.close();
                return res;
            }else{
                connection.close();
                return 0;
            }

            
        }catch(SQLException e){
            e.printStackTrace();
            System.exit(0);
        }
        return 0;
    }

    private static int getPrixrepas(String refuge,String type_repas){
        try{
            Connection connection = dbConnect();
            PreparedStatement stmt = connection.prepareStatement("SELECT PRIX_REPAS FROM REFUGE_PRIX_REPAS WHERE mail_refuge=? AND type_repas=?");
            stmt.setString(1, refuge);
            stmt.setString(2, type_repas);
            ResultSet rset = stmt.executeQuery();
            if(rset.next()){
                int res = rset.getInt(1);
                connection.close();
                return res;
            }else{
                connection.close();
                return 0;
            }

        }catch(SQLException e){
            e.printStackTrace();
            System.exit(0);
        }
        return 0;
    }


    private static void makeRefugeReservation(String refuge, int nbPlace){

        String[] repasPos = {"dejeuner", "casse-croute", "diner", "souper"};
        try{
            
            boolean running = true;
            Connection connection = dbConnect();
            PreparedStatement stmt = connection.prepareStatement("SELECT date_ouverture,date_fermeture FROM Refuge WHERE mail_refuge=?");
            stmt.setString(1,refuge);
            ResultSet rset = stmt.executeQuery();
            if(!rset.next()){
                System.out.println("Erreur ce refuge n'existe pas");
                return;
            }
            Date debut = rset.getDate(1);
            Date fin = rset.getDate(2);
            Calendar cldeb = Calendar.getInstance();
            Calendar clfin = Calendar.getInstance();
            cldeb.setTime(debut);
            clfin.setTime(fin);


            Scanner scanner = new Scanner(System.in);
            
            SimpleDateFormat strFormat = new SimpleDateFormat("dd/MM/yyyy");
            java.util.Date entry = new java.util.Date(0);
            String input;
            int nuite = 0;
            int heure = 0;
            int totRepas = 0;
            int prix = 0;
            Calendar cur = Calendar.getInstance();
            LocalDateTime now = LocalDateTime.now();
            Calendar tempCal = Calendar.getInstance();
            int[][] listRepas = new int[nuite][3];

            while(running){
                System.out.println("rentrez une date de réservation au format DD/MM/YYYY");
                input = scanner.nextLine();
                System.out.println("A quelle heure comptez vous arriver");
                heure = scanner.nextInt();
                System.out.println("Combien de jours voulez vous rester?");
                nuite = scanner.nextInt();
                try{
                    entry = strFormat.parse(input);
                    cur.setTime(entry);
                    tempCal.setTime(entry);
                    tempCal.add(Calendar.YEAR,nuite);
                    
                }catch(ParseException e){
                    e.printStackTrace();
                    return;
                }
                if(entry.getTime()<(now.getLong(ChronoField.EPOCH_DAY)-1)*1000*3600*24){
                    System.out.println("Renseignez une date future");
                }

                else if(entry.getTime()<debut.getTime() || entry.getTime()>fin.getTime()){
                    System.out.println("Veuillez entrer une date pendant la période d'ouverture du refuge");

                }else if(tempCal.compareTo(clfin)==-1){
                    System.out.println("Réservation trop longue");
                }else{
                    boolean validResa = true;
                    for(int i =0;i<nuite;i++){
                        if(nbPlace-getPlacesTaken(refuge, entry)<=0){
                            validResa=false;
                        }
                    }
                    if(validResa){
                        prix = 0;
                        totRepas = 0;
                        listRepas = new int[nuite][3];
                        int nb_repas;
                        int choix;
                        for(int i=0;i<nuite;i++){
                            nb_repas = 0;
                            choix=0;
                            while(nb_repas<3 && choix!=5){
                                System.out.println("Choisissez un repas à ajouter à vos repas pour la journée "+(i+1)+"/"+(nuite)+"  repas :"+nb_repas+"/3");
                                System.out.println("1.déjeuner");
                                System.out.println("2.casse-croute");
                                System.out.println("3.diner");
                                System.out.println("4.souper");
                                System.out.println("5.aucun");

                                choix = scanner.nextInt();

                                if(choix==5){
                                    continue;
                                }
                                boolean validChoice = true;
                                for(int u=0;u<3;u++){
                                    if(choix==listRepas[i][u]){
                                        validChoice=false;
                                        System.out.println("Vous avez déja pris ce type de repas pour ce jour la!");
                                    }
                                }
                                if(validChoice &&(choix>0 || choix<5)){
                                    listRepas[i][nb_repas] = choix;
                                    nb_repas++;
                                    totRepas++;
                                    prix+=getPrixrepas(refuge, repasPos[choix-1]);
                                }
                                

                            }

                            
                        }
                        for(int i =0;i<nuite;i++){
                        if(nbPlace-getPlacesTaken(refuge, entry)<=0){
                            validResa=false;
                        }
                        if(validResa){
                            running=false;
                        }else{
                            System.out.println("Votre réservation a été prise par quelqu'un d'autre");
                        }
                    }

                    }else{
                        System.out.println("Il n'y a pas assez de place sur la durée pour votre réservation");
                    }
                }
            }
            System.out.println("Valider la réservation?\n1.Oui\n2.Non");
            int choix = scanner.nextInt();
            while(choix!=1){
                if(choix==2){
                    return;
                }
            }
            connection = dbConnect();
            stmt = connection.prepareStatement("INSERT INTO REFUGE_RESERVATION VALUES(?,seq_id_reservation_ref.NEXTVAL,?,?,?,?,?,?)");
            stmt.setString(1, refuge);
            stmt.setDate(2,new Date(entry.getTime()));
            stmt.setInt(3,heure);
            stmt.setInt(4,currentUser);
            stmt.setInt(5,nuite);
            stmt.setInt(6,totRepas);
            stmt.setInt(7,prix);
            rset = stmt.executeQuery();
            if(!rset.next()){
                System.out.println("Erreur");
            }

            stmt = connection.prepareStatement("UPDATE COMPTES_UTILISATEUR SET SOMME_DUE=SOMME_DUE+? WHERE ID_UTILISATEUR=?");
            stmt.setInt(1,prix);
            stmt.setInt(2, currentUser);
            rset = stmt.executeQuery();
            if(!rset.next()){
                System.out.println("Erreur");
            }

            for(int i = 0;i<nuite;i++){
                for(int j=0;j<3;j++){
                    if(listRepas[i][j]!=0){
                        stmt = connection.prepareStatement("INSERT INTO REPAS_RESERVES VALUES(?,seq_id_reservation_ref.CURRVAL,?,?)");
                        stmt.setString(1, refuge);
                        stmt.setString(2, repasPos[listRepas[i][j]-1]);
                        stmt.setInt(3,i+1);
                        stmt.executeQuery();
                    }

                }
            }
            connection.commit();
            connection.close();
            System.out.println("\nRefuge réservé avec succès!\n");
        
            
        }catch(SQLException e){
            e.printStackTrace();
            System.exit(0);
        }
        

    }

    private static void dumpFormation(int filtre){
        try{
            ArrayList<String> formations = new ArrayList<String>();
            Connection connection = dbConnect();
            Statement stmt = connection.createStatement();
            String request = "SELECT id_formation, nom_formation, date_formation, duree_formation, places_formation, prix_formation FROM Formation" + 
                ((filtre == 1) ? " ORDER BY date_formation ASC, nom_formation ASC" : "");
            ResultSet rset = stmt.executeQuery(request);
            
            String[] columnsNames = {"Nom", "Date debut", "Durée", "Places disponibles", "Prix"};
            
            if(filtre == 0) System.out.println("\n"+"0.Filtrer"+"\n");
            
            for (int k=0;k<5;k++){
                System.out.print(String.format((k == 0) ? "%-36s" : "%-25s", columnsNames[k]));
            }
            System.out.println();
            int line = 1;
            while (rset.next()){
                formations.add(rset.getString(1)); // on conserve les id_formations pour pouvoir afficher ensuite les details d'une formation
                System.out.print(String.format("%-36s", line+"."+rset.getString(2)));
                System.out.print(String.format("%-25s", rset.getDate(3)));
                System.out.print(String.format("%-25s", rset.getInt(4)));
                System.out.print(String.format("%-25s", rset.getInt(5)));
                System.out.println(String.format("%-25s", rset.getInt(6)));
                line++;
            }
            System.out.println(line+".Retour");

            connection.close();

            boolean running = true;
            Scanner scanner = new Scanner(System.in);

            while(running){
                int choix = scanner.nextInt();
                if(choix == 0){
                    // on filtre les formations selon les criteres demandés
                    if(filtre == 0){
                        dumpFormation(1);
                        running = false; // on sort du dumpFormation actuel
                    }
                    else{
                        System.out.println("Mauvaise commande");
                    }
                }else if(choix<line){
                    formationDetails(formations.get(choix - 1), filtre);
                    running = false;
                }else if(choix==line){
                    running=false;
                }else{
                    System.out.println("Mauvaise commande");
                }
            }

            stmt.close();

        }catch(SQLException e){
            e.printStackTrace();
            System.exit(0);
        }
    }

    private static void formationDetails(String id_formation, int filtre){
        try{
            Connection connection = dbConnect();
            PreparedStatement stmt1 = connection.prepareStatement("SELECT nom_formation, description_formation, date_formation, duree_formation, places_formation, prix_formation FROM Formation WHERE id_formation=?");
            stmt1.setString(1,id_formation);
            ResultSet rset1 = stmt1.executeQuery();
            if(rset1.next()){
                System.out.println("---------------------------------------------------------------------------------------------------\n");
                System.out.print("Nom : "+rset1.getString(1) + "\n");
                getActivites(id_formation); // Affiche les activites de la formation
                System.out.print("Description : "+rset1.getString(2) + "\n");
                System.out.print("Date debut : "+rset1.getDate(3) + "\n");
                System.out.print("Durée : "+rset1.getInt(4) + "\n");
                System.out.print("Places disponibles : "+rset1.getInt(5) + "\n");   int nbPlaces = rset1.getInt(5);
                System.out.print("Prix : "+rset1.getInt(6) + "\n");
                System.out.println("\n---------------------------------------------------------------------------------------------------");

                System.out.println("\n1.Reserver cette formation\n2.Retour");
                
                boolean running1 = true;
                Scanner scanner1 = new Scanner(System.in);
                
                while(running1){
                    int choix1 = scanner1.nextInt();
                    if(choix1 == 1){
                        PreparedStatement stmt3 = connection.prepareStatement("SELECT id_adherent FROM Jointure_Adherents WHERE id_utilisateur=?");
                        stmt3.setInt(1,currentUser);
                        ResultSet rset3 = stmt3.executeQuery();
                        if(rset3.next()){
                            int id_adherent = rset3.getInt(1); // on recupere l'id_adherent
                            PreparedStatement stmt4 = connection.prepareStatement("SELECT id_adherent FROM Formation_reservation WHERE id_formation=? AND id_adherent=?");
                            stmt4.setString(1,id_formation);
                            stmt4.setInt(2,id_adherent);
                            ResultSet rset4 = stmt4.executeQuery();
                            if(rset4.next()){
                                System.out.println("\nVous avez déja reservé cette formation");
                            }
                            else{
                                if(nbPlaces>0){
                                    reserverFormation(id_formation, id_adherent);
                                }
                                else{
                                    ajoutListeAttente(id_formation, id_adherent);
                                }    
                            }
                            stmt4.close();  
                        }
                        else{
                            System.out.println("Vous n'etes pas adhérents, vous ne pouvez pas reserver cette formation");
                        }
                        stmt3.close(); 
                        running1 = false; //
                    }
                    else if(choix1 == 2){
                        running1 = false;
                    }
                    else{
                        System.out.println("Mauvaise commande");
                    }
                }
            }else{
                System.out.println("Mauvaise commande");
            }

            stmt1.close();
            dumpFormation(filtre); // on retourne a la liste des formations disponibles
            connection.close();
        }catch(SQLException e){
            e.printStackTrace();
            System.exit(0);
        }
    }

    private static void getActivites(String id_formation){
        try{
            Connection connection = dbConnect();
            PreparedStatement stmt2 = connection.prepareStatement("SELECT nom_act FROM Formation_activites WHERE id_formation=?");
            stmt2.setString(1,id_formation);
            ResultSet rset2 = stmt2.executeQuery();
            String activites = "";
            while(rset2.next()){
                activites += (!(activites.equals("")) ? ", " : "")+rset2.getString(1);                            
            }
            System.out.print("Activités : [ "+ activites + " ]\n");

            stmt2.close();
            connection.close();
        }catch(SQLException e){
            e.printStackTrace();
            System.exit(0);
        }
    }

    private static void reserverFormation(String id_formation, int id_adherent){
        int places = -1; // nombre de places disponibles juste avant l'exécution du UPDATE
        try{
            Connection connection = dbConnect();
            connection.setTransactionIsolation(Connection.TRANSACTION_SERIALIZABLE); // niveau d'isolation serializable
            PreparedStatement stmt5 = connection.prepareStatement("INSERT INTO Formation_reservation VALUES (?, ?, ?)");
            stmt5.setString(1,id_formation); // c'est id_formation
            stmt5.setInt(2,id_adherent);
            stmt5.setInt(3,0);
            ResultSet rset5 = stmt5.executeQuery();
            if(rset5.next()){
                PreparedStatement stmt6 = connection.prepareStatement("UPDATE Formation SET places_formation =? WHERE id_formation=?");
                places = getNbPlaces(id_formation);
                stmt6.setInt(1, (places - 1));
                stmt6.setString(2,id_formation);
                ResultSet rset6 = stmt6.executeQuery();
                if(rset6.next()){
                    System.out.println("\nFormation reservée avec succes");
                    connection.commit();
                }
                else{
                    System.out.println("Erreur de mise a jour");
                }
                stmt6.close();
            }
            else{
                System.out.println("Erreur insertion");
            }

            stmt5.close();
            connection.close();
        }catch(SQLException e){
            if(places == 0){
                // il n'y a plus de place pour la formation
                ajoutListeAttente(id_formation, id_adherent);
            }
            else{
                e.printStackTrace();
                System.exit(0);
            }
        }
    }

    public static int getNbPlaces(String id_formation){
        int nb_places = 0;
        try{
            Connection connection = dbConnect();
            PreparedStatement stmt = connection.prepareStatement("SELECT places_formation FROM Formation WHERE id_formation=?");
            stmt.setString(1, id_formation);
            ResultSet rset = stmt.executeQuery();

            if(rset.next()){
                nb_places = rset.getInt(1);
            }
            
            stmt.close();
            connection.close();
        }catch(SQLException e){
            e.printStackTrace();
            System.exit(0);
        }

        return nb_places;
    }

    public static void ajoutListeAttente(String id_formation, int id_adherent){
        try{
            Connection connection = dbConnect();
            connection.setTransactionIsolation(Connection.TRANSACTION_SERIALIZABLE);
            PreparedStatement stmt5 = connection.prepareStatement("INSERT INTO Formation_reservation VALUES (?, ?, ((SELECT MAX (rang_attente) FROM Formation_reservation) + 1))");
            stmt5.setString(1,id_formation); // c'est id_formation
            stmt5.setInt(2,id_adherent);
            
            ResultSet rset5 = stmt5.executeQuery();
            if(rset5.next()){
                System.out.println("\nVous avez été ajouté a la liste d'attente");
                connection.commit();
            }
            else{
                System.out.println("Erreur insertion");
            }

            stmt5.close();
            connection.close();
        }catch(SQLException e){
            e.printStackTrace();
            System.exit(0);
        }
    }

    private static boolean isConformEmail(String email){ // retourne true si email est conforme
        String regexPattern = "^[a-zA-Z0-9_!#$%&'*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$"; // norme RFC-5322
        return Pattern.compile(regexPattern).matcher(email).matches();
    }

    private static boolean isConformPassword(String password){ // retourne true si le password est de taille >= 8
        return (password.length() > 7);
    }

    private static void dumpMateriel(int filtre){ 
        try{
            ArrayList<IdMateriel> materiels = new ArrayList<IdMateriel>();
            Connection connection = dbConnect();
            Statement stmt = connection.createStatement();
            String request = "SELECT marque, modele, date_achat, nb_pieces, categorie, nom_act FROM Materiel NATURAL JOIN Materiel_activites";
            // 0 - pas de filtre
            if(filtre == 1){ // 1- trier par categorie
                request += " ORDER BY categorie ASC";
            }
            else if(filtre == 2){ // 2- trier par activite
                request += " ORDER BY nom_act ASC";
            }

            ResultSet rset = stmt.executeQuery(request);
            
            String[] columnsNames = {"Marque", "Modele", "Date achat", "Pieces disponibles", "Categorie", "Activite"};
            
            System.out.println("\n"+"0.Filtrer par Categorie\n1.Filtrer par Activite"+"\n");
            
            for (int k=0;k<6;k++){
                System.out.print(String.format("%-25s", columnsNames[k]));
            }

            System.out.println();
            int line = 2;

            while (rset.next()){
                materiels.add(new IdMateriel(rset.getString(1), rset.getString(2), rset.getDate(3))); // on conserve la marque, le modele et la date d'achat du materiel
                System.out.print(String.format("%-25s", line+"."+rset.getString(1)));
                System.out.print(String.format("%-25s", rset.getString(2)));
                System.out.print(String.format("%-25s", rset.getDate(3)));
                System.out.print(String.format("%-25s", rset.getInt(4)));
                System.out.print(String.format("%-25s", rset.getString(5)));
                System.out.println(String.format("%-25s", rset.getString(6)));
                line++;
            }
            System.out.println(line+".Retour");

            connection.close();

            boolean running = true;
            Scanner scanner = new Scanner(System.in);

            while(running){
                int choix = scanner.nextInt();
                if(choix == 0){
                    // on filtre les formations par categorie
                    dumpMateriel(1);
                    running = false; // on sort du dumpFormation actuel
                }
                else if(choix == 1){
                    // on filtre les formations par activite
                    dumpMateriel(2);
                    running = false;
                }
                else if(choix<line){
                    materielDetails(materiels.get(choix - 2), filtre); // afficher les details du materiel
                    running = false;
                }else if(choix==line){
                    running=false;
                }else{
                    System.out.println("Mauvaise commande");
                }
            }

            stmt.close();

        }catch(SQLException e){
            e.printStackTrace();
            System.exit(0);
        }
    }

    private static void materielDetails(IdMateriel  id_materiel, int filtre){
        try{
            Connection connection = dbConnect();
            PreparedStatement stmt1 = connection.prepareStatement("SELECT marque, modele, date_achat, nb_pieces, categorie, nom_act FROM Materiel NATURAL JOIN Materiel_activites WHERE marque = ? and modele = ? and date_achat = ?");
            
            stmt1.setString(1,id_materiel.marque);
            stmt1.setString(2,id_materiel.modele);
            stmt1.setDate(3,id_materiel.date_achat);

            ResultSet rset1 = stmt1.executeQuery();

            if(rset1.next()){
                System.out.println("---------------------------------------------------------------------------------------------------\n");
                System.out.print("Marque : "+rset1.getString(1) + "\n");
                System.out.print("Modele : "+rset1.getString(2) + "\n");
                System.out.print("Date d'achat : "+rset1.getDate(3) + "\n");
                getDescriptionMateriel(id_materiel);
                System.out.print("Nombre de pieces disponibles : "+rset1.getInt(4) + "\n"); int nb_pieces = rset1.getInt(4);
                System.out.print("Categorie : "+rset1.getString(5) + "\n");
                System.out.print("Activité : "+rset1.getString(6) + "\n");
                System.out.println("\n---------------------------------------------------------------------------------------------------");
                
                System.out.println("\n1.Reserver ce materiel\n2.Rendre ce materiel\n3.Retour");

                boolean running1 = true;
                Scanner scanner1 = new Scanner(System.in);
                
                while(running1){
                    int choix1 = scanner1.nextInt();
                    if(choix1 == 1){
                        PreparedStatement stmt3 = connection.prepareStatement("SELECT id_adherent FROM Jointure_Adherents WHERE id_utilisateur=?");
                        stmt3.setInt(1,currentUser);
                        ResultSet rset3 = stmt3.executeQuery();
                        if(rset3.next()){
                            int id_adherent = rset3.getInt(1); // on recupere l'id_adherent
                            if(nb_pieces>0){
                                reserverMateriel(id_materiel, id_adherent, nb_pieces);
                            }
                            else{
                                System.out.println("Desolé, mais ce materiel est en rupture de stock");
                            }  
                        }
                        else{
                            System.out.println("Désolé, mais seul les adherents peuvent reserver du materiel");
                        }
                        stmt3.close(); 
                        running1 = false;
                    }
                    else if(choix1 == 2){
                        rendreMateriel();
                        running1 = false;
                    }
                    else if(choix1 == 3){
                        running1 = false;
                    }
                    else{
                        System.out.println("Mauvaise commande");
                    }
                }
            }else{
                System.out.println("Mauvaise commande");
            }

            stmt1.close();
            dumpMateriel(filtre); // on retourne a la liste des materiels disponibles
            connection.close();
            
        }catch(SQLException e){
            e.printStackTrace();
            System.exit(0);
        }
    }

    public static void getDescriptionMateriel(IdMateriel id_materiel){
        try{
            Connection connection = dbConnect();
            PreparedStatement stmt = connection.prepareStatement("SELECT description_materiel FROM Materiel_description WHERE marque=? AND modele=? AND date_achat=?");
            stmt.setString(1, id_materiel.marque);
            stmt.setString(2, id_materiel.modele);
            stmt.setDate(3, id_materiel.date_achat);
            ResultSet rset = stmt.executeQuery();

            if(rset.next()){
                System.out.println("Description : "+rset.getString(1));
            }
            
            stmt.close();
            connection.close();
        }catch(SQLException e){
            e.printStackTrace();
            System.exit(0);
        }
    }

    public static void reserverMateriel(IdMateriel id_materiel, int id_adherent, int nb_pieces){
        try{
            System.out.println("Combien de pieces voulez-vous reserver (nous disposons de "+nb_pieces+" pieces) ?");
            Scanner scanner = new Scanner(System.in);
            int nb_pieces_resa = scanner.nextInt();
            while(nb_pieces_resa > nb_pieces || nb_pieces_resa <= 0){
                if(nb_pieces_resa <= 0){
                    System.out.println("Svp veuillez choisir un nombre de pieces strictement positif");
                }
                else{
                    System.out.println("Svp veuillez choisir au plus "+nb_pieces+" pieces");
                }
                nb_pieces_resa = scanner.nextInt();
            }

            // on lui demande la date de recup
            Date date_recup = new Date(new java.util.Date().getTime());
            long timestamp_recup = date_recup.getTime();
            Scanner scanner1 = new Scanner(System.in);
            boolean recup = false;
            String chaine = "";
            while(!recup){
                System.out.println("Quand souhaitez-vous recupperer le materiel (YYYY-MM-JJ) ?");
                chaine = scanner1.nextLine();
                if(!isConformDate(chaine)){
                    System.out.println("Ce que vous avez saisi ne correspond pas au format (YYYY-MM-JJ)");
                    continue;
                }
                date_recup = Date.valueOf(chaine);
                timestamp_recup = date_recup.getTime();
                if(timestamp_recup < new Date(new java.util.Date().getTime()).getTime() && 
                    !date_recup.toString().equals(new Date(new java.util.Date().getTime()).toString())
                ){
                    System.out.println("Veuillez choisir une date ulterieur");
                }
                else{
                    recup = true;
                }
            }

            // on lui demande la date de rendu envisagée (ne doit pas depasser 14 jours apres la date de recup)
            Date date_rendu = new Date(new java.util.Date().getTime()); // on l'initialise a la date du jour
            long timestamp_rendu;
            long differenceInDays;
            Scanner scanner2 = new Scanner(System.in);
            boolean difference = false;
            String chaine1 = "";
            while(!difference){
                System.out.println("Quand souhaitez-vous rendre le materiel (YYYY-MM-JJ) ?");
                chaine1 = scanner2.nextLine();
                if(!isConformDate(chaine1)){
                    System.out.println("Ce que vous avez saisi ne correspond pas au format (YYYY-MM-JJ)");
                    continue;
                }
                date_rendu = Date.valueOf(chaine1);
                timestamp_rendu = date_rendu.getTime();
                differenceInDays = (timestamp_rendu - timestamp_recup) / (24 * 60 * 60 * 1000);
                if(differenceInDays < 0){
                    System.out.println("Veuillez choisir une date ulterieur");
                }
                else if(differenceInDays > 14){
                    System.out.println("Veuillez choisir une date anterieur, la reservation ne peut pas aller au dela de 14 jours");
                }
                else{
                    difference = true;
                }
            }

            // connection a la BD
            Connection connection = dbConnect();
            connection.setTransactionIsolation(Connection.TRANSACTION_SERIALIZABLE);
            PreparedStatement stmt = connection.prepareStatement("INSERT INTO Materiel_reservation VALUES (?, ?, ?, ?, ?, ?, ?, ?)");
            stmt.setString(1,id_materiel.marque);
            stmt.setString(2,id_materiel.modele);
            stmt.setDate(3,id_materiel.date_achat);
            stmt.setInt(4,getMaxIdResaMateriel() + 1); // id_resa_materiel
            stmt.setInt(5,id_adherent);
            stmt.setInt(6,nb_pieces_resa); // le nombre de pieces reservées
            stmt.setDate(7,date_recup); // date a laquelle il reserve le materiel
            stmt.setDate(8,date_rendu); // date a laquelle il doit rendre le materiel
            ResultSet rset = stmt.executeQuery();

            if(rset.next()){
                PreparedStatement stmt1 = connection.prepareStatement("UPDATE Materiel SET nb_pieces =? WHERE marque=? AND modele=? AND date_achat=?"); 
                stmt1.setInt(1, (getNbPieces(id_materiel.marque, id_materiel.modele, id_materiel.date_achat) - nb_pieces_resa));
                stmt1.setString(2,id_materiel.marque);
                stmt1.setString(3,id_materiel.modele);
                stmt1.setDate(4,id_materiel.date_achat);
                ResultSet rset1 = stmt1.executeQuery();
                if(rset1.next()){
                    System.out.println("\nMateriel reservé avec succes");
                    connection.commit();
                }
                else{
                    System.out.println("Erreur de mise a jour du nombre de pieces");
                }
                stmt1.close();
            }
            else{
                System.out.println("Erreur insertion de la reservation du materiel");
            }

            stmt.close();
            connection.close();
        }catch(SQLException e){
            System.out.println("\nLa réservation a échouée\n");
        }
    }

    private static boolean isConformDate(String date){ // retourne true si date est conforme
        String regexPattern = "^\\d{4}-\\d{2}-\\d{2}$";
        return Pattern.compile(regexPattern).matcher(date).matches();
    }

    public static int getMaxIdResaMateriel(){
        int max = 0;
        try{
            Connection connection = dbConnect();
            Statement stmt = connection.createStatement();
            ResultSet rset = stmt.executeQuery("SELECT MAX (id_resa_materiel) FROM Materiel_reservation");
            if(rset.next()){
                max = rset.getInt(1);
            }
            
            stmt.close();
            connection.close();
        }catch(SQLException e){
            e.printStackTrace();
            System.exit(0);
        }

        return max;
    }

    public static int getNbPieces(String marque, String modele, Date date_achat){
        int nb_pieces = 0;
        try{
            Connection connection = dbConnect();
            PreparedStatement stmt = connection.prepareStatement("SELECT nb_pieces FROM Materiel WHERE marque=? AND modele=? AND date_achat=?");
            stmt.setString(1, marque);
            stmt.setString(2, modele);
            stmt.setDate(3, date_achat);
            ResultSet rset = stmt.executeQuery();

            if(rset.next()){
                nb_pieces = rset.getInt(1);
            }
            
            stmt.close();
            connection.close();
        }catch(SQLException e){
            e.printStackTrace();
            System.exit(0);
        }

        return nb_pieces;
    }

    public static void rendreMateriel(){
        // ici on rend le materiel
        //System.out.println("L'utilisateur souhaite rendre le materiel");
    }
    
}

class IdMateriel{
    protected String marque;
    protected String modele;
    protected Date date_achat;
    public IdMateriel(String marque, String modele, Date date_achat){
        this.marque = marque;
        this.modele = modele;
        this.date_achat = date_achat;
    }
}