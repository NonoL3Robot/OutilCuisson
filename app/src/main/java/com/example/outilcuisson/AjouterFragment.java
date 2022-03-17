/*
 * AjouterFragment.java, 21/02/2022
 * IUT Rodez 2022-2022, INFO2
 * pas de copyright, aucun droits
 */

package com.example.outilcuisson;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import java.util.ArrayList;

/**
 * Fragment qui comporte plusieurs champs à compléter et qui permet d'ajouter
 * des cuissons à l'application
 *
 * @author THIZY Alexandre
 * @author VABRE Lucàs
 * @author VILLENEUVE Noé
 */
public class AjouterFragment extends Fragment {

    /**
     * Référence de l'activité parente
     */
    MainActivity activity;

    /**
     * Zone de texte correspondant au nom du plat
     */
    EditText inputPlat;

    /**
     * Élément graphique qui permet de sélectionner une durée
     */
    TimePicker inputDuree;

    /**
     * Zone de texte correspondant à la température du plat
     */
    EditText inputTemperature;

    /**
     * Bouton qui efface les champs et rétabli leurs valeurs par défaut
     */
    Button btnEffacer;

    /**
     * Bouton qui essaye de créer/éditer une cuisson si celle-ci est valide
     */
    Button btnValider;

    /**
     * @return une nouvelle instance de la classe
     */
    public static AjouterFragment newInstance() {
        return new AjouterFragment();
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.ajouter_fragment, container, false);

        /* Liste des éléments intéractifs */
        inputPlat = view.findViewById(R.id.input_plat);
        inputDuree = view.findViewById(R.id.input_duree);
        inputTemperature = view.findViewById(R.id.input_temperature);
        btnEffacer = view.findViewById(R.id.btn_effacer);
        btnValider = view.findViewById(R.id.btn_valider);

        /* Initialise le TimePicker en format 24h et met les champs par défaut */
        inputDuree.setIs24HourView(true);
        champsDefaut();

        /* Actions des boutons */
        btnValider.setOnClickListener(this::actionBtnValider);
        btnEffacer.setOnClickListener(this::actionBtnEffacer);

        /* Référence la MainActivity */
        activity = (MainActivity) getActivity();

        return view;
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onResume() {
        super.onResume();

        /* Si on est en mode édition, on pré-rempli le champ */
        if (activity.modeEdition) {
            Cuisson cuisson = activity.cuissonAEditer;

            System.out.println(cuisson);

            /* On rempli les champs */
            inputPlat.setText(cuisson.getPlat());
            inputDuree.setCurrentHour(cuisson.getHeure());
            inputDuree.setCurrentMinute(cuisson.getMinute());
            inputTemperature.setText(Integer.toString(cuisson.getDegree()));
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        activity.modeEdition = false;
        champsDefaut();
    }

    /**
     * Action lors du clic sur le bouton Valider
     *
     * @param view Écouteur d'évènements
     */
    private void actionBtnValider(View view) {

        /* Récupère toutes les valeurs des champs */
        String txtPlat = inputPlat.getText().toString();
        String txtTemperature = inputTemperature.getText().toString();
        int hDuree = inputDuree.getCurrentHour();
        int mDuree = inputDuree.getCurrentMinute();

        /* Convertit la température en entier */
        int temperature = txtTemperature.isEmpty() ? -1 : Integer.parseInt(txtTemperature);

        /*
         * Si le mode édition est activé, édite la cuisson, sinon crée une
         * nouvelle cuisson
         */
        if (activity.modeEdition)
            editerCuisson(txtPlat, hDuree, mDuree, temperature);
        else creerCuisson(txtPlat, hDuree, mDuree, temperature);
    }

    /**
     * Édite une cuisson
     *
     * @param plat        Nouveau nom du plat
     * @param heure       La nouvelle durée en heure de la cuisson
     * @param minutes     La nouvelle durée en minutes de la cuisson
     * @param temperature La nouvelle température de la cuisson
     */
    private void editerCuisson(String plat, int heure, int minutes, int temperature) {
        Cuisson cuisson = activity.cuissonAEditer;
        try {
            cuisson.editCuisson(plat, heure, minutes, temperature);

            activity.changeFragment(0);
        } catch (IllegalArgumentException e) {
            /* Crée une boite de dialogue qui informe que les valeurs sont
            incorrectes */
            new AlertDialog
                    .Builder(getContext())
                    .setTitle(R.string.alert_title_error)
                    .setMessage(R.string.alert_content_error)
                    .setNeutralButton(R.string.alert_neutral_button, null)
                    .show();
            e.printStackTrace();
        }
    }

    /**
     * Methode qui tente de créer une nouvelle Cuisson si elle est valide
     *
     * Si la cuisson est valide mais qu'elle existe déjà, on demande si l'on
     * veut l'éditer et change la valeur suivant le choix de l'utilisateur
     *
     * @param plat Le nouveau nom du plat
     * @param heure La nouvelle durée en heure
     * @param minutes La nouvelle durée en minutes
     * @param temperature La nouvelle température du plat
     */
    private void creerCuisson(String plat, int heure, int minutes, int temperature) {
        try {
            Cuisson cuisson = new Cuisson(plat, heure, minutes, temperature);

            /* On ajoute la cuisson à la liste des cuissons */
            activity.addCuisson(cuisson);

            /* Remet les champs vides */
            champsDefaut();

            /* Message que l'ajout est un succès */
            String content = getString(R.string.toast_ajout_ok, cuisson.getPlat());
            Toast.makeText(getContext(), content, Toast.LENGTH_SHORT).show();
        } catch (IllegalArgumentException e) {

            /*
             * Crée un boite de dialogue qui informe que les valeurs sont
             * incorrectes
             */
            new AlertDialog
                    .Builder(getContext())
                    .setTitle(R.string.alert_title_error)
                    .setMessage(R.string.alert_content_error)
                    .setNeutralButton(R.string.alert_neutral_button, null)
                    .show();
            e.printStackTrace();
        } catch (CuissonDejaExistanteException e) {

            /*
             * La cuisson existe déjà, on propose d'éditer le plat qui fait
             * doublon
             */
            new AlertDialog
                    .Builder(getContext())
                    .setTitle(R.string.alert_title_doublon)
                    .setMessage(R.string.alert_content_doublon)
                    .setNegativeButton(R.string.alert_no_doublon, null)
                    .setPositiveButton(R.string.alert_yes_doublon, (dialogInterface, i) -> {
                        editerCuissonDoublon(plat, heure, minutes, temperature);
                    })
                    .show();
        }
    }

    /**
     * Recherche l'élément qui fait doublon dans la liste des cuissons de
     * l'activity et change ses valeurs
     *
     * @param plat Nouveau nom du plat
     * @param heure Nouvelle durée en heure
     * @param minutes Nouvelle durée en minutes
     * @param temperature Nouvelle température du plat
     */
    private void editerCuissonDoublon(String plat, int heure, int minutes,
                                      int temperature) {

        /* Récupère la liste des cuissons de l'activity */
        ArrayList<Cuisson> listeCuisson = activity.getListeCuisson();

        /* Recherche l'indice de l'élément qui fait doublon */
        for (int j = 0; j < listeCuisson.size(); j++) {

            /* Et modifie sa valeur avant d'arrêter la fonction */
            if (listeCuisson.get(j).getPlat().equals(plat)) {
                listeCuisson.get(j).editCuisson(plat,
                        heure, minutes, temperature);
                champsDefaut();
                return;
            }
        }
    }

    /**
     * Action lors du clic sur le bouton Effacer
     *
     *
     * @param view non utilisé
     */
    private void actionBtnEffacer(View view) {
        activity.modeEdition = false;
        champsDefaut();
    }

    /**
     * Remet les valeurs des champs par défaut
     */
    private void champsDefaut() {
        inputPlat.setText("");
        inputDuree.setCurrentHour(0);
        inputDuree.setCurrentMinute(40);
        inputTemperature.setText("");
    }
}
