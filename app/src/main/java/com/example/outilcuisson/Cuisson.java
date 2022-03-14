package com.example.outilcuisson;

import java.io.Serializable;

public class Cuisson implements Serializable {

    /**
     * Valeur de la chaîne qui représente une cuisson si l'une des
     * caractéristiques
     * de la cuisson est invalide
     */
    private static final String CHAINE_DEFAUT = "Information incohérente";

    /**
     * Température maximale pour la cuisson
     */
    public static final int TEMPERATURE_MAX = 300;

    /**
     * Valeur maximale pour le nombre d'heures d'une cuisson
     */
    public static final int HEURE_MAX = 9;

    /**
     * Nombre maximum de caractères pour le nom du plat
     */
    private static final int LG_MAX_PLAT = 18;

    private String plat;
    private int heure;
    private int minute;
    private int degree;

    public Cuisson(String plat, int heure, int minute, int degree) throws
                                                                   Exception {
        if (!platValide(plat) || !heureCuissonValide(heure)
            || !minuteCuissonValide(minute) || !temperatureValide(degree)) {
            throw new Exception(CHAINE_DEFAUT);
        }
        this.plat = plat;
        this.heure = heure;
        this.minute = minute;
        this.degree = degree;
    }

    /**
     * Détermine si un nom de plat est valide (non vide, au plus 20
     * caractères et ne
     * contient pas le caractère '|')
     *
     * @param nomPlat chaîne à testesr
     * @return un booléen égal à vrai ssi la chaîne à tester est valide
     */
    public static boolean platValide(String nomPlat) {
        return nomPlat.length() > 0 && nomPlat.length() <= LG_MAX_PLAT
               && !nomPlat.contains("|");
    }

    /**
     * Détermine si le nombre d'heures d'une durée de cuisson est valide
     * (comprise entre 0 et 9)
     *
     * @param heureCuisson heure à tester
     * @return un booléen égal à vrai ssi l'heure de la durée est valide
     */
    public static boolean heureCuissonValide(int heureCuisson) {
        return 0 <= heureCuisson && heureCuisson <= HEURE_MAX;
    }

    /**
     * Détermine si le nombre de minutes d'une durée de cuisson est valide
     * (comprise entre 0 et 59)
     *
     * @param minuteCuisson minute à tester
     * @return un booléen égal à vrai ssi le nombre de minutes de la durée
     * est valide
     */
    public static boolean minuteCuissonValide(int minuteCuisson) {
        return 0 <= minuteCuisson && minuteCuisson <= 59;
    }

    /**
     * Détermine si une température de cuisson est valide
     * (strictement positive et inférieure ou égale à TEMPERATURE_MAX)
     *
     * @param temperature temperature à tester
     * @return un booléen égal à vrai ssi la température est valide
     */
    public static boolean temperatureValide(int temperature) {
        return 0 < temperature && temperature <= TEMPERATURE_MAX;
    }

    public String getPlat() {
        return plat;
    }

    public int getHeure() {
        return heure;
    }

    public int getMinute() {
        return minute;
    }

    public int getDegree() {
        return degree;
    }

    @Override
    public String toString() {
        StringBuilder aRenvoyer = new StringBuilder();


        // on insère le nom du plat
        aRenvoyer.append(plat);
        aRenvoyer.append(chaineEspace(LG_MAX_PLAT - plat.length()));
        aRenvoyer.append(" | ");

        // on insère la durée
        aRenvoyer.append(String.valueOf(heure));
        aRenvoyer.append(" h ");
        if (minute < 10) {
            aRenvoyer.append("0");
        }
        aRenvoyer.append(String.valueOf(minute));
        aRenvoyer.append(" | ");

        // on insère la température
        aRenvoyer.append(String.format("%3d", degree));
        return aRenvoyer.toString();
    }

    /**
     * Extrait d'une chaîne le nom du plat. On suppose que la chaîne est
     * correctement
     * formatée dans le format de la description d'une cuisson gérée par
     * cette classe
     * (les 20 premiers caractères de la chaîne sont extraits)
     *
     * @param source chaîne source de l'extraction
     * @return une chaîne contenant le nom du plat ou CHAINE_DEFAUT si la chaîne
     * argument est trop courte
     */
    public static String extrairePlat(String source) {
        if (source.length() < LG_MAX_PLAT) {
            return CHAINE_DEFAUT;
        } else {
            return source.substring(0, LG_MAX_PLAT);
        }
    }

    /**
     * Extrait d'une chaîne la température de cuisson. On suppose que la chaîne
     * est correctement formatée dans le format de la description d'une cuisson
     * gérée par cette classe  (la température est présente sur les 3 derniers
     * caractères de la chaîne)
     *
     * @param source chaîne source de l'extraction
     * @return un entier égal à la température extraite ou bien -1 si un
     * problème inattendu a été rencontré avec le format de la chaîne
     */
    public static int extraireTemperature(String source) {
        int temperature;                // température extraite
        String
            chaineTemperature;       // température extraite en tant que chaîne


        try {
            chaineTemperature = source.substring(source.length() - 3,
                                                 source.length());
            temperature = Integer.parseInt(chaineTemperature);
        } catch (NumberFormatException | IndexOutOfBoundsException erreur) {

            /*
             *  erreur lors de l'extraction des 3 derniers caractère
             *  ou lors de la transformation en entier
             */
            temperature = -1;
        }
        return temperature;
    }

    /**
     * Renvoie le thermostat correspondant à la temperature argument
     * (celle-ci doit être inférieure à TEMPERATURE_MAX)
     *
     * @param temperature température à convertir
     * @return l'entier égal au thermostat ou -1 si la température est invalide
     */
    public static int thermostat(int temperature) {
        int aRenvoyer;          // valeur du thermostat à renvoyer
        if (temperature <= 0 || temperature > TEMPERATURE_MAX) {
            aRenvoyer = -1;
        } else {
            aRenvoyer = temperature / 30;
            if (temperature % 30 > 15) {
                aRenvoyer++;
            }
        }
        return aRenvoyer;
    }

    /**
     * Renvoie une chaîne constituée d'espaces. Le nombre d'espaces est indiqué
     * par le paramètre.
     *
     * @param nbEspace nombre d'espaces à placer dans la chaîne
     * @return une chaîne constituée du caractère espace
     */
    private static String chaineEspace(int nbEspace) {
        StringBuilder aRenvoyer = new StringBuilder();
        for (int i = 1; i <= nbEspace; i++) {
            aRenvoyer.append(" ");
        }
        return aRenvoyer.toString();
    }
}