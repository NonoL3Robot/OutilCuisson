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


import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

public class AfficherFragment extends Fragment {

    /**
     * Liste des cuissons enregistré dans l'application
     */
    public static ArrayList<com.example.outilcuisson.Cuisson> cuissonAffichees = new ArrayList<>();

    static View listeCuisson1;
    static ArrayList listeCuisson;

    private ArrayAdapter<String> adaptateur;
    private ArrayList<String> cuissonAAfficher;

    /**
     * Ajouter une cuisson
     *
     * @param cuisson La cuisson à ajouter
     */
    public static void addCuisson(Cuisson cuisson) {
        cuissonAffichees.add(cuisson);
        updateSaveFile();
    }

    /**
     * Met a jour le fichier dataCuisson.txt qui sauvegarde la liste des cuissons
     */
    private static void updateSaveFile() {
        // TODO Il faut creer un fichier cuisson.txt si il n'existe pas

        File file;
        FileOutputStream fos = null;
      try {
            file = new File("dataCuisson.txt");
            fos = new FileOutputStream(file);
            if(!file.exists()){
                file.createNewFile();
            }
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(listeCuisson1);
          oos.close(); fos.close();
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }

    /**
     * Charge la liste des cuissons contenue dans le fichier dataCuisson.txt
     */
    private static void loadSaveFile() {
        // TODO à décommenter lorsque updateSaveFile() sera terminé

        try {
            FileInputStream fis = new FileInputStream("dataCuisson.txt");
            ObjectInputStream ois = new ObjectInputStream(fis);

           listeCuisson = (ArrayList) ois.readObject();
           ois.close();
            fis.close();
        } catch (FileNotFoundException ignored) {
        } catch (IOException ioe) {
            ioe.printStackTrace();
        } catch (ClassNotFoundException c) {
            System.out.println("Class not found");
            c.printStackTrace();
        }
    }

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
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.afficher_fragment, container, false);
        loadSaveFile();
        listeCuisson1 = view.findViewById(R.id.listeCuisson);
        registerForContextMenu(listeCuisson1);
        return view;
    }

    /* Crée le menu contextuel en le désérialisant à partir du fichier
     * menu_contextuel.xml
     */
    @Override
    public void onCreateContextMenu(ContextMenu menu,
                                    View v,
                                    ContextMenu.ContextMenuInfo menuInfo) {
        // pas sûr du super.getContext() (Noé)
        new MenuInflater(super.getContext()).inflate(R.menu.menu_contextuel, menu);
        super.onCreateContextMenu(menu, v, menuInfo);
    }

    /* Réalise l'action souhaité en fonction de l'item du menu selectionné */
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
