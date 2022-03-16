/*
 * AjouterFragment.java, 21/02/2022
 * IUT Rodez 2022-2022, INFO2
 * pas de copyright, aucun droits
 */

package com.example.outilcuisson;

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

public class AjouterFragment extends Fragment {

    EditText inputPlat;
    TimePicker inputDuree;
    EditText inputTemperature;
    Button btnEffacer;
    Button btnValider;

    public AjouterFragment() {}

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

        View view = inflater.inflate(R.layout.ajouter_fragment, container,false);

        /* Liste des éléments intéractifs */
        inputPlat = view.findViewById(R.id.input_plat);
        inputDuree = view.findViewById(R.id.input_duree);
        inputTemperature = view.findViewById(R.id.input_temperature);
        btnEffacer = view.findViewById(R.id.btn_effacer);
        btnValider = view.findViewById(R.id.btn_valider);

        /* Initialise le TimePicker en format 24h et met les champs par défaut */
        inputDuree.setIs24HourView(true);
        champsDefaut();

        /* Actions des buttons */
        btnValider.setOnClickListener(this::actionBtnValider);
        btnEffacer.setOnClickListener(this::actionBtnEffacer);

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();

        /* Si on est en mode edition, on préremplis le champs */
        if ( ((MainActivity)getActivity()).modeEdition) {
            Cuisson cuisson = ((MainActivity)getActivity()).cuissonAEditer;

            System.out.println(cuisson);

            /* On remplis les champs */
            inputPlat.setText(cuisson.getPlat());
            inputDuree.setHour(cuisson.getHeure());
            inputDuree.setMinute(cuisson.getMinute());
            inputTemperature.setText(Integer.toString(cuisson.getDegree()));
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        ((MainActivity)getActivity()).modeEdition = false;
        champsDefaut();
    }

    private void actionBtnValider(View view) {
        /* Récupère toutes les valeurs des champs */
        String txtPlat = inputPlat.getText().toString();
        String txtTemperature = inputTemperature.getText().toString();
        int hDuree = inputDuree.getHour();
        int mDuree = inputDuree.getMinute();

        /* Convertis la température en entier */
        int temperature = txtTemperature.isEmpty() ? -1 : Integer.parseInt(txtTemperature);

        if (((MainActivity)getActivity()).modeEdition) {
            Cuisson cuisson = ((MainActivity)getActivity()).cuissonAEditer;
            try {
                cuisson.editCuisson(txtPlat, hDuree, mDuree, temperature);

                ((MainActivity)getActivity()).changeFragment(0);
            } catch (IllegalArgumentException e) {
                /* Crée un boite de dialogue qui informe que les valeurs sont incorrectes */
                new AlertDialog
                        .Builder(getContext())
                        .setTitle(R.string.alert_title_error)
                        .setMessage(R.string.alert_content_error)
                        .setNeutralButton(R.string.alert_neutral_button,null)
                        .show();
                e.printStackTrace();
            }
        } else {
            /* On essaye de créer une nouvelle cuisson */
            try {
                Cuisson cuisson = new Cuisson(txtPlat, hDuree, mDuree, temperature);

                /* On ajoute la cuisson a la liste des cuisson */
                ((MainActivity) getActivity()).addCuisson(cuisson);

                champsDefaut();

                /* Message d'ajout */
                String content = getString(R.string.toast_ajout_ok,
                        cuisson.getPlat());
                Toast.makeText(getContext(), content, Toast.LENGTH_SHORT).show();
            } catch (IllegalArgumentException e) {

                /* Crée un boite de dialogue qui informe que les valeurs sont incorrectes */
                new AlertDialog
                        .Builder(getContext())
                        .setTitle(R.string.alert_title_error)
                        .setMessage(R.string.alert_content_error)
                        .setNeutralButton(R.string.alert_neutral_button,null)
                        .show();
                e.printStackTrace();
            }
        }
    }

    /**
     * Efface le contenu des champs de textes et met le TimePicker à 0h40
     * @param view non utilisé
     */
    private void actionBtnEffacer(View view) {
        ((MainActivity)getActivity()).modeEdition = false;
        champsDefaut();
    }

    private void champsDefaut() {
        inputPlat.setText("");
        inputDuree.setHour(0);
        inputDuree.setMinute(40);
        inputTemperature.setText("");
    }
}
