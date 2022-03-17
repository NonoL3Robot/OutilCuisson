/*
 * Cuisson.java, 16/03/2022
 * IUT Rodez 2021-2022, INFO 2
 * pas de copyright, aucun droits
 */

package com.example.outilcuisson;

import java.io.Serializable;

/**
 * Classe qui définit une cuisson
 * Éléments serializable qui permettra d'être sauvegardé et chargé depuis un
 * fichier
 *
 * @author THIZY Alexandre
 * @author VABRE Lucàs
 * @author VILLENEUVE Noé
 */
public class Cuisson implements Serializable {

    /**
     * Valeur de la chaîne qui représente une cuisson si l'une des
     * caractéristiques de la cuisson est invalide
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
    private static final int LG_MAX_PLAT = 20;

    /**
     * Le nom du plat
     */
    private String plat;

    /**
     * La durée en heure de la cuisson du plat
     */
    private int heure;

    /**
     * La durée en minute de la cuisson du plat
     */
    private int minute;

    /**
     * La température en degrés de la cuisson du plat
     */
    private int degree;

    /**
     * Crée une nouvelle cuisson
     *
     * @param plat Le nom du plat
     * @param heure La durée en heure
     * @param minute La durée en minutes
     * @param degree La température en degrés
     * @throws IllegalArgumentException Si les valeurs passées en arguments
     * ne sont pas correctes
     */
    public Cuisson(String plat, int heure, int minute, int degree) {
        if (!platValide(plat)
                || !horaireValide(heure, minute)
                || !temperatureValide(degree)) {
            throw new IllegalArgumentException(CHAINE_DEFAUT);
        }

        this.plat = plat;
        this.heure = heure;
        this.minute = minute;
        this.degree = degree;
    }

    /**
     * Détermine si un nom de plat est valide (non vide, au plus 20
     * caractères et ne contient pas le caractère '|')
     *
     * @param nomPlat chaîne à tester
     * @return un booléen égal à vrai ssi la chaîne à tester est valide
     */
    public static boolean platValide(String nomPlat) {
        return nomPlat.length() > 0 && nomPlat.length() <= LG_MAX_PLAT
               && !nomPlat.contains("|");
    }

    /**
     * Prédicat qui définit si un horaire est valide
     * Les heures et les minutes doivent être valides et ne doivent pas être
     * nulles
     *
     * @param heure La durée en heure à tester
     * @param minute La durée en minutes à tester
     * @return true si le prédicat est vérifié, false sinon
     */
    public static boolean horaireValide(int heure, int minute) {
        return !(heure == 0 && minute == 0)
                && heureCuissonValide(heure)
                && minuteCuissonValide(minute);
    }

    /**
     * Détermine si le nombre d'heures d'une durée de cuisson est valide
     * (comprise entre 0 et 9)
     *
     * @param heureCuisson heure à tester
     * @return un booléen égal à vrai ssi l'heure de la durée est valide
     */
    private static boolean heureCuissonValide(int heureCuisson) {
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
    private static boolean minuteCuissonValide(int minuteCuisson) {
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

    /**
     * @return Le nom du plat
     */
    public String getPlat() {
        return plat;
    }

    /**
     * @return La durée en heure
     */
    public int getHeure() {
        return heure;
    }

    /**
     * @return La durée en minute
     */
    public int getMinute() {
        return minute;
    }

    /**
     * @return La température en degré
     */
    public int getDegree() {
        return degree;
    }

    /**
     * Édite la totalité des champs de la cuisson
     *
     * @param plat Le nouveau nom du plat
     * @param heure La nouvelle durée en heure de la cuisson
     * @param minute La nouvelle durée en minutes de la cuisson
     * @param degree Le nouvelle température de la cuisson
     * @throws IllegalArgumentException Si les paramètres sont incorrects
     */
    public void editCuisson(String plat, int heure, int minute, int degree) {
        if (!platValide(plat) || !horaireValide(heure, minute) || !temperatureValide(degree))
            throw new IllegalArgumentException();

        this.plat = plat;
        this.heure = heure;
        this.minute = minute;
        this.degree = degree;
    }

    @Override
    public String toString() {
        StringBuilder resultat = new StringBuilder();

        resultat.append(plat)
                .append(chaineEspace(LG_MAX_PLAT - plat.length()))
                .append(" | ");
        if (heure < 10) resultat.append(0);
        resultat.append(heure).append(" h ");
        if (minute < 10) resultat.append(0);
        resultat.append(minute).append(" | ").append(degree);

        return resultat.toString();
    }

    /**
     * Renvoie le thermostat correspondant à la température argument
     * (celle-ci doit être inférieure à TEMPERATURE_MAX)
     *
     * @return l'entier égal au thermostat ou -1 si la température est invalide
     */
    public int getThermostat() {
        int thermostat = -1;

        if (temperatureValide(degree)) {
            thermostat = degree / 30;
            if (degree % 30 > 15) thermostat++;
        }

        return thermostat;
    }

    /**
     * Renvoie une chaîne constituée d'espaces. Le nombre d'espaces est indiqué
     * par le paramètre.
     *
     * @param nbEspace nombre d'espaces à placer dans la chaîne
     * @return une chaîne constituée du caractère espace
     */
    private static String chaineEspace(int nbEspace) {
        final char ESPACE = ' ';
        return new String(new char[nbEspace]).replace('\0', ESPACE);
    }
}