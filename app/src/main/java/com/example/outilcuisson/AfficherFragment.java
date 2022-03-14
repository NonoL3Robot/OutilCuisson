/*
 * AfficherFragment.java, 21/02/2022
 * IUT Rodez 2022-2022, INFO2
 * pas de copyright, aucun droits
 */

package com.example.outilcuisson;

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
    public ArrayAdapter<String> adapterCuissons;


    /**
     * Liste des cuissons enregistrées dans l'application
     */
    public static ArrayList<String> cuissonAffichees;

    /**
     * Le nom du fichier de sauvegarde
     */
    public static final String FICHIER_SAUVEGARDE = "cuisson.save";


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
        adapterCuissons = new ArrayAdapter<String>(getActivity(),
            android.R.layout.simple_list_item_1, cuissonAffichees);
        listeCuissons.setAdapter(adapterCuissons);
        registerForContextMenu(listeCuissons);

//        cuissonAffichees.add(new Cuisson("test", 0, 40, 180));
        adapterCuissons.notifyDataSetChanged();
        listeCuissons.requestLayout();

        return view;
    }

    /**
     * Ajouter une cuisson
     *
     * @param cuisson La cuisson à ajouter
     */
    public static void addCuisson(Cuisson cuisson) {
        cuissonAffichees.add(cuisson.toString());
        System.out.println(cuissonAffichees);
    }

    /**
     * Met a jour le fichier dataCuisson.txt qui sauvegarde la liste des
     * cuissons
     */
    private static void updateSaveFile() {
        try {
            FileOutputStream fos = new FileOutputStream(FICHIER_SAUVEGARDE);
            ObjectOutputStream oos = new ObjectOutputStream(fos);

            oos.writeObject(cuissonAffichees);

            oos.close();
            fos.close();
            System.out.println("Sauvegardé avec succès");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Charge la liste des cuissons contenue dans le fichier dataCuisson.txt
     */
    private static void loadSaveFile() {
        try {
            FileInputStream fis = new FileInputStream(FICHIER_SAUVEGARDE);
            ObjectInputStream ois = new ObjectInputStream(fis);

            cuissonAffichees = (ArrayList) ois.readObject();

            ois.close();
            fis.close();
            System.out.println("Chargement des données avec succès");
        } catch (IOException ioe) {
            ioe.printStackTrace();
        } catch (ClassNotFoundException c) {
            System.out.println("Class not found");
            c.printStackTrace();
        }
    }

    /**
     * Crée le menu contextuel en le désérialisant à partir du fichier
     * menu_contextuel.xml
     */
    @Override
    public void onCreateContextMenu(ContextMenu menu,
                                    View v,
                                    ContextMenu.ContextMenuInfo menuInfo) {
        // pas sûr du super.getContext() (Noé)
        new MenuInflater(super.getContext()).inflate(R.menu.menu_contextuel,
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
            case R.id.supprContext:
                // TODO
                break;
            case R.id.thermosContext:
                // TODO
                break;
            case R.id.cancelContext:
                break;
        }
        return super.onContextItemSelected(item);
    }
}
