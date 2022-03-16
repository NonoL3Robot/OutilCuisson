/*
 * AfficherFragment.java, 21/02/2022
 * IUT Rodez 2022-2022, INFO2
 * pas de copyright, aucun droits
 */

package com.example.outilcuisson;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import java.util.ArrayList;

/**
 * Fragment qui affiche la liste des cuissons de l'application
 *
 * @author THIZY Alexandre
 * @author VABRE Lucàs
 * @author VILLENEUVE Noé
 */
public class AfficherFragment extends Fragment {

    /**
     * Activité parente
     */
    private MainActivity activity;

    /**
     * Liste visuelle des cuissons
     */
    private ListView listView;

    /**
     * Adapter qui fait le lien entre la liste visuelle et une ArrayList de
     * String
     */
    public static ArrayAdapter<String> adapterCuissons;

    /**
     * @return une nouvelle instance de cette classe
     */
    public static AfficherFragment newInstance() {
        return new AfficherFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.afficher_fragment, container,false);

        /* Elements intéractifs */
        listView = view.findViewById(R.id.listeCuisson);
        adapterCuissons = new ArrayAdapter<>(getActivity(),
                R.layout.ligne_liste, R.id.item_cuisson, new ArrayList<>());
        listView.setAdapter(adapterCuissons);
        registerForContextMenu(listView);

        /* Référence le main activity */
        activity = (MainActivity) getActivity();

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        afficherCuisson();
    }

    /**
     * Met a jour la liste visuelle des cuissons par rapport au cuissons
     * enregistré dans le MainActivity
     */
    public void afficherCuisson() {
        adapterCuissons.clear();

        /* Ajoute l'objet cuisson  */
        ArrayList<Cuisson> listeCuisson = activity.getListeCuisson();

        /* On affiche toutes les cuissons de la liste dans la view */
        for (Cuisson cuisson : listeCuisson) {
            adapterCuissons.add(cuisson.toString());
        }

        /* Met à jour la liste */
        adapterCuissons.notifyDataSetChanged();
        listView.requestLayout();
    }

    /**
     * Crée le menu contextuel en le désérialisant à partir du fichier
     * menu_contextuel.xml
     */
    @Override
    public void onCreateContextMenu(@NonNull ContextMenu menu,
                                    @NonNull View v,
                                    ContextMenu.ContextMenuInfo menuInfo) {
        new MenuInflater(getContext()).inflate(R.menu.menu_contextuel,
                menu);
        super.onCreateContextMenu(menu, v, menuInfo);
    }

    /**
     * Réalise l'action souhaité en fonction de l'item du menu selectionné
     */
    @SuppressLint("NonConstantResourceId")
    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        AdapterView.AdapterContextMenuInfo information
                = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();

        int index = information.position;

        switch (item.getItemId()) {
            case R.id.thermosContext:
                afficherThermos(index);
                break;
            case R.id.modifierContext:
                activity.editerCuisson(index);
                break;
            case R.id.supprContext:
                supprimerCuisson(index);
                break;
        }
        return super.onContextItemSelected(item);
    }

    /**
     * Crée une fenêtre qui montre les informations de la cuisson sélectionnée
     * avec notamment la valeur du thermostat
     *
     * @param index indice de la cuisson sélectionné dans la liste des cuissons
     */
    public void afficherThermos(int index) {
        /* Recupère l'objet cuisson */
        Cuisson cuisson = activity.getListeCuisson().get(index);

        String content = getString(R.string.alert_content_thermos,
                cuisson.getPlat(),
                cuisson.getDegree(),
                cuisson.getThermostat());
        new AlertDialog
                .Builder(getContext())
                .setTitle(R.string.alert_title_thermos)
                .setMessage(content)
                .setNeutralButton(R.string.alert_neutral_button, null)
                .show();
    }

    /**
     * Supprime l'objet correspondant cuisson
     *
     * @param index indice de la cuissons sélectionnée dans la liste des
     *              cuissons
     */
    public void supprimerCuisson(int index) {
        activity.getListeCuisson().remove(index);
        afficherCuisson();
    }
}
