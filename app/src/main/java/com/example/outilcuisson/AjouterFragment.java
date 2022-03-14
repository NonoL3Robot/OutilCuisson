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

import com.example.outilcuisson.OutilCuisson;
import com.example.outilcuisson.Cuisson;

public class AjouterFragment extends Fragment {

    EditText inputPlat;
    TimePicker inputDuree;
    EditText inputTemperature;
    Button btnEffacer;
    Button btnValider;

    public AjouterFragment() {
    }

    public static AjouterFragment newInstance() {
        return new AjouterFragment();
    }

    public interface EcouteurAjout {
        void recevoirCuisson(Cuisson cuisson);
    }

    private EcouteurAjout activiteQuiMEcoute;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        activiteQuiMEcoute = (EcouteurAjout) context;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.ajouter_fragment, container,
                                     false);

        /* Liste des éléments intéractifs */
        inputPlat = view.findViewById(R.id.input_plat);
        inputDuree = view.findViewById(R.id.input_duree);
        inputTemperature = view.findViewById(R.id.input_temperature);
        btnEffacer = view.findViewById(R.id.btn_effacer);
        btnValider = view.findViewById(R.id.btn_valider);

        /* Initialise le TimePicker en format 24h avec par défaut la valeur
        0h40 */
        inputDuree.setIs24HourView(true);
        inputDuree.setHour(0);
        inputDuree.setMinute(40);

        /*
         * Action de création de la nouvelle cuisson
         */
        btnValider.setOnClickListener(view1 -> {
            /* Récupère toutes les valeurs des champs */
            String txtPlat = inputPlat.getText().toString();
            String txtTemperature = inputTemperature.getText().toString();
            int hDuree = inputDuree.getHour();
            int mDuree = inputDuree.getMinute();

            /* Convertis la température en entier */
            int temperature = txtTemperature.isEmpty() ? -1 : Integer.parseInt(
                txtTemperature);

            /* Cas ou les valeurs ne sont pas valides */
            if (!OutilCuisson.platValide(txtPlat)
                || !OutilCuisson.heureCuissonValide(hDuree)
                || !OutilCuisson.minuteCuissonValide(mDuree)
                || !OutilCuisson.temperatureValide(temperature)) {

                System.out.println("Erreur");

            }

            /* Cas ou les valeurs sont valides : on ajoute une nouvelle
            cuisson dans la liste a afficher */
            try {
                activiteQuiMEcoute.recevoirCuisson(
                    new Cuisson(txtPlat, hDuree, mDuree, temperature));
                Toast.makeText(getContext(), R.string.toast_ajout_ok, Toast.LENGTH_SHORT).show();
            } catch (Exception e) {
                new AlertDialog.Builder(getContext()).setTitle("Erreur")
                                                     .setMessage(e.getMessage())
                                                     .setPositiveButton(
                                                         R.string.alert_positive_button,
                                                         null)
                                                     .show();
                System.out.println(e.getMessage());
            }
        });

        /*
         * Efface le contenu des champs de textes et met le TimePicker à 0h40
         */
        btnEffacer.setOnClickListener(view2 -> {

            inputPlat.setText("");
            inputDuree.setHour(0);
            inputDuree.setMinute(40);
            inputTemperature.setText("");
        });

        return view;
    }
}
