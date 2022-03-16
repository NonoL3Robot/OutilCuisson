/*
 * AfficherFragment.java, 21/02/2022
 * IUT Rodez 2022-2022, INFO2
 * pas de copyright, aucun droits
 */

package com.example.outilcuisson;

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
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;


import com.google.android.material.navigation.NavigationView;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

public class AfficherFragment extends Fragment {

    /**
     * TODO
     */
    public ListView listeCuissons;

    /**
     * TODO
     */
    public static ArrayAdapter<String> adapterCuissons;


    /**
     * Liste des cuissons enregistrées dans l'application
     */
    public static ArrayList<String> cuissonAffichees;


    public AfficherFragment() {
    }

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
        View view = inflater.inflate(R.layout.afficher_fragment, container,
                                     false);
        cuissonAffichees = new ArrayList<>();
        listeCuissons = view.findViewById(R.id.listeCuisson);
        adapterCuissons = new ArrayAdapter<>(getActivity(),
            R.layout.ligne_liste, R.id.item_cuisson, cuissonAffichees);
        listeCuissons.setAdapter(adapterCuissons);
        registerForContextMenu(listeCuissons);

        return view;
    }

    /**
     * Ajouter une cuisson
     *
     * @param cuisson La cuisson à ajouter
     */
    public void addCuisson(Cuisson cuisson) {
        adapterCuissons.add(cuisson.toString());
        adapterCuissons.notifyDataSetChanged();
        listeCuissons.requestLayout();
    }

    /**
     * Crée le menu contextuel en le désérialisant à partir du fichier
     * menu_contextuel.xml
     */
    @Override
    public void onCreateContextMenu(ContextMenu menu,
                                    View v,
                                    ContextMenu.ContextMenuInfo menuInfo) {
        new MenuInflater(getContext()).inflate(R.menu.menu_contextuel,
                                                     menu);
        super.onCreateContextMenu(menu, v, menuInfo);
    }

    /**
     * Réalise l'action souhaité en fonction de l'item du menu selectionné
     */
    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        AdapterView.AdapterContextMenuInfo information
            = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();

        switch (item.getItemId()) {
            case R.id.thermosContext:
                afficherThermos(cuissonAffichees.get(information.position));
                break;
            case R.id.modifierContext:
//                modifierCuisson();
                break;
            case R.id.supprContext:
                cuissonAffichees.remove(information.position);
                adapterCuissons.notifyDataSetChanged();
                listeCuissons.requestLayout();
                break;

            case R.id.cancelContext:
                break;
        }
        return super.onContextItemSelected(item);
    }

    public void afficherThermos(String cuisson) {
        String content = getString(R.string.alert_content_thermos,
                                   Cuisson.extrairePlat(cuisson),
                                   Cuisson.extraireTemperature(cuisson),
                                   Cuisson.thermostat(Cuisson.extraireTemperature(cuisson)));
        new AlertDialog.Builder(getContext()).setTitle(R.string.alert_title_thermos)
                                             .setMessage(content)
                                             .setNeutralButton(
                                                 R.string.alert_neutral_button,
                                                 null)
                                             .show();
    }

    public void modifierCuisson() {
//        ((MainActivity)getActivity()).changeFragment(1);
        Fragment fragment = new AjouterFragment();
        FragmentManager fragmentManager = fragment.getChildFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.ajouter_fragment, fragment, fragment.toString());
        fragmentTransaction.addToBackStack(fragment.toString());
        fragmentTransaction.commit();
    }
}
